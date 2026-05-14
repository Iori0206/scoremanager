<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目情報登録</c:param>

    <c:param name="content">
        <section class="me-4" style="width: 100%; max-width: 1040px;">
            <h2 class="h3 mb-4 fw-normal bg-secondary bg-opacity-10 py-3 px-4"
                style="width: 100%; max-width: 1040px;">
                科目情報登録
            </h2>

            <form action="SubjectCreateExecute.action" method="post" style="width: 100%; max-width: 780px;">
                <div class="mb-3">
                    <label class="form-label">科目コード</label>
                    <input type="text"
                           name="cd"
                           class="form-control"
                           value="${cd}"
                           placeholder="科目コードを入力してください">
                    <c:if test="${not empty cdError}">
                        <div class="text-warning small mt-1">${cdError}</div>
                    </c:if>
                </div>

                <div class="mb-3">
                    <label class="form-label">科目名</label>
                    <input type="text"
                           name="name"
                           class="form-control"
                           value="${name}"
                           placeholder="科目名を入力してください">
                    <c:if test="${not empty nameError}">
                        <div class="text-warning small mt-1">${nameError}</div>
                    </c:if>
                </div>

                <div class="mt-4">
                    <button type="submit" class="btn btn-primary px-4">登録して終了</button>
                </div>

                <div class="mt-3">
                    <a href="SubjectList.action">戻る</a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>