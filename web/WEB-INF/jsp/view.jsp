<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="by.tut.darrko.webapp.model.SectionType" %>
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
    <H3> ${resume.fullName} </H3>
    <c:set var="contacts" scope="request" value="${resume.contacts}"/>
    <jsp:useBean id="contacts" scope="request"
                 type="java.util.Map<by.tut.darrko.webapp.model.ContactType, java.lang.String>"/>
    <p>
    <c:forEach var="contact" items="${contacts}">
        <br>
        ${contact.key.title} ${contact.value}

    </c:forEach>
    </p>
    <c:set var="sections" scope="request" value="${resume.sections}"/>
    <c:forEach var="section" items="${sections}">
        <p>
        <H4>${section.key.title}</H4>
        <c:choose>
            <c:when test="${section.key eq SectionType.PERSONAL
            || section.key eq SectionType.OBJECTIVE }">
                ${section.value}
            </c:when>
            <c:when test="${section.key eq SectionType.ACHIEVEMENT
            || section.key eq SectionType.QUALIFICATIONS }">
                    <c:set var="listSection" scope="request" value="${section.value}"/>
                    <jsp:useBean id="listSection" scope="request"
                                 type="by.tut.darrko.webapp.model.ListSection"/>
                    <c:forEach var="item" items="${listSection.items}">
                        ${item}
                        <br>
                    </c:forEach>
            </c:when>
            <c:when test="${section.key eq SectionType.EXPERIENCE
            || section.key eq SectionType.EDUCATION }">
                <c:set var="organizationSection" scope="request" value="${section.value}"/>
                <jsp:useBean id="organizationSection" scope="request"
                             type="by.tut.darrko.webapp.model.OrganizationSection"/>
                <c:forEach var="organization" items="${organizationSection.organizations}">
                    <a href="${organization.homePage.url}">
                        ${organization.homePage.name}
                    </a>
                    <br>
                    <c:forEach var="position" items="${organization.positions}">
                        ${position.title}
                        <br>
                        ${position.startDate} - ${position.endDate}
                        ${position.description}
                        <br>
                    </c:forEach>
                    <br>
                </c:forEach>
            </c:when>
        </c:choose>
        </p>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
