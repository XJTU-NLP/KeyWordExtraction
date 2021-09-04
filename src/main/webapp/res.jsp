<%--
  Created by IntelliJ IDEA.
  User: L
  Date: 2021/8/25
  Time: 9:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="keywordEx" class="com.example.bean.keywordEx" scope="page">
    <jsp:setProperty name="keywordEx" property="article"/>
</jsp:useBean>
<html>
<head>
    <title>提取文章关键字保存到印象笔记中</title>
    <link rel="stylesheet" href="index.css">
</head>
<body>
<canvas id="canvas"></canvas>
<div class="header">
    KeyWordExtraction
</div>
<div class="container">
    <form class="box" action="res.jsp" method="post">
        <textarea class="input" name="article" id="input" placeholder="请输入需要提取的文本">
            <jsp:getProperty name="keywordEx" property="article"/>
        </textarea>
        <input class="button" type="submit" value="submit"/>
    </form>
    <div class="box">
        <textarea class="input" name="keyWord" id="output" placeholder="提取的结果将在这里展示">
            <jsp:getProperty name="keywordEx" property="keyWord"/>
        </textarea>
        <button class="button">保存</button>
    </div>
</div>
<script src="index.js"></script>
</body>
</html>