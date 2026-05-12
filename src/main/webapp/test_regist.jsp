<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">成績登録</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績登録</h2>

            <form action="TestRegistExecute.action" method="post">
                <div class="mb-3">
                    入学年度：
                    <input type="text" name="ent_year">
                </div>

                <div class="mb-3">
                    クラス：
                    <select name="class_num" class="form-select">
                        <option value="">選択してください</option>
                        <c:forEach var="c" items="${class_num_list}">
                            <option value="${c}" ${param.class_num == c ? "selected" : ""}>${c}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    科目：
                    <select name="subject_cd" class="form-select">
                        <option value="">選択してください</option>
                        <c:forEach var="subject" items="${subject_list}">
                            <option value="${subject.cd}">${subject.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    学生番号：
                    <input type="text" name="student_no">
                </div>

                <div class="mb-3">
                    点数：
                    <input type="text" name="point">
                </div>

                <div class="mb-3">
                    <input type="submit" value="登録">
                </div>
            </form>

            <div class="mt-3">
                <a href="Menu.action">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>