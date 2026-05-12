<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:import url="/common/base.jsp">

    <c:param name="title">
        学生管理
    </c:param>

    <c:param name="content">

        <h3 class="mb-4">
            学生管理
        </h3>

        <form
            action="StudentList.action"
            method="get"
            class="mb-4">

            <div class="row mb-3">

                <div class="col-3">
                    <label class="form-label">
                        入学年度
                    </label>

                    <select
                        name="ent_year"
                        class="form-select">

                        <option value="">
                            ------
                        </option>

                        <c:forEach
                            var="y"
                            items="${ent_year_list}">

                            <option
                                value="${y}"
                                <c:if test="${y == ent_year}">
                                    selected="selected"
                                </c:if>
                            >
                                ${y}
                            </option>

                        </c:forEach>

                    </select>
                </div>

                <div class="col-3">
                    <label class="form-label">
                        クラス
                    </label>

                    <select
                        name="class_num"
                        class="form-select">

                        <option value="">
                            ------
                        </option>

                        <c:forEach
                            var="c"
                            items="${class_num_list}">

                            <option
                                value="${c}"
                                <c:if test="${c == class_num}">
                                    selected="selected"
                                </c:if>
                            >
                                ${c}
                            </option>

                        </c:forEach>

                    </select>
                </div>

                <div class="col-3 d-flex align-items-end">
                    <div class="form-check">
                        <input
                            type="checkbox"
                            name="is_attend"
                            value="1"
                            class="form-check-input"
                            <c:if test="${is_attend == '1'}">
                                checked="checked"
                            </c:if>
                        >

                        <label class="form-check-label">
                            在学中
                        </label>
                    </div>
                </div>

                <div class="col-3 d-flex align-items-end">
                    <button
                        type="submit"
                        class="btn btn-primary">
                        絞込み
                    </button>
                </div>

            </div>
        </form>

        <div class="mb-3">
            <a
                href="StudentInsert.action"
                class="btn btn-success">
                新規登録
            </a>
        </div>

        <p>
            検索結果：
            ${fn:length(students)}件
        </p>

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
                            <td
                                colspan="6"
                                class="text-center">
                                学生情報が存在しませんでした
                            </td>
                        </tr>
                    </c:when>

                    <c:otherwise>
                        <c:forEach
                            var="s"
                            items="${students}">
                            <tr>
                                <td>${s.entYear}</td>
                                <td>${s.no}</td>
                                <td>${s.name}</td>
                                <td>${s.classNum}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${s.attend}">
                                            ○
                                        </c:when>
                                        <c:otherwise>
                                            ×
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="StudentEdit.action?no=${s.no}">
                                        変更
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

    </c:param>

</c:import>