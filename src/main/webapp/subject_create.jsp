<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目情報登録</c:param>

    <c:param name="content">
        <style>
            .main-content {
                padding: 0 20px 20px 40px;
                font-family: "Yu Gothic", "Meiryo", sans-serif;
            }

            .title-box {
                background-color: #f8f9fa;
                padding: 15px;
                font-size: 20px;
                font-weight: bold;
                border-radius: 4px;
                margin-bottom: 30px;
            }

            .form-label {
                display: block;
                margin-bottom: 8px;
                font-size: 14px;
                color: #666;
            }

            .custom-input {
                width: 100%;
                max-width: 600px;
                padding: 10px;
                border: 1px solid #ced4da;
                border-radius: 4px;
                margin-bottom: 8px;
                font-size: 14px;
                box-sizing: border-box;
            }

            .error-message {
                color: orange;
                font-size: 13px;
                margin-bottom: 18px;
            }

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

            .back-link {
                display: block;
                margin-top: 15px;
                color: #4a76d1;
                text-decoration: underline;
                font-size: 13px;
            }

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
                <input type="text" name="cd" class="custom-input" value="${subject.cd}" placeholder="科目コードを入力してください">
                <c:if test="${not empty cdError}">
                    <div class="error-message">${cdError}</div>
                </c:if>

                <label class="form-label">科目名</label>
                <input type="text" name="name" class="custom-input" value="${subject.name}" placeholder="科目名を入力してください">
                <c:if test="${not empty nameError}">
                    <div class="error-message">${nameError}</div>
                </c:if>

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