<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">学生情報変更</c:param>

    <c:param name="content">
        <section class="me-4">
            <%-- 画面タイトル --%>
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                学生情報変更
            </h2>

            <%-- 完了メッセージ --%>
            <div class="alert alert-success mt-4">
                変更が完了しました
            </div>

            <div class="mt-4">
                <%-- 戻るリンク（変更画面へ戻る） --%>
                <a href="StudentUpdate.action?no=${param.no}" class="me-3">戻る</a>
                
                <%-- 学生一覧リンク --%>
                <a href="StudentList.action">学生一覧</a>
            </div>
        </section>
    </c:param>
</c:import>