<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目情報変更</c:param>

    <c:param name="content">
        <style>
            .subject-form-page {
                width: 100%;
                max-width: 1100px;
            }

            .subject-form-title {
                background: #f1f1f1;
                padding: 12px 22px;
                font-size: 18px;
                font-weight: bold;
                margin-bottom: 22px;
            }

            .subject-form-label {
                display: block;
                margin-bottom: 8px;
                color: #666;
                font-size: 14px;
            }

            .subject-code-view {
                width: 100%;
                max-width: 980px;
                margin-bottom: 10px;
                font-size: 14px;
                color: #333;
            }

            .subject-form-input {
                width: 100%;
                max-width: 980px;
                padding: 10px 12px;
                border: 1px solid #ced4da;
                border-radius: 4px;
                font-size: 14px;
                box-sizing: border-box;
                margin-bottom: 10px;
            }

            .subject-form-error {
                color: orange;
                font-size: 13px;
                margin-bottom: 20px;
            }

            .subject-form-btn {
                background-color: #0d6efd;
                color: #fff;
                border: none;
                border-radius: 4px;
                padding: 8px 18px;
                font-size: 14px;
                cursor: pointer;
            }

            .subject-form-back {
                display: inline-block;
                margin-top: 14px;
                color: #2f6df6;
                text-decoration: underline;
                font-size: 13px;
            }
        </style>

        <c:set var="displayCd" value="${empty cd ? subject.cd : cd}" />
        <c:set var="displayName" value="${empty name ? subject.name : name}" />

        <div class="subject-form-page">
            <div class="subject-form-title">科目情報変更</div>

            <form action="SubjectUpdateExecute.action" method="post">
                <label class="subject-form-label">科目コード</label>
                <div class="subject-code-view">${displayCd}</div>
                <input type="hidden" name="cd" value="${displayCd}">

                <c:if test="${not empty notFoundError}">
                    <div class="subject-form-error">${notFoundError}</div>
                </c:if>

                <label class="subject-form-label">科目名</label>
                <input
                    type="text"
                    name="name"
                    class="subject-form-input"
                    value="${displayName}"
                    placeholder="科目名を入力してください"
                    maxlength="20"
                    required>

                <c:if test="${not empty nameError}">
                    <div class="subject-form-error">${nameError}</div>
                </c:if>

                <div>
                    <button type="submit" class="subject-form-btn">変更</button>
                </div>
            </form>

            <a href="SubjectList.action" class="subject-form-back">戻る</a>
        </div>
    </c:param>
</c:import>