<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">学生情報登録</c:param>

    <c:param name="content">
        <section class="me-4">
            <%-- No.1 画面タイトル：fw-bold で太く修正 --%>
            <div class="bg-secondary bg-opacity-10 py-3 px-4 mb-4">
                <h2 class="h3 mb-0 fw-bold">学生情報登録</h2>
            </div>

            <form action="StudentInsert.action" method="post" class="ms-4 w-75">
                <%-- 入学年度 --%>
                <div class="mb-3">
                    <label class="form-label">入学年度</label>
                    <div class="col-md-5">
                        <select name="ent_year" class="form-select">
                            <option value="">--------</option>
                            <c:forEach var="y" items="${ent_year_list}">
                                <option value="${y}" ${y == ent_year ? "selected" : ""}>${y}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <c:if test="${not empty entYearError}">
                        <div class="text-danger small mt-1">${entYearError}</div>
                    </c:if>
                </div>

                <%-- 学生番号 --%>
                <div class="mb-3">
                    <label class="form-label">学生番号</label>
                    <div class="col-md-7">
                        <input type="text" name="no" class="form-control" value="${no}"
                               placeholder="学生番号を入力してください" 
                               maxlength="10" required>
                    </div>
                    <c:if test="${not empty noError}">
                        <div class="text-danger small mt-1">${noError}</div>
                    </c:if>
                </div>

                <%-- 氏名 --%>
                <div class="mb-3">
                    <label class="form-label">氏名</label>
                    <div class="col-md-9">
                        <input type="text" name="name" class="form-control" value="${name}"
                               placeholder="氏名を入力してください" 
                               maxlength="30" required>
                    </div>
                    <c:if test="${not empty nameError}">
                        <div class="text-danger small mt-1">${nameError}</div>
                    </c:if>
                </div>

                <%-- クラス --%>
                <div class="mb-4">
                    <label class="form-label">クラス</label>
                    <div class="col-md-5">
                        <select name="class_num" class="form-select">
                            <option value="">--------</option>
                            <c:forEach var="c" items="${class_num_list}">
                                <option value="${c}" ${c == class_num ? "selected" : ""}>${c}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <c:if test="${not empty classNumError}">
                        <div class="text-danger small mt-1">${classNumError}</div>
                    </c:if>
                </div>

                <%-- ボタンエリア --%>
                <div class="mt-4 pt-2">
                    <button type="submit" class="btn btn-secondary px-4">登録して終了</button>
                </div>

                <div class="mt-3">
                    <a href="StudentList.action" class="text-decoration-none">戻る</a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>