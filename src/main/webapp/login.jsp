<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>学生管理システム ログイン</title>

    <!-- Bootstrap 読み込み（必要なら変更OK） -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #f5f5f5;
        }
        .login-box {
            width: 380px;
            margin: 80px auto;
            padding: 30px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .footer {
            text-align: center;
            margin-top: 40px;
            color: #777;
            font-size: 14px;
        }
    </style>
</head>

<body>

    <div class="login-box">
        <h3 class="text-center mb-4">学生管理システム</h3>

        <form action="Login.action" method="post">

            <div class="mb-3">
                <label class="form-label">学校コード</label>
                <input type="text" name="school_cd" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">パスワード</label>
                <input type="password" name="password" id="pw" class="form-control" required>
            </div>

            <div class="form-check mb-3">
                <input type="checkbox" class="form-check-input" id="showPw"
                       onclick="document.getElementById('pw').type = this.checked ? 'text' : 'password'">
                <label class="form-check-label">パスワードを表示</label>
            </div>

            <button class="btn btn-primary w-100">ログイン</button>

            <c:if test="${not empty error}">
                <p class="text-danger mt-3 text-center">${error}</p>
            </c:if>

        </form>
    </div>

    <div class="footer">
        © 2026 熊本校<br>
        学生管理システム
    </div>

</body>
</html>