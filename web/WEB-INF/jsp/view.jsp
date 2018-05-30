<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="by.tut.darrko.webapp.model.ContactType" %>
<%@ page import="by.tut.darrko.webapp.model.SectionType" %>
<%@ page import="by.tut.darrko.webapp.model.ListSection" %>
<%@ page import="by.tut.darrko.webapp.model.OrganizationSection" %>

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



                    <c:forEach var="item" items="${listSection.getItems()}">
                        ${item}
                        <br>

                    </c:forEach>


            </c:when>


            <c:when test="${section.key eq SectionType.EXPERIENCE
            || section.key eq SectionType.EDUCATION }">


                <c:set var="organizationSection" scope="request" value="${section.value}"/>
                <jsp:useBean id="organizationSection" scope="request"
                             type="by.tut.darrko.webapp.model.OrganizationSection"/>



                <c:forEach var="organization" items="${organizationSection.getOrganizations()}">


                    <a href="${organization.getHomePage().getUrl()}">
                        ${organization.getHomePage().getName()}
                    </a>
                    <br>
                    <c:forEach var="position" items="${organization.getPositions()}">
                        ${position.getTitle()}
                        <br>
                        ${position.getStartDate()} - ${position.getEndDate()}
                        ${position.getDescription()}
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
