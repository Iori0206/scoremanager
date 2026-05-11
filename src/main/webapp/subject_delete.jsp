<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目情報削除</c:param>
    <c:param name="content">
        <style>
            /* メインエリアの配置：サイドバーとコンテンツを横並びに */
            .main-area {
                display: flex;
                min-height: 500px;
            }
            
            /* コンテンツエリアのスタイル調整（画像a5bd15参照） */
            .content-container {
                flex: 1;
                padding: 0 30px 30px 40px;
            }

            .section-title {
                background-color: #f5f5f5; /* 画像の薄いグレー背景 */
                padding: 12px 20px;
                font-size: 18px;
                font-weight: bold;
                border-radius: 2px;
                margin-top: 15px;
                margin-bottom: 30px;
            }

            .alert-msg {
                font-size: 14px;
                margin-bottom: 25px;
                color: #333;
            }

            /* 情報表示ボックス（画像通り枠線のみのデザイン） */
            .info-field {
                width: 100%;
                max-width: 650px;
                padding: 12px 15px;
                border: 1px solid #ddd;
                background-color: #fff;
                margin-bottom: 12px;
                font-size: 14px;
                color: #333;
            }

            /* 削除ボタン（画像の赤色を忠実に再現） */
            .btn-delete-action {
                background-color: #d9534f;
                color: #ffffff;
                border: none;
                padding: 8px 20px;
                border-radius: 4px;
                font-size: 14px;
                cursor: pointer;
                margin-top: 15px;
            }

            .btn-delete-action:hover {
                background-color: #c9302c;
            }

            .link-back {
                display: inline-block;
                margin-top: 20px;
                color: #4a76d1;
                text-decoration: underline;
                font-size: 13px;
            }

            .footer-ohara {
                text-align: center;
                margin-top: 100px;
                padding: 20px 0;
                color: #888;
                font-size: 12px;
                line-height: 1.6;
            }
        </style>

        <div class="main-area">
            <div class="content-container">
                <div class="section-title">科目情報削除</div>

                <p class="alert-msg">この科目を削除しますか？</p>

                <form action="SubjectDeleteExecute.action" method="post">
                    <input type="hidden" name="cd" value="${subject.cd}">
                    
                    <div class="info-field">
                        科目コード：${subject.cd}
                    </div>

                    <div class="info-field">
                        科目名：${subject.name}
                    </div>

                    <div>
                        <button type="submit" class="btn-delete-action">削除して終了</button>
                    </div>
                </form>

                <a href="SubjectList.action" class="link-back">戻る</a>

                <div class="footer-ohara">
                    © 2023 TIC<br>
                    大原学園
                </div>
            </div>
        </div>
    </c:param>
</c:import>