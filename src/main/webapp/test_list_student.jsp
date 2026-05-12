<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績参照（学生別）</c:param>
    <c:param name="content">
        <section class="p-4">
            <h2 class="h4 mb-4">成績参照（学生別）</h2>
            
            <c:choose>
                <%-- 学生情報が存在する場合 --%>
                <c:when test="${not empty student}">
                    <div class="mb-3">
                        <%-- Actionでセットした student.name と student.no を表示 --%>
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
                            <%-- 
                                TestListStudentDAO から返される List<TestListStudent> をループ。
                                Beanの設計に合わせてプロパティ名を修正 
                            --%>
                            <c:forEach var="test" items="${tests}">
                                <tr>
                                    <%-- TestListStudent Bean のフィールド名に合わせて修正 --%>
                                    <td>${test.subjectName}</td>
                                    <td class="text-center">${test.subjectCd}</td>
                                    <td class="text-center">${test.num}回</td>
                                    <td class="text-end">${test.point} 点</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    
                    <%-- 成績が1件もない場合 --%>
                    <c:if test="${empty tests}">
                        <div class="alert alert-warning">成績情報が存在しませんでした。</div>
                    </c:if>
                </c:when>
                
                <%-- 学生情報自体が送られてこなかった場合 --%>
                <c:otherwise>
                    <div class="alert alert-danger">
                        指定された学生が見つかりません。検索画面からやり直してください。
                    </div>
                </c:otherwise>
            </c:choose>

            <div class="mt-4">
                <%-- 検索画面（ScoreListAction）に戻る --%>
                <a href="ScoreList.action" class="btn btn-outline-secondary">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>