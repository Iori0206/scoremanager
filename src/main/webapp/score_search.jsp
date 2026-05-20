<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">成績参照</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">

            <h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">
                <c:if test="${empty pageTitle}">成績参照</c:if>
                <c:if test="${not empty pageTitle}">${pageTitle}</c:if>
            </h2>

            <div class="border rounded bg-white p-4">

                <!-- 科目検索フォーム -->
                <form action="ScoreSearch.action" method="get" class="mb-0">
                    <div class="row align-items-end mb-3">
                        <div class="col-2">
                            <label class="form-label mb-0">科目情報</label>
                        </div>

                        <div class="col-2">
                            <label class="form-label">入学年度</label>
                            <select name="ent_year" class="form-select">
                                <option value="">---------</option>
                                <c:forEach var="y" items="${years}">
                                    <option value="${y}" <c:if test="${ent_year == y}">selected</c:if>>
                                        ${y}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-2">
                            <label class="form-label">クラス</label>
                            <select name="class_num" class="form-select">
                                <option value="">---------</option>
                                <c:forEach var="c" items="${classes}">
                                    <option value="${c}" <c:if test="${class_num == c}">selected</c:if>>
                                        ${c}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-3">
                            <label class="form-label">科目</label>
                            <select name="subject_cd" class="form-select">
                                <option value="">---------</option>
                                <c:forEach var="s" items="${subjects}">
                                    <option value="${s.cd}" <c:if test="${subject_cd == s.cd}">selected</c:if>>
                                        ${s.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-2">
                            <button type="submit" class="btn btn-secondary">検索</button>
                        </div>
                    </div>

                    <c:if test="${not empty conditionError}">
                        <div class="mb-2" style="color:#f0ad4e; font-size:14px;">
                            ${conditionError}
                        </div>
                    </c:if>
                </form>

                <hr class="my-4">

                <!-- 学生検索フォーム -->
                <form action="ScoreSearch.action" method="get" class="mb-0">
                    <div class="row align-items-end mb-3">
                        <div class="col-2">
                            <label class="form-label mb-0">学生情報</label>
                        </div>

                        <div class="col-4">
                            <label class="form-label">学生番号</label>
                            <input type="text"
                                   name="student_no"
                                   class="form-control"
                                   value="${student_no}"
                                   placeholder="学生番号を入力してください"
                                   required>
                        </div>

                        <div class="col-2">
                            <button type="submit" class="btn btn-secondary">検索</button>
                        </div>
                    </div>
                </form>
            </div>

            <!-- 学生検索時のみ氏名表示 -->
            <c:if test="${not empty student}">
                <div class="mt-3 mb-1" style="font-size:14px;">
                    氏名：
                    <c:if test="${not empty student.name}">${student.name}</c:if>
                    <c:if test="${empty student.name}"> </c:if>
                    (${student.no})
                </div>
            </c:if>

            <!-- 枠の下のエラー -->
            <c:if test="${not empty searchError}">
                <div class="mb-2" style="font-size:14px; color:#222;">
                    ${searchError}
                </div>
            </c:if>

            <!-- 初期表示だけ案内文 -->
            <c:if test="${empty tests and empty conditionError and empty searchError and empty student}">
                <div class="mt-3" style="color:#4fc3f7; font-size:14px;">
                    科目情報を選択または学生情報を入力して検索ボタンをクリックしてください
                </div>
            </c:if>

            <!-- 学生検索結果 -->
            <c:if test="${not empty student and not empty tests}">
                <div class="mt-2">
                    <table class="table table-bordered">
                        <thead class="table-light">
                            <tr>
                                <th>科目名</th>
                                <th>科目コード</th>
                                <th>回数</th>
                                <th>点数</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="t" items="${tests}">
                                <tr>
                                    <td>${t.subjectName}</td>
                                    <td>${t.subjectCd}</td>
                                    <td>${t.num}</td>
                                    <td>
                                        <c:if test="${t.point == -1}">-</c:if>
                                        <c:if test="${t.point != -1}">${t.point}</c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <!-- 科目検索結果 -->
            <c:if test="${empty student and not empty tests}">
                <div class="mt-3 mb-2" style="font-size:14px;">
                    科目：${subject.name}
                </div>

                <div class="mt-2">
                    <table class="table table-bordered">
                        <thead class="table-light">
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
                                        <c:if test="${t.point == -1}">-</c:if>
                                        <c:if test="${t.point != -1}">${t.point}</c:if>
                                    </td>
                                    <td class="text-center">
                                        <c:if test="${t.point2 == -1}">-</c:if>
                                        <c:if test="${t.point2 != -1}">${t.point2}</c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

        </section>
    </c:param>
</c:import>