<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績参照（科目別）</c:param>

    <c:param name="content">
        <section class="p-4">

            <h2 class="h4 mb-4">成績参照（科目別）</h2>

            <c:if test="${not empty tests}">
                <div class="mb-3">
                    <span class="badge bg-info text-dark">
                        科目：${tests[0].subject.name}
                    </span>
                </div>
            </c:if>

            <c:if test="${empty tests}">
                <div class="alert alert-warning">
                    成績データがありません
                </div>
            </c:if>

            <c:if test="${not empty tests}">
                <table class="table table-striped table-hover">
                    <thead class="table-light">
                        <tr>
                            <th>入学年度</th>
                            <th>クラス</th>
                            <th>学生番号</th>
                            <th>氏名</th>
                            <th>回数</th>
                            <th>点数</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="test" items="${tests}">
                            <tr>
                                <td>${test.student.entYear}</td>
                                <td>${test.student.classNum}</td>
                                <td>${test.student.no}</td>
                                <td>${test.student.name}</td>

                                <!-- 回数（ここが本体） -->
                                <td class="text-center">
                                    <c:choose>
                                        <c:when test="${test.num > 0}">
                                            ${test.num}回
                                        </c:when>
                                        <c:otherwise>
                                            -
                                        </c:otherwise>
                                    </c:choose>
                                </td>

                                <!-- 点数 -->
                                <td class="text-end">
                                    <c:choose>
                                        <c:when test="${test.point != -1}">
                                            ${test.point}点
                                        </c:when>
                                        <c:otherwise>
                                            -
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <div class="mt-4">
                <a href="TestList.action" class="btn btn-outline-secondary">
                    戻る
                </a>
            </div>

        </section>
    </c:param>
</c:import>