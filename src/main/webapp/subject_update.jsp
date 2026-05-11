<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="bean.Subject" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科目変更</title>
<style>
body {
    margin: 0;
    font-family: "Yu Gothic", "Meiryo", sans-serif;
    background-color: #f5f5f5;
    color: #333;
}

.wrapper {
    width: 1000px;
    margin: 20px auto;
}

.header {
    background-color: #eaf3ff;
    padding: 20px 25px;
    position: relative;
}

.header h1 {
    margin: 0;
    font-size: 30px;
    font-weight: bold;
}

.user-area {
    position: absolute;
    right: 25px;
    top: 25px;
    font-size: 13px;
}

.user-area a {
    color: #4a76d1;
    margin-left: 10px;
}

.main-area {
    display: flex;
    margin-top: 18px;
    min-height: 420px;
}

.sidebar {
    width: 150px;
    padding: 10px 15px 0 10px;
    border-right: 1px solid #ddd;
}

.sidebar a,
.sidebar .menu-title {
    display: block;
    margin-bottom: 12px;
    font-size: 13px;
}

.sidebar a {
    color: #4a76d1;
    text-decoration: underline;
}

.sidebar .menu-title {
    font-weight: bold;
    color: #333;
}

.content {
    flex: 1;
    padding-left: 20px;
}

.section-title {
    background-color: #eeeeee;
    padding: 10px 15px;
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 18px;
}

.form-area {
    width: 620px;
}

.form-row {
    margin-bottom: 18px;
}

.form-label {
    display: block;
    margin-bottom: 6px;
    font-size: 13px;
}

.form-input {
    width: 100%;
    box-sizing: border-box;
    padding: 10px;
    border: 1px solid #cfcfcf;
    border-radius: 4px;
    font-size: 14px;
    background: #fff;
}

.readonly-box {
    width: 100%;
    box-sizing: border-box;
    padding: 10px;
    border: 1px solid #cfcfcf;
    border-radius: 4px;
    font-size: 14px;
    background: #f8f8f8;
}

.error-message {
    color: orange;
    font-size: 13px;
    margin-top: 6px;
}

.button-row {
    margin-top: 20px;
}

.btn {
    background-color: #2f80ed;
    color: white;
    border: none;
    padding: 8px 18px;
    border-radius: 4px;
    font-size: 13px;
    cursor: pointer;
}

.back-link {
    display: inline-block;
    margin-top: 14px;
    color: #4a76d1;
    font-size: 13px;
}

.footer {
    background-color: #efefef;
    text-align: center;
    color: #777;
    font-size: 12px;
    padding: 14px 0;
    margin-top: 24px;
}
</style>
</head>
<body>

<%
Subject subject = (Subject)request.getAttribute("subject");
if (subject == null) {
    subject = new Subject();
}
String nameError = (String)request.getAttribute("nameError");
String notFoundError = (String)request.getAttribute("notFoundError");
%>

<div class="wrapper">

    <div class="header">
        <h1>得点管理システム</h1>
        <div class="user-area">
            管理者1様
            <a href="Logout.action">ログアウト</a>
        </div>
    </div>

    <div class="main-area">
        <div class="sidebar">
            <a href="Menu.action">メニュー</a>
            <a href="StudentList.action">学生管理</a>
            <div class="menu-title">成績管理</div>
            <a href="TestRegist.action">成績登録</a>
            <a href="ScoreSearch.action">成績参照</a>
            <a href="SubjectList.action">科目管理</a>
        </div>

        <div class="content">
            <div class="section-title">科目情報変更</div>

            <%
            if (notFoundError != null) {
            %>
                <div class="error-message"><%= notFoundError %></div>
            <%
            }
            %>

            <form action="SubjectUpdateExecute.action" method="post" class="form-area">
                <div class="form-row">
                    <label class="form-label">科目コード</label>
                    <div class="readonly-box"><%= subject.getCd() == null ? "" : subject.getCd() %></div>
                    <input type="hidden" name="cd" value="<%= subject.getCd() == null ? "" : subject.getCd() %>">
                </div>

                <div class="form-row">
                    <label class="form-label">科目名</label>
                    <input type="text" name="name" class="form-input"
                           value="<%= subject.getName() == null ? "" : subject.getName() %>">
                    <%
                    if (nameError != null) {
                    %>
                        <div class="error-message"><%= nameError %></div>
                    <%
                    }
                    %>
                </div>

                <div class="button-row">
                    <input type="submit" value="変更して終了" class="btn">
                </div>
            </form>

            <a href="SubjectList.action" class="back-link">戻る</a>
        </div>
    </div>

    <div class="footer">
        © 2023 TIC<br>
        大原学園
    </div>
</div>

</body>
</html>