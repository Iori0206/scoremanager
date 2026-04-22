<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:import url="/common/base.jsp">
    <c:param name="title">学生管理</c:param>

    <c:param name="content">
        <h3 class="mb-4">学生管理</h3>

        <!-- 絞込みフォーム -->
        <form action="StudentList.action" method="get" class="mb-4">

            <div class="row mb-3">
                <div class="col-3">
                    <label class="form-label">入学年度</label>
                    <select name="ent_year" class="form-select">
                        <option value="">------</option>
                        <c:forEach var="y" begin="2010" end="2030">
                            <option value="${y}" ${y == ent_year ? "selected" : ""}>${y}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-3">
                    <label class="form-label">クラス</label>
                    <select name="class_num" class="form-select">
                        <option value="">------</option>
                        <option value="201" ${class_num == "201" ? "selected" : ""}>201</option>
                        <option value="202" ${class_num == "202" ? "selected" : ""}>202</option>
                        <option value="203" ${class_num == "203" ? "selected" : ""}>203</option>
                    </select>
                </div>

                <div class="col-3 d-flex align-items-end">
                    <div class="form-check">
                        <input type="checkbox" name="is_attend" value="1"
                            class="form-check-input"
                            ${is_attend == "1" ? "checked" : ""}>
                        <label class="form-check-label">在学中</label>
                    </div>
                </div>

                <div class="col-3 d-flex align-items-end">
                    <button class="btn btn-primary">絞込み</button>
                </div>
            </div>

        </form>

        <div class="mb-3">
            <a href="StudentInsert.action" class="btn btn-success">新規登録</a>
        </div>

        <!-- 結果件数 -->
        <p>検索結果：${fn:length(students)}件</p>

        <!-- 一覧 -->
        <table class="table table-bordered table-striped">
            <thead class="table-secondary">
                <tr>
                    <th>入学年度</th>
                    <th>学生番号</th>
                    <th>氏名</th>
                    <th>クラス</th>
                    <th>在学中</th>
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
                                <td>
                                    <c:choose>
                                        <c:when test="${s.attend}">○</c:when>
                                        <c:otherwise>×</c:otherwise>
                                    </c:choose>
                                </td>
                                <td><a href="StudentEdit.action?no=${s.no}">変更</a></td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

    </c:param>
</c:import>