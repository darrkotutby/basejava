<%@ page import="by.tut.darrko.webapp.model.ContactType" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table class="table">
        <thead class="thead-light">
        <tr>
            <th>Имя</th>
            <th>E-mail</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>


        <jsp:useBean id="resumes" scope="request" type="java.util.List<by.tut.darrko.webapp.model.Resume>"/>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="by.tut.darrko.webapp.model.Resume"/>
            <tr>
                <td><a href="${pageContext.request.contextPath}/resume?action=view&uuid=${resume.getUuid()}"
                >${resume.fullName}
                </a>
                </td>
                 <td>${resume.getContact(ContactType.MAIL)}
                </td>

                <td><a href="${pageContext.request.contextPath}/resume?action=delete&uuid=${resume.getUuid()}"
                >Delete
                </a>
                </td>
                <td><a href="${pageContext.request.contextPath}/resume?action=edit&uuid=${resume.getUuid()}"
                >Edit
                </a>
                </td>

            </tr>

        </c:forEach>
        </tbody>
    </table>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
