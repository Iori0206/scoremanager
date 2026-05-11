<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">クラス削除</c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス削除</h2>

            <c:if test="${not empty class_data}">
                <p>以下のクラスを削除します。よろしいですか？</p>
                <p>クラス番号：${class_data.class_num}</p>

                <form action="ClassDeleteExecute.action" method="post">
                    <input type="hidden" name="class_num" value="${class_data.class_num}">
                    <div class="mb-3">
                        <input type="submit" value="削除">
                    </div>
                </form>
            </c:if>

            <div class="mt-3">
                <a href="ClassList.action">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>