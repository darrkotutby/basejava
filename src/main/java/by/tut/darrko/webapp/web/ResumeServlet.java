package by.tut.darrko.webapp.web;

import by.tut.darrko.webapp.model.*;
import by.tut.darrko.webapp.storage.SqlStorage;
import by.tut.darrko.webapp.storage.Storage;
import by.tut.darrko.webapp.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;



public class ResumeServlet extends HttpServlet {

    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        String user = getServletContext().getInitParameter("dbUser");
        String password = getServletContext().getInitParameter("dbPassword");
        String url = getServletContext().getInitParameter("dbURL");

        storage = new SqlStorage(url, user, password);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String cancelEdit = request.getParameter("CancelEdit");
        if (cancelEdit != null) {
            response.sendRedirect("resume");
            return;
        }

        String save = request.getParameter("save");


        String uuid = request.getParameter("uuid");
        Integer revision = Integer.parseInt(request.getParameter("revision"));
        String fullName = request.getParameter("fullName");
        Resume r = new Resume(uuid, fullName, revision);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            }
        }

        for (SectionType type : SectionType.values()) {

            switch (type) {
                case PERSONAL:
                case OBJECTIVE: {
                    String content = request.getParameter(type.toString());
                    if (content != null) {
                        r.addSection(type, new TextSection(content));
                    }
                    break;
                }
                case ACHIEVEMENT:
                case QUALIFICATIONS: {
                    String[] items = request.getParameterValues(type.toString());
                    if (items != null) {
                        r.addSection(type, new ListSection(Arrays.asList(items)));
                    }

                    break;
                }
                case EXPERIENCE:
                case EDUCATION: {
                    OrganizationSection section = getOrganizationSectionFromRequest(request, type);
                    if (section!=null && section.getOrganizations().size() != 0) {
                        r.addSection(type, section);
                    }

                    break;
                }
                default:
                    throw new IllegalArgumentException("Unknown section type:" + type);
            }


        }


        if (save != null) {
            if (revision != -1) {
                storage.update(r);
            } else {
                storage.save(r);
            }
            response.sendRedirect("resume");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        String revision = request.getParameter("revision");
        String sectionType = request.getParameter("sectionType");
        String organization = request.getParameter("organization");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "new":
                r = new Resume();
                break;
            case "view":
            case "edit":
                r = storage.get(uuid);
                request.setAttribute("resume", r);
                request.setAttribute("revision", revision);
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }


        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    public OrganizationSection getOrganizationSectionFromRequest(HttpServletRequest request, SectionType type) {
        OrganizationSection section = new OrganizationSection();

        SortedMap<String, String[]> sortedMap = new TreeMap<>(request.getParameterMap()).subMap(type.name(), type.name() + Character.MAX_VALUE);

        if (sortedMap.size() != 0) {

            Iterator<String> iterator = sortedMap.keySet().iterator();
            String name = null;
            String url = null;
            String organizationTag = null;
            Organization organization = null;
            List<Organization.Position> positions = null;

            while (iterator.hasNext()) {
                String key = iterator.next();
                if (key.endsWith("1name")) {
                    if (organizationTag != null) {
                        organization = new Organization(name, url);
                        if (positions.size() != 0) {
                            organization.setPositions(positions);
                        }
                        section.getOrganizations().add(organization);
                    }
                    name = sortedMap.get(key)[0];
                    url = sortedMap.getOrDefault(iterator.next(), new String[]{null})[0];
                    positions = new ArrayList<>();
                    organizationTag = key.replace("_1name", "");
                }
                if (key.endsWith("1title")) {
                    String title = sortedMap.get(key)[0];
                    String startD = sortedMap.get(iterator.next())[0];
                    String endD = sortedMap.getOrDefault(iterator.next(), new String[]{DateUtil.NOW.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))})[0];
                    String description = sortedMap.getOrDefault(iterator.next(), new String[]{null})[0];
                    if (positions == null) {
                        positions = new ArrayList<>();
                    }
                    positions.add(new Organization.Position(LocalDate.parse(startD, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            LocalDate.parse(endD, DateTimeFormatter.ofPattern("yyyy-MM-dd")), title, description));
                }
            }
            organization = new Organization(name, url);
            if (positions != null && positions.size() != 0) {
                organization.setPositions(positions);
            }
            section.getOrganizations().add(organization);
            return section;
        }
        return null;
    }
}