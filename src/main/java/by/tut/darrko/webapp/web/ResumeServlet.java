package by.tut.darrko.webapp.web;

import by.tut.darrko.webapp.model.*;
import by.tut.darrko.webapp.storage.SqlStorage;
import by.tut.darrko.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

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
        if (cancelEdit!=null) {
            response.sendRedirect("resume");
            return;
        }

        String save = request.getParameter("save");
        String addAchievement = request.getParameter("addACHIEVEMENT");
        String addQualifications = request.getParameter("addQUALIFICATIONS");

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
                    if (content!=null) {
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
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unknown section type:" + type);
            }




        }









        if (save!=null) {
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
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
} 