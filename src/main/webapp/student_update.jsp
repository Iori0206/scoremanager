<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">学生情報変更</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-4 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                学生情報変更
            </h2>

            <form action="StudentUpdateExecute.action" method="post" class="w-75">
                <%-- 学生番号：表示用と送信用(hidden) --%>
                <div class="mb-4">
                    <label class="form-label text-muted small">学生番号</label>
                    <div class="form-control bg-light text-muted border-0 shadow-sm" style="min-height: 38px;">
                        ${student.no}
                    </div>
                    <input type="hidden" name="no" value="${student.no}">
                </div>

                <%-- 氏名 --%>
                <div class="mb-4">
                    <label class="form-label text-muted small">氏名</label>
                    <input type="text" name="name" class="form-control shadow-sm" 
                           value="${student.name}" placeholder="氏名を入力してください" required>
                </div>

                <%-- 入学年度 --%>
                <div class="mb-4">
                    <label class="form-label text-muted small">入学年度</label>
                    <select name="ent_year" class="form-select shadow-sm">
                        <c:forEach var="y" items="${ent_year_list}">
                            <option value="${y}" ${y == student.entYear ? 'selected' : ''}>${y}</option>
                        </c:forEach>
                    </select>
                </div>

                <%-- クラス --%>
                <div class="mb-4">
                    <label class="form-label text-muted small">クラス</label>
                    <select name="class_num" class="form-select shadow-sm">
                        <c:forEach var="c" items="${class_num_list}">
                            <option value="${c}" ${c == student.classNum ? 'selected' : ''}>${c}</option>
                        </c:forEach>
                    </select>
                </div>

                <%-- 在学中フラグ --%>
                <div class="mb-4 form-check">
                    <%-- ★ 修正箇所： isAttend ではなく attend を指定 --%>
                    <input type="checkbox" name="is_attend" value="1" class="form-check-input" id="isAttendCheck" 
                           ${student.attend ? 'checked' : ''}>
                    <label class="form-check-label" for="isAttendCheck">在学中</label>
                </div>

                <%-- 変更ボタン --%>
                <div class="mt-4">
                    <button type="submit" class="btn btn-secondary px-4 py-2">変更</button>
                </div>

                <div class="mt-3">
                    <a href="StudentList.action" class="text-decoration-none small">戻る</a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>