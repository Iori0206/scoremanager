<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">学生情報登録</c:param>

    <c:param name="content">
        <section class="me-4">
            <%-- タイトルエリア：fw-bold を追加し、余白を py-3 px-4 に調整 --%>
            <div class="bg-secondary bg-opacity-10 py-3 px-4 mb-4">
                <h2 class="h3 mb-0 fw-bold">学生情報登録</h2>
            </div>

            <%-- 完了メッセージ --%>
            <div class="ms-4">
                <div class="alert alert-success mt-4 mb-4 w-75" role="alert">
                    登録が完了しました
                </div>

                <div class="mt-3">
                    <a href="StudentInsert.action" class="me-4 text-decoration-none">戻る</a>
                    <a href="StudentList.action" class="text-decoration-none">学生一覧</a>
                </div>
            </div>
        </section>
    </c:param>
</c:import>