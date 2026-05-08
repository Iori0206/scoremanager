<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科目登録</title>
</head>
<body>

<h1>科目登録</h1>

<form action="SubjectCreateExecute.action" method="post">
    <p>科目コード：<input type="text" name="cd"></p>
    <p>科目名：<input type="text" name="name"></p>
    <p><input type="submit" value="登録"></p>
</form>

<p><a href="SubjectList.action">一覧へ戻る</a></p>

</body>
</html>