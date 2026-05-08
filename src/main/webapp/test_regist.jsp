<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<c:import url="/common/base.jsp">
    <c:param name="title">成績管理</c:param>

    <c:param name="content">
        <h3 class="mb-4">成績管理</h3>

        <form action="TestRegist.action" method="post" class="card shadow-sm mb-4">
            <div class="card-body bg-light">
                <div class="row g-3 align-items-end">
                    <%-- 入学年度：DBから取得したリストを回す --%>
                    <div class="col-md-3">
                        <label class="form-label small fw-bold">入学年度</label>
                        <select name="f1" class="form-select">
                            <option value="0" ${empty ent_year or ent_year == 0 ? "selected" : ""}>---</option>
                            <c:forEach var="y" items="${ent_year_list}">
                                <option value="${y}" ${y == ent_year ? "selected" : ""}>${y}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <%-- クラス：DBから取得したリストを回す --%>
                    <div class="col-md-2">
                        <label class="form-label small fw-bold">クラス</label>
                        <select name="f2" class="form-select">
                            <option value="---" ${class_num == "---" ? "selected" : ""}>---</option>
                            <c:forEach var="c" items="${class_num_list}">
                                <option value="${c}" ${c == class_num ? "selected" : ""}>${c}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <%-- 科目：DBから取得した科目リスト（subjects）を回す --%>
                    <div class="col-md-3">
                        <label class="form-label small fw-bold">科目</label>
                        <select name="f3" class="form-select">
                            <option value="---">---</option>
                            <c:forEach var="s" items="${subjects}">
                                <option value="${s.cd}" ${s.cd == subject_cd ? "selected" : ""}>${s.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-2">
                        <label class="form-label small fw-bold">回数</label>
                        <select name="f4" class="form-select">
                            <option value="0" ${empty num or num == 0 ? 'selected' : ''}>---</option>
                            <option value="1" ${num == 1 ? 'selected' : ''}>1</option>
                            <option value="2" ${num == 2 ? 'selected' : ''}>2</option>
                        </select>
                    </div>

                    <div class="col-md-2">
                        <button type="submit" class="btn btn-secondary w-100">検索</button>
                    </div>
                </div>
            </div>
        </form>

        <c:if test="${not empty tests}">
            <p class="mb-2">科目：${subject.name} （${num}回）</p>
            
            <form action="TestRegistExecute.action" method="post">
                <table class="table table-hover border-top">
                    <thead>
                        <tr>
                            <th>入学年度</th>
                            <th>クラス</th>
                            <th>学生番号</th>
                            <th>氏名</th>
                            <th style="width: 250px;">点数</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="t" items="${tests}">
                            <tr>
                                <td>${ent_year}</td> 
                                <td>${class_num}</td>
                                <td>${t.studentNo}</td>
                                <td>${t.studentName}</td>
                                <td>
                                    <input type="number" name="point_${t.studentNo}" 
                                           value="${t.point == -1 ? '' : t.point}" min="0" max="100" 
                                           class="form-control d-inline-block">
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <input type="hidden" name="f1" value="${ent_year}">
                <input type="hidden" name="f2" value="${class_num}">
                <input type="hidden" name="f3" value="${subject_cd}">
                <input type="hidden" name="f4" value="${num}">

                <div class="mt-4">
                    <button type="submit" class="btn btn-secondary">登録して終了</button>
                </div>
            </form>
        </c:if>
    </c:param>
</c:import>