<%@ page import="by.tut.darrko.webapp.model.ContactType" %>
<%@ page import="by.tut.darrko.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="by.tut.darrko.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>

    <script type="text/javascript">
        function addElement(parentId, elementTag, elementName, html) {
            // Adds an element to the document
            var div = document.getElementById(parentId);
            var newElement = document.createElement(elementTag);
            newElement.setAttribute('name', elementName);
            newElement.innerHTML = html;
            div.appendChild(newElement);
        }
    </script>


</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>



    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="revision" value="${resume.revision}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="${ContactType.values()}">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="sectionType" items="${SectionType.values()}">
            <h4>${sectionType.title}</h4>



        <c:choose>
            <c:when test="${sectionType eq SectionType.PERSONAL
                                || sectionType eq SectionType.OBJECTIVE }">


                <c:set var="textSection" value="${resume.getSection(sectionType)}" scope="request" />

                <c:if test="${not empty textSection}">
                    <jsp:useBean id="textSection" type="by.tut.darrko.webapp.model.TextSection" scope="request"/>
                    <dd><input type="text" name="${sectionType.name()}" size=30 value="${textSection.content}"></dd>
                </c:if>
                <c:if test="${empty textSection}">
                    <dd><input type="text" name="${sectionType.name()}" size=30"></dd>
                </c:if>


            </c:when>
            <c:when test="${sectionType eq SectionType.ACHIEVEMENT
            || sectionType eq SectionType.QUALIFICATIONS }">
                <c:set var="listSection" value="${resume.getSection(sectionType)}" scope="page" />



                <a id="myLink" href="#" onclick="javascript:addElement('${sectionType.name()}', 'p', '${sectionType.name()}', '<dd><input type=text name=${sectionType.name()} size=30/><dd>'  );return false;"><img src="img/add.png"> Add new item</a>
                <p>
                <div id="${sectionType.name()}">
                <c:if test="${not empty listSection}">





                <jsp:useBean id="listSection" scope="page"
                             type="by.tut.darrko.webapp.model.ListSection"/>

                <c:forEach var="item" items="${listSection.items}">
                    <dd><input type="text" name="${sectionType.name()}" size=30 value="${item}"></dd>
                    <p>
                </c:forEach>

                </c:if>
            </div>

            </c:when>
            <c:when test="${sectionType eq SectionType.EXPERIENCE
            ||sectionType eq SectionType.EDUCATION }">
                <c:set var="organizationSection" scope="request" value="${resume.getSection(sectionType)}"/>



                    <a id="myLink" href="#" onclick="javascript:addElement('${sectionType.name()}', 'p', '${sectionType.name()}', '<dd><input type=text name=${sectionType.name()} size=30/><dd>'  );return false;"><img src="img/add.png"> Add new item</a>
                    <p>
                    <div id="${sectionType.name()}">


                <c:if test="${not empty organizationSection}">

                <jsp:useBean id="organizationSection" scope="request"
                             type="by.tut.darrko.webapp.model.OrganizationSection"/>
                <c:forEach var="organization" items="${organizationSection.organizations}">


                    <fieldset>

                    <dl>
                        <dt>Название:</dt>
                        <dd><input type="text" name="organizationName" size=50 value="${organization.homePage.name}"></dd>
                    </dl>
                    <dl>
                        <dt>Сайт:</dt>
                        <dd><input type="text" name="organizationHomePage" size=50 value="${organization.homePage.url}"></dd>
                    </dl>



                    <br>
                    <c:forEach var="position" items="${organization.positions}">
                        ${position.title}
                        <br>
                        ${position.startDate} - ${position.endDate}
                        ${position.description}
                        <br>
                    </c:forEach>
                    <br>

                    </fieldset>

                </c:forEach>



                </c:if>

                    </div>

            </c:when>
        </c:choose>

            <p>
        </c:forEach>
        <hr>
        <button type="submit" name="save" value="1">Сохранить</button>
        <button onclick="window.history.back()" name="CancelEdit" value="1">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

