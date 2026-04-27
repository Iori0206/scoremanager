<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績登録</c:param>
    <c:param name="content">
        <section class="p-4">
            <h2 class="h4 border-bottom pb-3 mb-4">成績登録</h2>

            <%-- 検索条件指定フォーム --%>
            <form action="TestRegist.action" method="get" class="row g-3 mb-5 align-items-end bg-light p-3 rounded">
                <div class="col-md-3">
                    <label class="form-label small">入学年度</label>
                    <select name="f1" class="form-select" required>
                        <c:forEach var="year" items="${ent_years}">
                            <option value="${year}" ${param.f1 == year ? 'selected' : ''}>${year}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-2">
                    <label class="form-label small">クラス</label>
                    <select name="f2" class="form-select" required>
                        <c:forEach var="c_num" items="${class_nums}">
                            <option value="${c_num}" ${param.f2 == c_num ? 'selected' : ''}>${c_num}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label small">科目</label>
                    <select name="f3" class="form-select" required>
                        <c:forEach var="subject" items="${subjects}">
                            <option value="${subject.cd}" ${param.f3 == subject.cd ? 'selected' : ''}>${subject.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-2">
                    <label class="form-label small">回数</label>
                    <select name="f4" class="form-select">
                        <option value="1" ${param.f4 == '1' ? 'selected' : ''}>1</option>
                        <option value="2" ${param.f4 == '2' ? 'selected' : ''}>2</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary w-100">検索</button>
                </div>
            </form>

            <%-- 成績入力テーブル：検索結果がある場合のみ表示 --%>
            <c:choose>
                <c:when test="${not empty tests}">
                    <form action="TestRegistExecute.action" method="post">
                        <%-- 検索時の情報を隠しパラメータで送信 --%>
                        <input type="hidden" name="subject_cd" value="${param.f3}">
                        <input type="hidden" name="num" value="${param.f4}">

                        <table class="table table-hover align-middle">
                            <thead class="table-secondary">
                                <tr>
                                    <th>入学年度</th>
                                    <th>クラス</th>
                                    <th>学生番号</th>
                                    <th>氏名</th>
                                    <th style="width: 200px;">点数</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="test" items="${tests}">
                                    <tr>
                                        <td>${test.entYear}</td>
                                        <td>${test.classNum}</td>
                                        <td>${test.student.no}</td>
                                        <td>${test.student.name}</td>
                                        <td>
                                            <%-- 配列形式で一括送信 --%>
                                            <input type="hidden" name="student_no_set[]" value="${test.student.no}">
                                            <div class="input-group">
                                                <input type="number" name="point_set[]" 
                                                       value="${test.point != -1 ? test.point : ''}" 
                                                       class="form-control" min="0" max="100">
                                                <span class="input-group-text">点</span>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <div class="text-end mt-4">
                            <button type="submit" class="btn btn-success px-5 shadow-sm">登録して保存</button>
                        </div>
                    </form>
                </c:when>
                <c:when test="${not empty param.f1}">
                    <p class="alert alert-warning">該当する学生が見つかりませんでした。</p>
                </c:when>
            </c:choose>
        </section>
    </c:param>
</c:import>