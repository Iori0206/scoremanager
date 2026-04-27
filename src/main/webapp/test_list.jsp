<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績参照</c:param>
    <c:param name="content">
        <section class="p-4">
            <h2 class="h4 border-bottom pb-3 mb-4">成績参照</h2>

            <%-- 科目別検索 --%>
            <form action="TestListSubjectExecute.action" method="get" class="row g-3 align-items-end mb-5 bg-light p-3 rounded">
                <div class="col-md-3">
                    <label class="form-label">入学年度</label>
                    <select name="f1" class="form-select" required>
                        <option value="">選択してください</option>
                        <c:forEach var="year" items="${ent_years}">
                            <option value="${year}">${year}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label">クラス</label>
                    <select name="f2" class="form-select" required>
                        <c:forEach var="c_num" items="${class_nums}">
                            <option value="${c_num}">${c_num}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4">
                    <label class="form-label">科目</label>
                    <select name="f3" class="form-select" required>
                        <c:forEach var="subject" items="${subjects}">
                            <option value="${subject.cd}">${subject.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-secondary w-100">検索</button>
                </div>
            </form>

            <%-- 学生別検索 --%>
            <form action="TestListStudentExecute.action" method="get" class="row g-3 align-items-end bg-light p-3 rounded">
                <div class="col-md-10">
                    <label class="form-label">学生番号</label>
                    <input type="text" name="f4" class="form-control" placeholder="学生番号を入力してください" required>
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-secondary w-100">検索</button>
                </div>
            </form>
        </section>
    </c:param>
</c:import>