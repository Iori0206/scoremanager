<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目情報変更</c:param>

    <c:param name="content">
        <section class="me-4" style="width: 100%; max-width: 1040px;">
            <h2 class="h3 mb-4 fw-normal bg-secondary bg-opacity-10 py-3 px-4"
                style="width: 100%; max-width: 1040px;">
                科目情報変更
            </h2>

            <div class="alert alert-success"
                 role="alert"
                 style="width: 100%; max-width: 780px;">
                変更が完了しました
            </div>

            <div class="mt-4" style="width: 100%; max-width: 780px;">
                <a href="SubjectList.action">科目一覧</a>
            </div>
        </section>
    </c:param>
</c:import>