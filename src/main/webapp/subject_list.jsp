<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="bean.Subject" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科目一覧</title>
</head>
<body>

<h1>科目管理</h1>

<p><a href="Menu.action">メニュー</a></p>
<p><a href="SubjectCreate.action">新規登録</a></p>

<%
List<Subject> list = (List<Subject>)request.getAttribute("subjects");
%>

<table border="1">
    <tr>
        <th>科目コード</th>
        <th>科目名</th>
        <th>変更</th>
        <th>削除</th>
    </tr>

<%
if (list != null) {
    for (Subject s : list) {
%>
    <tr>
        <td><%= s.getCd() %></td>
        <td><%= s.getName() %></td>
        <td><a href="SubjectUpdate.action?cd=<%= s.getCd() %>">変更</a></td>
        <td><a href="SubjectDelete.action?cd=<%= s.getCd() %>">削除</a></td>
    </tr>
<%
    }
}
%>
</table>

</body>
</html>