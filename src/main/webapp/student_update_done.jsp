<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">学生情報変更</c:param>

    <c:param name="content">
        <section class="me-4">
           
            <div class="bg-secondary bg-opacity-10 py-3 px-4 mb-4">
                <h2 class="h3 mb-0 fw-bold">学生情報変更</h2>
            </div>

            <div class="ms-4">
                <%-- 完了メッセージ --%>
                <div class="alert alert-success mt-4 mb-4 w-75" role="alert">
                    変更が完了しました
                </div>

                <div class="mt-4">
                    
                    <%-- 学生一覧リンク --%>
                    <a href="StudentList.action" class="text-decoration-none">学生一覧</a>
                </div>
            </div>
        </section>
    </c:param>
</c:import>