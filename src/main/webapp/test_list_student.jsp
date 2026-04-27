<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績参照（学生別）</c:param>
    <c:param name="content">
        <section class="p-4">
            <h2 class="h4 mb-4">成績参照（学生別）</h2>
            <div class="mb-3">
                <p>氏名：<strong>${student.name} (${student.no})</strong></p>
            </div>

            <table class="table table-bordered">
                <thead class="table-light text-center">
                    <tr>
                        <th>科目名</th>
                        <th>科目コード</th>
                        <th>回数</th>
                        <th>点数</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="test" items="${tests}">
                        <tr>
                            <td>${test.subject.name}</td>
                            <td class="text-center">${test.subject.cd}</td>
                            <td class="text-center">${test.no}回</td>
                            <td class="text-end">${test.point} 点</td>
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