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
</head>
<body>

<div>
    <form action="index.jsp" method="post">
        <textarea name="article" id="input" cols="30" rows="10" wrap="soft">
            <jsp:getProperty name="keywordEx" property="article"/>
        </textarea>
        <input type="submit" value="submit"/>
    </form>
</div>
<div>
        <textarea name="keyWord" id="output" cols="30" rows="10">
            <jsp:getProperty name="keywordEx" property="keyWord"/>
        </textarea>
</div>
</body>
</html>
