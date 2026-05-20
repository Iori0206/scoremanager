<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- システムエラー発生時は error.jsp へ遷移 --%>
<%@ page errorPage="error.jsp" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>得点管理システム</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        /* 全体のベーススタイル */
        body {
            background-color: #ffffff;
            font-family: "Helvetica Neue", Arial, "Hiragino Kaku Gothic ProN", "Hiragino Sans", Meiryo, sans-serif;
        }

        /* 画面上部の大きなヘッダー領域 */
        .system-header {
            background-color: #e6f0fa;
            padding: 20px 40px;
            font-size: 28px;
            font-weight: bold;
            color: #333;
            border-bottom: 1px solid #d0dfef;
        }

        /* ① ログインカード全体の配置・外枠 */
        .login-box {
            width: 460px;
            margin: 60px auto;
            background: white;
            border: 1px solid #e0e0e0;
            border-radius: 4px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.05);
            overflow: hidden;
        }

        /* ①  */
        .card-top-header {
            background-color: #f5f5f5;
            text-align: center;
            padding: 12px;
            font-size: 18px;
            font-weight: bold;
            color: #444;
            border-bottom: 1px solid #e0e0e0;
        }

        /* カード内部のフォーム余白 */
        .card-main-body {
            padding: 35px 40px;
        }

        /* 認証エラーメッセージ */
        .error-list {
            color: #dc3545;
            font-size: 13px;
            margin-bottom: 20px;
            padding-left: 20px;
            text-align: left;
        }

        /* ②③ */
        .custom-input-group {
            background-color: #eaf2fc;
            border-radius: 6px;
            padding: 6px 14px;
            margin-bottom: 18px;
        }

        
        .custom-input-group label {
            display: block;
            font-size: 11px;
            color: #778899;
            margin-bottom: 1px;
            font-weight: bold;
        }

        .custom-input-group .form-control {
            background: transparent;
            border: none;
            padding: 2px 0;
            font-size: 14px;
            color: #333;
            box-shadow: none;
        }
        
        /* フォーカス時の調整 */
        .custom-input-group .form-control:focus {
            background: transparent;
            border: none;
            box-shadow: none;
        }

        .custom-input-group .form-control::placeholder {
            color: #a0abba;
            font-size: 13px;
        }

        /* ④⑤ パスワードを表示チェックボックスの行 */
        .form-check {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 4px;
            margin-bottom: 25px;
            font-size: 14px;
        }
        
        .form-check-input {
            margin-top: 0;
            width: 16px;
            height: 16px;
            cursor: pointer;
        }

        /* ⑥ ログインボタンのカスタム */
        .btn-custom-login {
            background-color: #0066ff;
            border: none;
            border-radius: 6px;
            padding: 8px 0;
            font-size: 16px;
            font-weight: bold;
            width: 35% !important;
            margin: 0 auto;
            display: block;
        }

        .btn-custom-login:hover {
            background-color: #0052cc;
        }

        /* フッター領域 */
        .footer {
            text-align: center;
            margin-top: 50px;
            color: #888;
            font-size: 13px;
            line-height: 1.6;
        }
    </style>
</head>

<body>

    <div class="system-header">
        得点管理システム
    </div>

    <div class="login-box">
        <div class="card-top-header">ログイン</div>

        <div class="card-main-body">
            
            <c:if test="${not empty error}">
                <ul class="error-list">
                    <li>${error}</li>
                </ul>
            </c:if>

            <form action="Login.action" method="post">

                <div class="custom-input-group">
                    <label for="school_cd">ＩＤ</label>
                    <input type="text" 
                           id="school_cd"
                           name="school_cd" 
                           class="form-control" 
                           maxlength="10" 
                           required 
                           placeholder="半角でご入力ください"
                           style="ime-mode: disabled; -webkit-ime-mode: disabled;">
                </div>

                <div class="custom-input-group">
                    <label for="pw">パスワード</label>
                    <input type="password" 
                           id="pw" 
                           name="password" 
                           class="form-control" 
                           maxlength="30"
                           required
                           placeholder="30文字以内の半角英数字でご入力ください"
                           style="ime-mode: disabled; -webkit-ime-mode: disabled;">
                </div>

                <div class="form-check">
                    <input type="checkbox" class="form-check-input" id="showPw"
                           onclick="document.getElementById('pw').type = this.checked ? 'text' : 'password'">
                    <label class="form-check-label" for="showPw">パスワードを表示</label>
                </div>

                <button class="btn btn-primary btn-custom-login">ログイン</button>

            </form>
        </div>
    </div>

    <div class="footer">
        © 2023 TIC<br>
        大原学園
    </div>

</body>
</html>