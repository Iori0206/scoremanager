<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bean.Subject" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科目変更</title>
</head>
<body>

<%
Subject subject = (Subject)request.getAttribute("subject");
%>

<h1>科目変更</h1>

<form action="SubjectUpdateExecute.action" method="post">
    <p>科目コード：<input type="text" name="cd" value="<%= subject.getCd() %>" readonly></p>
    <p>科目名：<input type="text" name="name" value="<%= subject.getName() %>"></p>
    <p><input type="submit" value="変更"></p>
</form>

<p><a href="SubjectList.action">一覧へ戻る</a></p>

</body>
</html>