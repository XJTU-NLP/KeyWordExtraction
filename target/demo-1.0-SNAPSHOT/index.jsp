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
</head>
<body>
    <div>
        <form action="res.jsp" method="post">
            <textarea name="article" id="input" cols="30" rows="10" wrap="soft"></textarea>
            <input type="submit" value="submit"/>
        </form>
    </div>
    <div>
        <textarea name="keyWord" id="output" cols="30" rows="10">
        </textarea>
        <button>保存</button>
    </div>

</body>
</html>
