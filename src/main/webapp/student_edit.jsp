<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="bean.Student" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>学生情報変更</title>
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
    height: 108px;
    position: relative;
    box-sizing: border-box;
    padding: 26px 0 0 34px;
}
.top-header h1 {
    margin: 0;
    font-size: 34px;
    font-weight: bold;
}
.user-info {
    position: absolute;
    right: 30px;
    top: 40px;
    font-size: 15px;
}
.user-info a {
    color: #2f6df6;
    margin-left: 14px;
    text-decoration: underline;
}
.main-wrap {
    display: flex;
    min-height: 620px;
}
.sidebar {
    width: 215px;
    border-right: 1px solid #dcdcdc;
    padding: 26px 0 0 42px;
    box-sizing: border-box;
}
.sidebar a,
.sidebar .menu-label {
    display: block;
    margin-bottom: 26px;
    font-size: 15px;
    line-height: 1;
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
    padding: 28px 44px 0 44px;
    box-sizing: border-box;
}
.section-title {
    background: #eef5ff;
    height: 78px;
    box-sizing: border-box;
    padding: 18px 26px;
    font-size: 26px;
    font-weight: bold;
    margin-bottom: 22px;
}
.form-box {
    width: 700px;
}
.form-row {
    margin-bottom: 20px;
}
.form-label {
    display: block;
    margin-bottom: 8px;
    font-size: 15px;
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
.readonly-box {
    width: 100%;
    box-sizing: border-box;
    padding: 12px 14px;
    border: 1px solid #d5d5d5;
    border-radius: 4px;
    background: #f2f2f2;
    font-size: 15px;
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
    background: #efefef;
    border-top: 1px solid #dcdcdc;
    text-align: center;
    color: #777;
    font-size: 13px;
    line-height: 1.7;
    padding: 12px 0 14px 0;
    margin-top: 18px;
}
</style>
</head>
<body>

<%
Student student = (Student)request.getAttribute("student");
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
            <div class="section-title">学生情報変更</div>

            <form action="StudentUpdateExecute.action" method="post" class="form-box">
                <div class="form-row">
                    <label class="form-label">学生番号</label>
                    <div class="readonly-box"><%= student.getNo() %></div>
                    <input type="hidden" name="no" value="<%= student.getNo() %>">
                </div>

                <div class="form-row">
                    <label class="form-label">氏名</label>
                    <input type="text" name="name" class="form-input" value="<%= student.getName() %>">
                </div>

                <div class="form-row">
                    <label class="form-label">入学年度</label>
                    <input type="text" name="ent_year" class="form-input" value="<%= student.getEntYear() %>">
                </div>

                <div class="form-row">
                    <label class="form-label">クラス</label>
                    <input type="text" name="class_num" class="form-input" value="<%= student.getClassNum() %>">
                </div>

                <div class="form-row">
                    <label class="form-label">在学中</label>
                    <input type="checkbox" name="is_attend" value="1" <%= student.isAttend() ? "checked" : "" %>>
                </div>

                <input type="submit" value="変更" class="btn">
            </form>

            <a href="StudentList.action" class="back-link">戻る</a>
        </div>
    </div>

    <div class="footer">
        © 2023 TIC<br>
        大原学園
    </div>
</div>

</body>
</html>