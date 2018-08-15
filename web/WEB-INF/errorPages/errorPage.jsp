<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no,width=device-width">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="telephone=no" name="format-detection">
<title>错误页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>

</head>

<body>
<!--<header class="header"><a class="fal" href="javascript:history.go(-1)">返回</a>
  <h1>错误页面</h1>
</header>
--><div class="error-prompt">
  <div class="mydd">
    <div class="picture"><img src="${pageContext.request.contextPath}/image/expertise-no.png" alt=""></div>
    <p>你访问的页面不存在！<br>
      请稍后再试~</p>
  </div>
  <div class="depressed">
    <ul>
      <li><a class="ment2" href="javascript:history.go(-1)">返回</a></li>
    </ul>
  </div>
</div>
</body>
</html>
