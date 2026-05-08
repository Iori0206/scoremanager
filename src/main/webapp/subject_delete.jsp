<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bean.Subject" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科目削除</title>
</head>
<body>

<%
Subject subject = (Subject)request.getAttribute("subject");
%>

<h1>科目削除</h1>

<p>この科目を削除しますか？</p>

<form action="SubjectDeleteExecute.action" method="post">
    <p>科目コード：<%= subject.getCd() %></p>
    <p>科目名：<%= subject.getName() %></p>
    <input type="hidden" name="cd" value="<%= subject.getCd() %>">
    <p><input type="submit" value="削除"></p>
</form>

<p><a href="SubjectList.action">一覧へ戻る</a></p>

</body>
</html>