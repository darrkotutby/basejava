<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="resume" scope="request" type="by.tut.darrko.webapp.model.Resume"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <H1>Uuid: ${resume.getUuid()} </H1>
    <H2>Full name: ${resume.getFullName()}</H2>
    <c:set var="contacts" scope="request" value="${resume.getContacts()}"/>
    <jsp:useBean id="contacts" scope="request"
                 type="java.util.Map<by.tut.darrko.webapp.model.ContactType, java.lang.String>"/>
    <c:forEach var="contact" items="${contacts}">
        ${contact.key.title} ${contact.value}
        <br>
    </c:forEach>
    <c:set var="sections" scope="request" value="${resume.getSections()}"/>
    <jsp:useBean id="sections" scope="request"
                 type="java.util.Map<by.tut.darrko.webapp.model.SectionType, by.tut.darrko.webapp.model.Section>"/>
    <c:forEach var="section" items="${sections}">
        <H3>${section.key.title}</H3>
        ${section.value}
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
