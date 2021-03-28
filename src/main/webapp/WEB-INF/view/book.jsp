<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${book.title}</title>
    <link href="<c:url value="/resources/css/stylesheet.css" />" rel="stylesheet">
</head>
<body>

<br><br>
<div class="book_main"><h3>${book.title}</h3> by ${book.author}

    <br>
    ${book.aboutTheAuthor}

    <br><br>
    <h3>${book.subtitle}</h3>

    <br><br>

    ${book.aboutTheBook}

    <br><br>

    ${book.readers}

    <a href="read/${book.title.replaceAll(" ", "_")}/1">Read and listen</a>

</div>

</body>
</html>
