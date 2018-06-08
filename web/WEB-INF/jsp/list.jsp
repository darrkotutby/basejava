<%@ page import="by.tut.darrko.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <title>List of resumes</title>
    <style>
        td {border: 1px solid #808080;}
        th {border: 1px solid #808080;}
    </style>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table cellpadding="8" cellspacing="0" align=center>
        <tr>
            <td style="border: 0px solid #808080;">
                <a href="resume?action=new"><img src="img/add.png"> Add new resume</a>
            </td>
        </tr>
        <tr>
            <th>Full name</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="by.tut.darrko.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.MAIL.toHtml(resume.getContact(ContactType.MAIL))%>
                </td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
