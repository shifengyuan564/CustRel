<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>
<head>
    <title>$Title$</title>
</head>
<body>
<!-- 取得在servlet中设置的session范围属性值 -->
<%= "session中取得的属性值" + session.getAttribute("china") %>
<br/>
<!-- 取得在servlet中设置的request的属性值。并进行解码。否则会乱码 -->
<%="request中取得的属性值:" + request.getAttribute("say") %>
<br/>
${say}

<div>
    <ul>
        <c:if test="${map != null}">
            <li><a class="ment">昵称：${map.nickname}</a></li>
        </c:if>
    </ul>
</div>
</body>
</html>
