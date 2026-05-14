<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目情報削除</c:param>

    <c:param name="content">
        <section class="me-4 page-box">
            <h2 class="h3 mb-4 fw-normal bg-secondary bg-opacity-10 py-3 px-4 page-title-box">
                科目情報削除
            </h2>

            <div class="alert alert-success page-message-box" role="alert">
                削除が完了しました
            </div>

            <div class="mt-4 page-message-box">
                <a href="SubjectList.action">科目一覧</a>
            </div>
        </section>
    </c:param>
</c:import>