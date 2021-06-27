<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Library</title>
    <link href="<c:url value="/resources/css/stylesheet.css" />" rel="stylesheet">
</head>
<body>

<br><br>
<div class="books_main">
    <h1>Library</h1>
    <a href="getTodayBook"><c:out default="" escapeXml="false"
                                  value="${isReceivedTodayBook ? '' : '(get_Today_Book)'}"/></a>
</div>


<br><br>

<table style="border:0 solid black;margin-left:auto;margin-right:auto; width: 1364px">
    <tbody>
    <tr>
        <td>
            <c:forEach items="${bookList}" var="book">
                <table style="border-collapse: separate; border-spacing: 50px 0; border:0 solid black;float: left">
                    <tbody>
                    <tr>
                        <td>
                            <img width="350" height="350"
                                 src="${pageContext.request.contextPath}/resources/${book.title.replaceAll(" ", "_")}/00_640.jpg"
                                 alt=""/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <a href="books/${book.title.replaceAll(" ", "_")}"
                               style="font-size: 20px; text-decoration: none">${book.title}</a>
                        </td>
                    </tr>
                    <tr>
                        <td style="height: 70px; vertical-align: top">
                                ${book.author}
                        </td>
                    </tr>
                    </tbody>
                </table>
            </c:forEach>
        </td>
    </tr>
    </tbody>
</table>
</body>
</html>
