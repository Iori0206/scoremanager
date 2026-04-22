<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">学生新規登録</c:param>

    <c:param name="content">
        <h3 class="mb-4">学生新規登録</h3>

        <form action="StudentInsert.action" method="post">

            <div class="mb-3">
                <label class="form-label">学生番号</label>
                <input type="text" name="no" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">氏名</label>
                <input type="text" name="name" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">入学年度</label>
                <input type="number" name="ent_year" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">クラス</label>
                <input type="text" name="class_num" class="form-control" required>
            </div>

            <div class="form-check mb-3">
                <input type="checkbox" name="is_attend" value="1" class="form-check-input">
                <label class="form-check-label">在学中</label>
            </div>

            <button class="btn btn-primary">登録</button>
            <a href="StudentList.action" class="btn btn-secondary">戻る</a>

        </form>
    </c:param>
</c:import>