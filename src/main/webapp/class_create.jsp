<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">クラス登録</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス登録</h2>

            <c:if test="${not empty error}">
                <p style="color:red;">${error}</p>
            </c:if>

            <form action="ClassCreateExecute.action" method="post">
                <div class="mb-3">
                    クラス番号：
                    <input type="text" name="class_num">
                </div>

                <div class="mb-3">
                    <input type="submit" value="登録">
                </div>
            </form>

            <div class="mt-3">
                <a href="ClassList.action">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>