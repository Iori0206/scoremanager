<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目情報登録</c:param>
    <c:param name="content">
        <style>
            /* コンテンツエリア全体の余白とフォント設定 */
            .main-content {
                padding: 0 20px 20px 40px;
                font-family: "Yu Gothic", "Meiryo", sans-serif;
            }
            /* セクションタイトルの背景グレー */
            .title-box {
                background-color: #f8f9fa;
                padding: 15px;
                font-size: 20px;
                font-weight: bold;
                border-radius: 4px;
                margin-bottom: 30px;
            }
            /* 入力項目のラベル */
            .form-label {
                display: block;
                margin-bottom: 8px;
                font-size: 14px;
                color: #666;
            }
            /* 入力フィールド（画像に合わせて幅を調整） */
            .custom-input {
                width: 100%;
                max-width: 600px;
                padding: 10px;
                border: 1px solid #ced4da;
                border-radius: 4px;
                margin-bottom: 25px;
                font-size: 14px;
            }
            /* 青色の登録ボタン */
            .btn-submit {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 10px 25px;
                border-radius: 4px;
                font-size: 14px;
                cursor: pointer;
            }
            .btn-submit:hover {
                background-color: #0069d9;
            }
            /* 戻るリンク */
            .back-link {
                display: block;
                margin-top: 15px;
                color: #4a76d1;
                text-decoration: underline;
                font-size: 13px;
            }
            /* 著作権表記 */
            .footer-copy {
                text-align: center;
                margin-top: 100px;
                color: #888;
                font-size: 12px;
                line-height: 1.5;
            }
        </style>

        <div class="main-content">
            <div class="title-box">科目情報登録</div>

            <form action="SubjectCreateExecute.action" method="post">
                <label class="form-label">科目コード</label>
                <input type="text" name="cd" class="custom-input" placeholder="科目コードを入力してください" required>

                <label class="form-label">科目名</label>
                <input type="text" name="name" class="custom-input" placeholder="科目名を入力してください" required>

                <div>
                    <button type="submit" class="btn-submit">登録して終了</button>
                </div>
            </form>

            <a href="SubjectList.action" class="back-link">戻る</a>

            <div class="footer-copy">
                © 2023 TIC<br>
                大原学園
            </div>
        </div>
    </c:param>
</c:import>