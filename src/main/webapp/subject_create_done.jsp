<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科目登録完了</title>
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

.success-box {
    background-color: #9fd3b0;
    color: #2e5339;
    padding: 10px 14px;
    font-size: 13px;
    margin-bottom: 20px;
    width: 620px;
    box-sizing: border-box;
}

.link-row {
    margin-top: 12px;
}

.back-link {
    display: inline-block;
    margin-right: 24px;
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
            <div class="section-title">科目情報登録</div>

            <div class="success-box">
                登録が完了しました
            </div>

            <div class="link-row">
                <a href="SubjectCreate.action" class="back-link">戻る</a>
                <a href="SubjectList.action" class="back-link">科目一覧</a>
            </div>
        </div>
    </div>

    <div class="footer">
        © 2023 TIC<br>
        大原学園
    </div>
</div>

</body>
</html>