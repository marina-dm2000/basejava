<%@ page import="ru.javaops.basejava.model.TextSection" %>
<%@ page import="ru.javaops.basejava.model.ListSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javaops.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" alt="edit"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javaops.basejava.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <hr>
    <table>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javaops.basejava.model.SectionType, ru.javaops.basejava.model.Section>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="ru.javaops.basejava.model.Section"/>
            <tr>
                <td><a name="type.name">${type.title}</a></td>
                <c:if test="${type == 'OBJECTIVE'}">
                    <td><%=((TextSection) section).getText()%></td>
                </c:if>
            </tr>
            <c:if test="${type != 'OBJECTIVE'}">
                <c:choose>
                    <c:when test="${type == 'PERSONAL'}">
                        <tr>
                            <td><%=((TextSection) section).getText()%></td>
                        </tr>
                    </c:when>
                    <c:when test="${type == 'QUALIFICATIONS' || type == 'ACHIEVEMENT'}">
                        <tr>
                            <td>
                                <ul>
                                    <c:forEach var="item" items="<%=((ListSection) section).getList()%>">
                                        <li>${item}</li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>
                    </c:when>
                </c:choose>
            </c:if>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
