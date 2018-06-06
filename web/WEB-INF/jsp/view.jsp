<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.tut.darrko.webapp.model.SectionType" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="by.tut.darrko.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<by.tut.darrko.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>

        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<by.tut.darrko.webapp.model.SectionType, by.tut.darrko.webapp.model.Section>"/>
    <p>

    <c:choose>
        <c:when test="${sectionEntry.key eq SectionType.PERSONAL
                                || sectionEntry.key eq SectionType.OBJECTIVE }">
    <H3>${sectionEntry.key.title}</H3>
            ${sectionEntry.value}
        </c:when>
        <c:when test="${sectionEntry.key eq SectionType.ACHIEVEMENT
            || sectionEntry.key eq SectionType.QUALIFICATIONS }">
            <H3>${sectionEntry.key.title}</H3>
            <c:set var="listSection" scope="request" value="${sectionEntry.value}"/>
            <jsp:useBean id="listSection" scope="request"
                         type="by.tut.darrko.webapp.model.ListSection"/>
            <c:forEach var="item" items="${listSection.items}">
                ${item}
                <br>
            </c:forEach>
        </c:when>
        <c:when test="${sectionEntry.key eq SectionType.EXPERIENCE
            || sectionEntry.key eq SectionType.EDUCATION }">

            <H3>${sectionEntry.key.title}
            <a href="resume?uuid=${resume.uuid}&action=editOrganizationSection&sectionType=${sectionEntry.key}">
            <img src="img/pencil.png"></a>
            </H3>


            <c:set var="organizationSection" scope="request" value="${sectionEntry.value}"/>
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


    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>


