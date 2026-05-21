<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <%-- タイトルを空にすることで、base.jsp側が自動出力する余計な見出しやスペースを消去します --%>
    <c:param name="title"></c:param>

    <c:param name="content">
        <style>
            /* ─── 【大本命の修正】base.jsp の padding: 2rem を完全に打ち消します ─── */
            .content-area {
                padding: 0px !important;
            }

            .subject-done-page {
                width: 100%;
                max-width: 100%;
            }

            .subject-done-title {
                background: #f1f1f1;
                padding: 12px 22px;
                font-size: 18px;
                font-weight: bold;
                margin-bottom: 22px;
                width: 100%;
            }

            .subject-done-message {
                width: 95%; /* 1枚目の画像と同じように、少しだけ左右に隙間を作るための幅 */
                max-width: 100%; 
                background: #9fd3b0;
                color: #2e5339;
                padding: 10px 14px;
                font-size: 14px;
                box-sizing: border-box;
                margin: 0 auto;
                /* 1枚目のお手本画像と同じように、文字を少し右側に寄せるための左余白 */
                padding-left: 280px; 
            }

            .subject-done-links {
                margin-top: 70px;
                padding-left: 40px;
            }

            .subject-done-links a {
                color: #2f6df6;
                text-decoration: underline;
                font-size: 13px;
                margin-right: 70px; 
            }
        </style>

        <div class="subject-done-page">
            <div class="subject-done-title">科目情報登録</div>

            <div class="subject-done-message">登録が完了しました</div>

            <div class="subject-done-links">
                <a href="${pageContext.request.contextPath}/SubjectCreate.action">戻る</a>
                <a href="${pageContext.request.contextPath}/SubjectList.action">科目一覧</a>
            </div>
        </div>
    </c:param>
</c:import>