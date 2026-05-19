<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:import url="/common/base.jsp">
    <c:param name="title">学生管理</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <%-- ① タイトルエリア：fw-bold を追加して太く修正 --%>
            <div class="bg-secondary bg-opacity-10 py-3 px-4 mb-1">
                <h2 class="h3 mb-0 fw-bold">学生管理</h2>
            </div>

            <%-- ⑧ 新規登録リンク --%>
            <div class="text-end mb-1">
                <a href="StudentInsert.action" class="text-primary text-decoration-underline" style="font-size: 0.9rem;">新規登録</a>
            </div>

            <%-- ②〜⑨ 検索フィルタエリア --%>
            <form action="StudentList.action" method="get" class="p-4 border rounded mb-4 bg-white shadow-sm">
                <div class="row align-items-end">
                    <%-- ②④ 入学年度 --%>
                    <div class="col-md-4">
                        <label class="form-label small fw-bold">入学年度</label>
                        <select name="f1" class="form-select">
                            <option value="0">-------</option>
                            <c:forEach var="y" items="${ent_year_list}">
                                <option value="${y}" ${y == f1 ? "selected" : ""}>${y}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <%-- ③⑤ クラス --%>
                    <div class="col-md-4">
                        <label class="form-label small fw-bold">クラス</label>
                        <select name="f2" class="form-select">
                            <option value="0">-------</option>
                            <c:forEach var="c" items="${class_num_list}">
                                <option value="${c}" ${c == f2 ? "selected" : ""}>${c}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <%-- ⑥⑦ 在学中 --%>
                    <div class="col-md-2">
                        <div class="form-check mb-2">
                            <input type="checkbox" name="f3" value="t"
                                   class="form-check-input" id="in_school_check"
                                   ${f3 ? "checked" : ""}>
                            <label class="form-check-label" for="in_school_check">在学中</label>
                        </div>
                    </div>

                    <%-- ⑨ 絞込みボタン --%>
                    <div class="col-md-2 text-end">
                        <button type="submit" class="btn btn-secondary px-4 w-100" id="filter_button" style="background-color: #6c757d; border: none;">絞込み</button>
                    </div>
                </div>
            </form>

            <%-- メッセージ表示 --%>
            <c:if test="${empty students}">
                <p class="ms-1 mb-3 text-danger">学生情報が存在しませんでした</p>
            </c:if>

            <%-- No.10 検索結果件数 --%>
            <p class="ms-1 mb-3">検索結果：${fn:length(students)}件</p>

            <%-- テーブルエリア --%>
            <c:if test="${not empty students}">
                <table class="table table-bordered align-middle">
                    <thead class="table-light">
                        <tr>
                            <th style="width: 15%;">入学年度</th>
                            <th style="width: 15%;">学生番号</th>
                            <th style="width: 25%;">氏名</th>
                            <th style="width: 15%;">クラス</th>
                            <th class="text-center" style="width: 15%;">在学中</th>
                            <th style="width: 15%;"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${students}">
                            <tr>
                                <td>${s.entYear}</td>
                                <td>${s.no}</td>
                                <td>${s.name}</td>
                                <td>${s.classNum}</td>
                                <td class="text-center">
                                    <c:choose>
                                        <c:when test="${s.attend}">〇</c:when>
                                        <c:otherwise>×</c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-center">
                                    <a href="StudentUpdate.action?no=${s.no}" class="text-primary text-decoration-underline">変更</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </section>
    </c:param>
</c:import>