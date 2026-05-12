<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">クラス一覧</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス一覧</h2>

            <div class="mb-3">
                <a href="ClassCreate.action">新規登録</a>
            </div>

            <c:if test="${not empty error}">
                <p style="color:red;">${error}</p>
            </c:if>

            <table class="table table-bordered">
                <tr>
                    <th>クラス番号</th>
                </tr>

                <c:choose>
                    <c:when test="${empty class_list}">
                        <tr>
                            <td>クラス情報が存在しませんでした。</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="c" items="${class_list}">
                            <tr>
                                <td>${c}</td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </table>

            <div class="mt-3">
                <a href="Menu.action">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>