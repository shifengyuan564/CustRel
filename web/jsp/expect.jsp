<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>敬请期待</title>
</head>
<body>
<%--<div>
    <img src="${pageContext.request.contextPath}/image/expect.jpg" alt=""/>
</div>--%>
<div>
    <ul>
        <c:if test="${map != null}">
            <li><a class="ment">昵称：${map.nickname}</a></li>
            <li><a class="ment">appid：${appid}</a></li>
            <li><a class="ment">timestamp：${timestamp}</a></li>
            <li><a class="ment">nonceStr：${map.nickname}</a></li>
            <li><a class="ment">signature：${signature}</a></li>
        </c:if>
    </ul>
</div>
</body>
</html>