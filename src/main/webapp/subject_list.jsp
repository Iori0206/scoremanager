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

.top-link {
    text-align: right;
    margin-bottom: 18px;
    font-size: 15px;
}

.top-link a {
    color: #2f6df6;
    text-decoration: underline;
}

.table-box {
    background: #fff;
    border: 1px solid #ddd;
}

.subject-table {
    width: 100%;
    border-collapse: collapse;
    table-layout: fixed;
}

.subject-table th,
.subject-table td {
    border-bottom: 1px solid #ddd;
    padding: 18px 30px;
    font-size: 16px;
    text-align: left;
}

.subject-table th {
    background: #fff;
    font-weight: bold;
}

.subject-table tr:last-child td {
    border-bottom: none;
}

.code-col {
    width: 18%;
}

.name-col {
    width: 52%;
}

.action-col {
    width: 15%;
    text-align: center !important;
}

.subject-table a {
    color: #2f6df6;
    text-decoration: underline;
}

.empty-row td {
    height: 120px;
    border-bottom: none;
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
List<Subject> list = (List<Subject>)request.getAttribute("list");
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
        // 名前が取れなかったときは固定表示
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
            <div class="section-title">科目管理</div>

            <div class="top-link">
                <a href="SubjectCreate.action">新規登録</a>
            </div>

            <div class="table-box">
                <table class="subject-table">
                    <tr>
                        <th class="code-col">科目コード</th>
                        <th class="name-col">科目名</th>
                        <th class="action-col"></th>
                        <th class="action-col"></th>
                    </tr>

                    <%
                    if (list != null && !list.isEmpty()) {
                        for (Subject s : list) {
                    %>
                    <tr>
                        <td><%= s.getCd() %></td>
                        <td><%= s.getName() %></td>
                        <td class="action-col">
                            <a href="SubjectUpdate.action?cd=<%= s.getCd() %>">変更</a>
                        </td>
                        <td class="action-col">
                            <a href="SubjectDelete.action?cd=<%= s.getCd() %>">削除</a>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr class="empty-row">
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    <%
                    }
                    %>
                </table>
            </div>
        </div>
    </div>

    <div class="footer">
        © 2023 TIC 大原学園
    </div>
</div>

</body>
</html>