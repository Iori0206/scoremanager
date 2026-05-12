<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">

    <c:param name="title">
        成績参照（学生別）
    </c:param>

    <c:param name="content">

        <section class="p-4">

            <h2 class="h4 mb-4">
                成績参照（学生別）
            </h2>

            <c:choose>

                <!-- 学生情報あり -->
                <c:when test="${not empty student}">

                    <div class="mb-3">

                        <p>
                            氏名：
                            <strong>
                                ${student.name}
                                (${student.no})
                            </strong>
                        </p>

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

                            <c:choose>

                                <!-- 成績あり -->
                                <c:when test="${not empty tests}">

                                    <c:forEach
                                        var="test"
                                        items="${tests}"
                                    >

                                        <tr>

                                            <!-- 科目名 -->
                                            <td>
                                                ${test.subject.name}
                                            </td>

                                            <!-- 科目コード -->
                                            <td class="text-center">
                                                ${test.subject.cd}
                                            </td>

                                            <!-- 回数 -->
                                            <td class="text-center">
                                                ${test.num}回
                                            </td>

                                            <!-- 点数 -->
                                            <td class="text-end">
                                                ${test.point} 点
                                            </td>

                                        </tr>

                                    </c:forEach>

                                </c:when>

                                <!-- 成績なし -->
                                <c:otherwise>

                                    <tr>

                                        <td
                                            colspan="4"
                                            class="text-center"
                                        >

                                            成績情報が存在しません

                                        </td>

                                    </tr>

                                </c:otherwise>

                            </c:choose>

                        </tbody>

                    </table>

                </c:when>

                <!-- 学生なし -->
                <c:otherwise>

                    <div class="alert alert-danger">

                        指定された学生が
                        見つかりません。

                    </div>

                </c:otherwise>

            </c:choose>

            <div class="mt-4">

                <a
                    href="ScoreList.action"
                    class="btn btn-outline-secondary"
                >

                    戻る

                </a>

            </div>

        </section>

    </c:param>

</c:import>