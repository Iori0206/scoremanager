<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">学生情報登録</c:param>

    <c:param name="content">
        <section class="me-4">
            <%-- No.1 画面タイトル --%>
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                学生情報登録
            </h2>

            <form action="StudentInsert.action" method="post" class="w-75">
                <div class="mb-3">
                    <%-- No.2-3 入学年度 --%>
                    <label class="form-label">入学年度</label>
                    <select name="ent_year" class="form-select">
                        <option value="">--------</option>
                        <c:forEach var="y" items="${ent_year_list}">
                            <option value="${y}" ${y == ent_year ? "selected" : ""}>${y}</option>
                        </c:forEach>
                    </select>
                    <c:if test="${not empty entYearError}">
                        <div class="text-warning small mt-1">${entYearError}</div>
                    </c:if>
                </div>

                <div class="mb-3">
                    <%-- No.4-5 学生番号 --%>
                    <label class="form-label">学生番号</label>
                    <input type="text" name="no" class="form-control" value="${no}"
                           placeholder="学生番号を入力してください" 
                           maxlength="10" required>
                    <c:if test="${not empty noError}">
                        <div class="text-warning small mt-1">${noError}</div>
                    </c:if>
                </div>

                <div class="mb-3">
                    <%-- No.6-7 氏名 --%>
                    <label class="form-label">氏名</label>
                    <input type="text" name="name" class="form-control" value="${name}"
                           placeholder="氏名を入力してください" 
                           maxlength="30" required>
                    <c:if test="${not empty nameError}">
                        <div class="text-warning small mt-1">${nameError}</div>
                    </c:if>
                </div>

                <div class="mb-3">
                    <%-- No.8-9 クラス --%>
                    <label class="form-label">クラス</label>
                    <select name="class_num" class="form-select">
                        <option value="">--------</option>
                        <c:forEach var="c" items="${class_num_list}">
                            <option value="${c}" ${c == class_num ? "selected" : ""}>${c}</option>
                        </c:forEach>
                    </select>
                    <c:if test="${not empty classNumError}">
                        <div class="text-warning small mt-1">${classNumError}</div>
                    </c:if>
                </div>

                <div class="mt-4">
                    <%-- No.10 登録して終了ボタン --%>
                    <button type="submit" class="btn btn-secondary">登録して終了</button>
                </div>

                <div class="mt-3">
                    <%-- No.11 戻るリンク --%>
                    <a href="StudentList.action">戻る</a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>