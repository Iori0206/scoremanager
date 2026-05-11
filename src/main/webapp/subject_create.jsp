<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="bean.Subject" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科目登録</title>
<style>
body {
    margin: 0;
    font-family: "Yu Gothic", "Meiryo", sans-serif;
    background-color: #f7f7f7;
    color: #333;
}

.page {
    width: 100%;
    min-height: 100vh;
}

.top-header {
    background: #ffffff;
    border-bottom: 1px solid #ddd;
    padding: 24px 30px 20px 30px;
    position: relative;
}

.top-header h1 {
    margin: 0;
    font-size: 34px;
    font-weight: bold;
    letter-spacing: 1px;
}

.user-info {
    position: absolute;
    right: 30px;
    top: 36px;
    font-size: 15px;
}

.user-info a {
    color: #2f6df6;
    margin-left: 14px;
    text-decoration: underline;
}

.main-wrap {
    display: flex;
    min-height: 670px;
}

.sidebar {
    width: 210px;
    background: #fafafa;
    border-right: 1px solid #ddd;
    padding: 34px 0 0 42px;
    box-sizing: border-box;
}

.sidebar a,
.sidebar .menu-label {
    display: block;
    margin-bottom: 24px;
    font-size: 16px;
}

.sidebar a {
    color: #2f6df6;
    text-decoration: underline;
}

.sidebar .menu-label {
    color: #222;
    font-weight: bold;
    margin-bottom: 18px;
}

.content {
    flex: 1;
    padding: 34px 38px 0 38px;
    box-sizing: border-box;
}

.section-title {
    background: #eef5ff;
    padding: 18px 28px;
    font-size: 28px;
    font-weight: bold;
    margin-bottom: 26px;
}

.form-box {
    width: 700px;
}

.form-row {
    margin-bottom: 22px;
}

.form-label {
    display: block;
    font-size: 15px;
    margin-bottom: 8px;
}

.form-input {
    width: 100%;
    box-sizing: border-box;
    padding: 12px 14px;
    border: 1px solid #d5d5d5;
    border-radius: 4px;
    background: #fff;
    font-size: 15px;
}

.error-message {
    color: #f39c12;
    font-size: 14px;
    margin-top: 8px;
}

.button-row {
    margin-top: 10px;
}

.btn {
    background: #2f80ed;
    color: #fff;
    border: none;
    border-radius: 4px;
    padding: 9px 18px;
    font-size: 14px;
    cursor: pointer;
}

.back-link {
    display: inline-block;
    margin-top: 16px;
    color: #2f6df6;
    text-decoration: underline;
    font-size: 14px;
}

.footer {
    text-align: center;
    color: #777;
    font-size: 14px;
    padding: 18px 0 26px 0;
    background: #f2f2f2;
    border-top: 1px solid #ddd;
}
</style>
</head>
<body>

<%
Subject subject = (Subject)request.getAttribute("subject");
if (subject == null) {
    subject = new Subject();
}

String cd = subject.getCd() == null ? "" : subject.getCd();
String name = subject.getName() == null ? "" : subject.getName();

String cdError = (String)request.getAttribute("cdError");
String nameError = (String)request.getAttribute("nameError");

String userName = "管理者1";
Object userObj = session.getAttribute("user");
if (userObj != null) {
    try {
        java.lang.reflect.Method m = userObj.getClass().getMethod("getName");
        Object result = m.invoke(userObj);
        if (result != null) {
            userName = result.toString();
        }
    } catch (Exception e) {
        // 取れないときは固定表示
    }
}
%>

<div class="page">
    <div class="top-header">
        <h1>得点管理システム</h1>
        <div class="user-info">
            <%= userName %>様
            <a href="Logout.action">ログアウト</a>
        </div>
    </div>

    <div class="main-wrap">
        <div class="sidebar">
            <a href="Menu.action">メニュー</a>
            <a href="StudentList.action">学生管理</a>

            <div class="menu-label">成績管理</div>
            <a href="TestRegist.action">成績登録</a>
            <a href="ScoreSearch.action">成績参照</a>
            <a href="SubjectList.action">科目管理</a>
            <a href="#">クラス管理</a>
        </div>

        <div class="content">
            <div class="section-title">科目情報登録</div>

            <form action="SubjectCreateExecute.action" method="post" class="form-box">
                <div class="form-row">
                    <label class="form-label">科目コード</label>
                    <input type="text" name="cd" class="form-input" value="<%= cd %>" placeholder="科目コードを入力してください">
                    <%
                    if (cdError != null) {
                    %>
                        <div class="error-message"><%= cdError %></div>
                    <%
                    }
                    %>
                </div>

                <div class="form-row">
                    <label class="form-label">科目名</label>
                    <input type="text" name="name" class="form-input" value="<%= name %>" placeholder="科目名を入力してください">
                    <%
                    if (nameError != null) {
                    %>
                        <div class="error-message"><%= nameError %></div>
                    <%
                    }
                    %>
                </div>

                <div class="button-row">
                    <input type="submit" value="登録" class="btn">
                </div>
            </form>

            <a href="SubjectList.action" class="back-link">戻る</a>
        </div>
    </div>

    <div class="footer">
        © 2023 TIC 大原学園
    </div>
</div>

</body>
</html>