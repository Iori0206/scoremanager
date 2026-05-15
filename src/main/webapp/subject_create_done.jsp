<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目情報登録</c:param>

    <c:param name="content">
        <style>
            .subject-done-page {
                width: 100%;
                max-width: 1100px;
            }

            .subject-done-title {
                background: #f1f1f1;
                padding: 12px 22px;
                font-size: 18px;
                font-weight: bold;
                margin-bottom: 22px;
            }

            .subject-done-message {
                width: 100%;
                max-width: 820px;
                background: #9fd3b0;
                color: #2e5339;
                padding: 10px 14px;
                font-size: 14px;
            }

            .subject-done-links {
                margin-top: 70px;
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
                <a href="SubjectCreate.action">戻る</a>
                <a href="SubjectList.action">科目一覧</a>
            </div>
        </div>
    </c:param>
</c:import>