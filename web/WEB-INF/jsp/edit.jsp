<%@ page import="by.tut.darrko.webapp.model.ContactType" %>
<%@ page import="by.tut.darrko.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>




<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" class="by.tut.darrko.webapp.model.Resume" scope="request"/>
    <c:set var="organizationCounter" value="${0}" scope="request" />
    <c:set var="positionCounter" value="${0}" scope="request" />

    <title>Resume ${resume.fullName}</title>

    <script type="text/javascript">
        function addElement(parentId, elementTag, elementName, html) {
            // Adds an element to the document
            var div = document.getElementById(parentId);
            var newElement = document.createElement(elementTag);
            newElement.setAttribute('id', elementName);
            newElement.innerHTML = html;
            div.appendChild(newElement);
        }


        function addOrganization(parentId, elementTag, elementName) {
            // Adds an element to the document
            var div = document.getElementById(parentId);
            var newElement = document.createElement(elementTag);


            var s = parseInt(document.getElementById('organizationCounter').value);
            newElement.setAttribute('id', elementName+ "_organization"+s);

            var html1 =
                "<dl>" +
                "<dt>Name*</dt>" +
                "<dd>" +
                "<input type=\"text\" name=\""+ elementName +"_organization"+ s +"_1name\" size=30>" +
                "</dd>" +
                "</dl>" +
                "<dl>" +
                "<dt>URL</dt>" +
                "<dd>" +
                "<input type=\"text\" name=\""+ elementName +"_organization"+ s +"_2url\" size=30>" +
                "</dd>" +
                "</dl>" +


                "<br>" +


                "<a id=\"myLink1\" href=\"#\" onclick=\"javascript:addPosition('"+ elementName +"_organization"+ s + "', 'div', '"+ elementName +"_organization"+ s +"');return false;\"><img src=\"img/add.png\"> Add new position</a>" +






                "<p>"  ;




            newElement.innerHTML = html1;
            div.appendChild(newElement);

            document.getElementById('organizationCounter').value = s + 1;
        }


        function addPosition(parentId, elementTag, elementName) {
            // Adds an element to the document
            var div = document.getElementById(parentId);
            var newElement = document.createElement(elementTag);


            var s = parseInt(document.getElementById('positionCounter').value);

            var html1 = "<fieldset id=\""+ elementName + "_position"+s+"\">" +
                "<dl>" +
                "<dt>Title*</dt>" +
                "<dd>" +
                "<input type=\"text\" name=\""+ elementName +"_position"+ s +"_1title\" size=30>" +
                "</dd>" +
                "</dl>" +
                "<dl>" +
                "<dt>Started*</dt>" +
                "<dd>" +
                "<input type=\"text\" name=\""+ elementName +"_position"+ s +"_2startDate\" size=30>" +
                "</dd>" +
                "</dl>" +
            "<dl>" +
            "<dt>Ended</dt>" +
            "<dd>" +
            "<input type=\"text\" name=\""+ elementName +"_position"+ s +"_3endDate\" size=30>" +
            "</dd>" +
            "</dl>" +
                "<dl>" +
                "<dt>Description</dt>" +
                "<dd>" +
                "<input type=\"text\" name=\""+ elementName +"_position"+ s +"_4description\" size=30>" +
                "</dd>" +
                "</dl>" +
                "</fieldset>" ;




            newElement.innerHTML = html1;
            div.appendChild(newElement);

            document.getElementById('positionCounter').value = s + 1;
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
            <dt>Full name*</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Contacts:</h3>
        <c:forEach var="type" items="${ContactType.values()}">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Sections:</h3>
        <c:forEach var="sectionType" items="${SectionType.values()}">




        <c:choose>
            <c:when test="${sectionType eq SectionType.PERSONAL
                                || sectionType eq SectionType.OBJECTIVE }">

                <h4>${sectionType.title}</h4>
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

                <h4>${sectionType.title}</h4>

                <a id="myLink" href="#" onclick="javascript:addElement('${sectionType.name()}', 'p', '${sectionType.name()}', '<dd><input type=text name=${sectionType.name()} size=30/><dd>'  );return false;"><img src="img/add.png"> Add new item</a>
                <p>
                <div id="${sectionType.name()}">
                <c:if test="${not empty listSection}">





                <jsp:useBean id="listSection" scope="page"
                             type="by.tut.darrko.webapp.model.ListSection"/>

                <c:forEach var="item" items="${listSection.items}">
                    <dd><input type="text" name="${sectionType.name()}" size=30 value="${item}"></dd>
                    <p/>
                </c:forEach>

                </c:if>
            </div>

            </c:when>


            <c:when test="${sectionType eq SectionType.EXPERIENCE
            || sectionType eq SectionType.EDUCATION }">

                <h4>${sectionType.title} </h4>




                <div id="${sectionType.name()}">

                    <a id="myLink" href="#" onclick="javascript:addOrganization('${sectionType.name()}', 'fieldset', '${sectionType.name()}');return false;"><img src="img/add.png"> Add new organization</a>
                    <p>



                <c:set var="organizationSection" scope="request" value="${resume.sections.get(sectionType)}"/>



                <jsp:useBean id="organizationSection" scope="request"
                             class="by.tut.darrko.webapp.model.OrganizationSection"/>
                <c:forEach var="organization" items="${organizationSection.organizations}">


                    <fieldset  id="${sectionType.name()}_organization${organizationCounter}">

                        <dl>
                            <dt>Name*</dt>
                            <dd><input type="text" name="${sectionType.name()}_organization${organizationCounter}_1name" size=30 value="${organization.homePage.name}"></dd>
                        </dl>

                        <dl>
                            <dt>URL</dt>
                            <dd><input type="text" name="${sectionType.name()}_organization${organizationCounter}_2url" size=30 value="${organization.homePage.url}"></dd>
                        </dl>



                    <br>

                        <a id="myLink1" href="#" onclick="javascript:addPosition('${sectionType.name()}_organization${organizationCounter}', 'div', '${sectionType.name()}_organization${organizationCounter}');return false;"><img src="img/add.png"> Add new position</a>
                    <p>

                    <c:forEach var="position" items="${organization.positions}">



                        <fieldset id="${sectionType.name()}_organization${organizationCounter}_position${positionCounter}">
                        <dl>
                            <dt>Title*</dt>
                            <dd><input type="text" name="${sectionType.name()}_organization${organizationCounter}_position${positionCounter}_1title" size=30 value="${position.title}"></dd>
                        </dl>
                        <dl>
                            <dt>Started*</dt>
                            <dd><input type="text" name="${sectionType.name()}_organization${organizationCounter}_position${positionCounter}_2startDate" size=30 value="${position.startDate}"></dd>
                        </dl>
                        <dl>
                            <dt>Ended</dt>
                            <dd><input type="text" name="${sectionType.name()}_organization${organizationCounter}_position${positionCounter}_3endDate" size=30 value="${position.endDate}"></dd>
                        </dl>
                        <dl>
                            <dt>Description</dt>
                            <dd><input type="text" name="${sectionType.name()}_organization${organizationCounter}_position${positionCounter}_4description" size=30 value="${position.description}"></dd>
                        </dl>

                        <br>

                        <c:set var="positionCounter" value="${positionCounter + 1}" scope="request"/>
                        </fieldset>
                    </c:forEach>

                    </fieldset>


                    <br>


                    <c:set var="organizationCounter" value="${organizationCounter + 1}" scope="request"/>

                </c:forEach>

                </div>


            </c:when>


        </c:choose>

            <p>
        </c:forEach>

            <input type="hidden" id="organizationCounter" name="organizationCounter" value="${organizationCounter}">
            <input type="hidden" id="positionCounter" name="positionCounter" value="${positionCounter}">

        <button type="submit" name="save" value="1">Сохранить</button>
        <button onclick="window.history.back()" name="CancelEdit" value="1">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

