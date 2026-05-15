<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目情報削除</c:param>

    <c:param name="content">
        <style>
            .subject-delete-page {
                width: 100%;
                max-width: 1100px;
            }

            .subject-delete-title {
                background: #f1f1f1;
                padding: 12px 22px;
                font-size: 18px;
                font-weight: bold;
                margin-bottom: 22px;
            }

            .subject-delete-message {
                font-size: 14px;
                margin-bottom: 34px;
                color: #333;
            }

            .subject-delete-error {
                color: orange;
                font-size: 13px;
                margin-bottom: 18px;
            }

            .subject-delete-btn-row {
                margin-bottom: 28px;
            }

            .subject-delete-btn {
                background-color: #dc3545;
                color: #ffffff;
                border: none;
                border-radius: 4px;
                padding: 8px 18px;
                font-size: 14px;
                cursor: pointer;
            }

            .subject-delete-back {
                display: inline-block;
                color: #2f6df6;
                text-decoration: underline;
                font-size: 13px;
            }
        </style>

        <div class="subject-delete-page">
            <div class="subject-delete-title">科目情報削除</div>

            <c:choose>
                <c:when test="${not empty subject}">
                    <p class="subject-delete-message">「${subject.name}(${subject.cd})」を削除してもよろしいですか</p>

                    <form action="SubjectDeleteExecute.action" method="post">
                        <input type="hidden" name="cd" value="${subject.cd}">

                        <div class="subject-delete-btn-row">
                            <button type="submit" class="subject-delete-btn">削除</button>
                        </div>
                    </form>
                </c:when>
                <c:otherwise>
                    <div class="subject-delete-error">${notFoundError}</div>
                </c:otherwise>
            </c:choose>

            <a href="SubjectList.action" class="subject-delete-back">戻る</a>
        </div>
    </c:param>
</c:import>