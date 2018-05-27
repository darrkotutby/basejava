package by.tut.darrko.webapp.web;

import by.tut.darrko.webapp.exception.NotExistStorageException;
import by.tut.darrko.webapp.model.*;
import by.tut.darrko.webapp.storage.SqlStorage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    private SqlStorage sqlStorage;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        String user = getServletContext().getInitParameter("dbUser");
        String password = getServletContext().getInitParameter("dbPassword");
        String url = getServletContext().getInitParameter("dbURL");

        sqlStorage = new SqlStorage(url, user, password);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        response.getWriter().write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <link rel=\"stylesheet\" href=\"" + getServletContext().getContextPath() + "/css/style.css\">\n" +
                "    <title>Курс JavaSE + Web.</title>\n" +
                "</head>\n" +
                "<body>");
        if (uuid != null && !uuid.equals("")) {
            try {
                Resume resume = sqlStorage.get(uuid);
                response.getWriter().write("<H1>Uuid: " + resume.getUuid() + "</H1>");
                response.getWriter().write("<p>");
                response.getWriter().write("<H2>Full name: " + resume.getFullName() + "</H2>");
                response.getWriter().write("<p>");
                for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                    response.getWriter().write(entry.getKey().getTitle() + ": " + entry.getValue() + "<br>");
                }
                response.getWriter().write("<p>");
                for (Map.Entry<SectionType, Section> entry : resume.getSections().entrySet()) {
                    response.getWriter().write("<H3>" + entry.getKey().getTitle() + "</H3>");
                    printSection(response, entry.getKey(), entry.getValue());
                }
            } catch (NotExistStorageException e) {
                response.getWriter().write(e.getMessage());
            }
        } else {
            List<Resume> list = sqlStorage.getAllSorted();
            if (list.size() == 0) {
                response.getWriter().write("not exist any resumes");
            } else {
                response.getWriter().write("<table>");

                response.getWriter().write("<thead><tr><th>UUID</th><th>Full name</th></tr></thead>");
                response.getWriter().write("<tbody>");
                for (Resume resume : list) {
                    response.getWriter().write("<tr>");
                    response.getWriter().write("<td>" + resume.getUuid() + "</td>");
                    response.getWriter().write("<td>" + resume.getFullName() + "</td>");
                    response.getWriter().write("</tr>");
                }
                response.getWriter().write("</tbody>");
                response.getWriter().write("</table>");
            }
        }
        response.getWriter().write("</body>\n" +
                "</html>");
    }

    public void printSection(HttpServletResponse response, SectionType sectionType, Section section) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE: {
                response.getWriter().write(((TextSection) section).getContent());
                break;
            }
            case ACHIEVEMENT:
            case QUALIFICATIONS: {
                for (String item : ((ListSection) section).getItems())
                    response.getWriter().write(item + "<br>");
                break;
            }
            case EXPERIENCE:
            case EDUCATION: {
                break;
            }
            default:
                response.getWriter().write("Unknown section type:" + sectionType);
        }
    }

}
