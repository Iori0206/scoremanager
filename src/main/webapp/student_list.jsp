<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:import url="/common/base.jsp">
    <c:param name="title">学生管理</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生管理</h2>

            <form action="StudentList.action" method="get" class="mb-4">
                <div class="row mb-3">

                    <div class="col-3">
                        <label class="form-label">入学年度</label>
                        <select name="f1" class="form-select">
                            <option value="0">------</option>
                            <c:forEach var="y" items="${ent_year_list}">
                                <option value="${y}" ${y == f1 ? "selected" : ""}>${y}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-3">
                        <label class="form-label">クラス</label>
                        <select name="f2" class="form-select">
                            <option value="0">------</option>
                            <c:forEach var="c" items="${class_num_list}">
                                <option value="${c}" ${c == f2 ? "selected" : ""}>${c}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-3 d-flex align-items-end">
                        <div class="form-check">
                            <input type="checkbox" name="f3" value="t"
                                   class="form-check-input"
                                   ${f3 ? "checked" : ""}>
                            <label class="form-check-label">在学中</label>
                        </div>
                    </div>

                    <div class="col-3 d-flex align-items-end">
                        <button class="btn btn-primary" id="filter_button">絞込み</button>
                    </div>
                </div>
            </form>

            <div class="mb-3">
                <a href="StudentInsert.action" class="btn btn-success">新規登録</a>
            </div>

            <p>検索結果：${fn:length(students)}件</p>

            <table class="table table-bordered table-striped">
                <thead class="table-secondary">
                    <tr>
                        <th>入学年度</th>
                        <th>学生番号</th>
                        <th>氏名</th>
                        <th>クラス</th>
                        <th class="text-center">在学中</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty students}">
                            <tr>
                                <td colspan="6" class="text-center">学生情報が存在しませんでした</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="s" items="${students}">
                                <tr>
                                    <td>${s.entYear}</td>
                                    <td>${s.no}</td>
                                    <td>${s.name}</td>
                                    <td>${s.classNum}</td>
                                    <td class="text-center">
                                        <%-- 修正箇所：isAttend() メソッドに対応するプロパティ名は attend --%>
                                        <c:choose>
                                            <c:when test="${s.attend}">〇</c:when>
                                            <c:otherwise>×</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a href="StudentUpdate.action?no=${s.no}">変更</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </section>
    </c:param>
</c:import>