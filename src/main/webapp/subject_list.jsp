<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.Subject" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科目一覧</title>
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
    margin-bottom: 15px;
}

.top-link {
    text-align: right;
    margin-bottom: 10px;
    font-size: 13px;
}

.top-link a {
    color: #4a76d1;
}

.list-table {
    width: 100%;
    border-collapse: collapse;
    background-color: white;
    font-size: 13px;
}

.list-table th,
.list-table td {
    border-bottom: 1px solid #ddd;
    padding: 10px 12px;
    text-align: left;
}

.list-table th.action,
.list-table td.action {
    width: 60px;
    text-align: center;
}

.list-table a {
    color: #4a76d1;
}

.footer {
    background-color: #efefef;
    text-align: center;
    color: #777;
    font-size: 12px;
    padding: 14px 0;
    margin-top: 24px;
}

.empty-box {
    height: 180px;
}
</style>
</head>
<body>

<%
List<Subject> list = (List<Subject>)request.getAttribute("list");
Object user = session.getAttribute("user");
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
            <div class="section-title">科目管理</div>

            <div class="top-link">
                <a href="SubjectCreate.action">新規登録</a>
            </div>

            <table class="list-table">
                <tr>
                    <th>科目コード</th>
                    <th>科目名</th>
                    <th class="action"></th>
                    <th class="action"></th>
                </tr>

                <%
                if (list != null) {
                    for (Subject s : list) {
                %>
                <tr>
                    <td><%= s.getCd() %></td>
                    <td><%= s.getName() %></td>
                    <td class="action">
                        <a href="SubjectUpdate.action?cd=<%= s.getCd() %>">変更</a>
                    </td>
                    <td class="action">
                        <a href="SubjectDelete.action?cd=<%= s.getCd() %>">削除</a>
                    </td>
                </tr>
                <%
                    }
                }
                %>
            </table>

            <%
            if (list == null || list.size() == 0) {
            %>
                <div class="empty-box"></div>
            <%
            }
            %>
        </div>
    </div>

    <div class="footer">
        © 2023 TIC<br>
        大原学園
    </div>
</div>

</body>
</html>