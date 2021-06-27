<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${chapter.title}</title>
    <link href="<c:url value="/resources/css/stylesheet.css" />" rel="stylesheet">
</head>
<body>


<br>
<div class="chapter_title"><h1>${chapter.title}</h1></div>
<div class="audio_pannel">
    <audio controls style="width: 825px">
        <source src="${pageContext.request.contextPath}/resources/<c:out default="" escapeXml="false" value="${title.replaceAll(\" \", \"_\")}/${chapter.audioUrl}"/>"
                type="audio/mpeg">
    </audio>
</div>
<div class="chapter_content">
    ${chapter.text}
</div>


<c:set var="next" value='${chapter.title.equals("Final summary") ? "" : "Next"}' scope="page"/>
<c:set var="previous" value='${chapter.title.contains("What’s in it for me?") ? "" : "Previous"}' scope="page"/>
<c:set var="hidden" value='style="display:none"' scope="page"/>


<div class="reader_container_buttons">
    <div class="reader_container_navigation_control">
        <div class="reader_container_navigation_control_prev_button">
            <a class="reader_prev-icon" data-no-turbolink="true" <c:out default="" escapeXml="false"
                                                                        value="${previous.length() eq 0 ? hidden : \"\"}"/>
               href="${previous.equals("") ? "" : chapter.orderNo}">${previous}</a>

        </div>


        <a class="reader_all_books" data-no-turbolink="true" href="${pageContext.request.contextPath}/">List of the
            books</a>


        <div class="reader_container_navigation_control_next_button">
            <a class="reader_next-icon" data-no-turbolink="true" <c:out default="" escapeXml="false"
                                                                        value="${next.length() eq 0 ? hidden : \"\"}"/>
               href="${next.equals("") ? "" : chapter.orderNo + 2}">${next}</a>

        </div>

    </div>
</div>


</body>
</html>
