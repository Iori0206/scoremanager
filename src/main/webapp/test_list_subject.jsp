<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績参照（科目別）</c:param>
    <c:param name="content">
        <section class="p-4">
            <h2 class="h4 mb-4">成績参照（科目別）</h2>
            <div class="mb-3">
                <span class="badge bg-info text-dark">科目：${tests[0].subject.name}</span>
            </div>

            <table class="table table-striped table-hover">
                <thead class="table-light">
                    <tr>
                        <th>入学年度</th>
                        <th>クラス</th>
                        <th>学生番号</th>
                        <th>氏名</th>
                        <th>1回目</th>
                        <th>2回目</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="test" items="${tests}">
                        <tr>
                            <td>${test.entYear}</td>
                            <td>${test.classNum}</td>
                            <td>${test.student.no}</td>
                            <td>${test.student.name}</td>
                            <td>${test.point != -1 ? test.point : '-'} 点</td>
                            <td><%-- 2回目用のデータがあれば表示 --%></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="mt-4">
                <a href="TestList.action" class="btn btn-outline-secondary">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>