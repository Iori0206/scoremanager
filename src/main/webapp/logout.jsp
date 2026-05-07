<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログアウト | 得点管理システム</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
    body {
        background-color: #f5f5f5;
    }
    .container-box {
        width: 500px;
        margin: 60px auto;
        background: white;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
    footer {
        text-align: center;
        margin-top: 40px;
        color: #777;
        font-size: 14px;
    }
</style>
</head>

<body>

<div class="container-box">
<!-- ログインとログアウトリンク -->
    <h3 class="mb-4">得点管理システム</h3>

    

    <div class="alert alert-success">
        ログアウトしました
    </div>

    <p><a href="Login.action">ログイン</a></p>

</div>

<footer>
    © 2026<br>
    熊本校
</footer>

</body>
</html>