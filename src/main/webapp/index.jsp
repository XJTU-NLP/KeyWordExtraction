<%--
  Created by IntelliJ IDEA.
  User: L
  Date: 2021/8/24
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>提取文章关键字保存到印象笔记中</title>
    <link rel="stylesheet" href="index.css">
</head>
<body>
<div class="container">
    <form class="box" action="res.jsp" method="post">
        <textarea class="input" name="article" id="input" placeholder="请输入需要提取的文本"></textarea>
        <input class="button" type="submit" value="submit"/>
    </form>
    <div class="box">
        <textarea class="input" name="keyWord" id="output" placeholder="提取的结果将在这里展示"></textarea>
        <button class="button">保存</button>
    </div>
</div>
</body>
</html>
