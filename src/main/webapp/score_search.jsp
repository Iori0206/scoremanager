<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">成績参照</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

            <c:if test="${not empty error}">
                <div class="alert alert-danger mb-3">${error}</div>
            </c:if>

            <div class="border bg-white p-4 mb-4">

                <!-- 科目情報から検索 -->
                <form action="ScoreSearch.action" method="get" class="mb-3 pb-3 border-bottom">
                    <div class="row align-items-end">
                        <div class="col-2">
                            <label class="form-label mb-0">科目情報</label>
                        </div>

                        <div class="col-2">
                            <label class="form-label">入学年度</label>
                            <select name="ent_year" class="form-select">
                                <option value="">------</option>
                                <c:forEach var="y" items="${years}">
                                    <option value="${y}" ${y == ent_year ? "selected" : ""}>${y}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-2">
                            <label class="form-label">クラス</label>
                            <select name="class_num" class="form-select">
                                <option value="">------</option>
                                <c:forEach var="c" items="${classes}">
                                    <option value="${c}" ${c == class_num ? "selected" : ""}>${c}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-3">
                            <label class="form-label">科目</label>
                            <select name="subject_cd" class="form-select">
                                <option value="">--------</option>
                                <c:forEach var="s" items="${subjects}">
                                    <option value="${s.cd}" ${s.cd == subject_cd ? "selected" : ""}>${s.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-2">
                            <button type="submit" class="btn btn-secondary">検索</button>
                        </div>
                    </div>
                </form>

                <!-- 学生情報から検索 -->
                <form action="ScoreSearch.action" method="get">
                    <div class="row align-items-end">
                        <div class="col-2">
                            <label class="form-label mb-0">学生情報</label>
                        </div>

                        <div class="col-4">
                            <label class="form-label">学生番号</label>
                            <select name="student_no" class="form-select">
                                <option value="">学生番号を選択してください</option>
                                <c:forEach var="stu" items="${students}">
                                    <option value="${stu.no}" ${stu.no == student_no ? "selected" : ""}>
                                        ${stu.no}：${stu.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-2">
                            <button type="submit" class="btn btn-secondary">検索</button>
                        </div>
                    </div>
                </form>

                <p class="mt-3 mb-0 text-info">
                    科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
                </p>
            </div>

            <!-- 学生別結果 -->
            <c:if test="${not empty tests and not empty student}">
                <div class="mb-2">氏名：${student.name} (${student.no})</div>

                <table class="table table-bordered table-striped">
                    <thead class="table-secondary">
                        <tr>
                            <th>科目名</th>
                            <th>科目コード</th>
                            <th class="text-center">回数</th>
                            <th class="text-center">点数</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="t" items="${tests}">
                            <tr>
                                <td>${t.subjectName}</td>
                                <td>${t.subjectCd}</td>
                                <td class="text-center">${t.num}</td>
                                <td class="text-center">
                                    <c:choose>
                                        <c:when test="${t.point == -1}">-</c:when>
                                        <c:otherwise>${t.point}</c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <!-- 科目別結果 -->
            <c:if test="${not empty tests and empty student}">
                <div class="mb-2">科目：${subject.name}</div>

                <table class="table table-bordered table-striped">
                    <thead class="table-secondary">
                        <tr>
                            <th>入学年度</th>
                            <th>クラス</th>
                            <th>学生番号</th>
                            <th>氏名</th>
                            <th class="text-center">1回</th>
                            <th class="text-center">2回</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="t" items="${tests}">
                            <tr>
                                <td>${t.student.entYear}</td>
                                <td>${t.student.classNum}</td>
                                <td>${t.student.no}</td>
                                <td>${t.student.name}</td>
                                <td class="text-center">
                                    <c:choose>
                                        <c:when test="${t.point == -1}">-</c:when>
                                        <c:otherwise>${t.point}</c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-center">
                                    <c:choose>
                                        <c:when test="${t.point2 == -1}">-</c:when>
                                        <c:otherwise>${t.point2}</c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </section>
    </c:param>
</c:import>