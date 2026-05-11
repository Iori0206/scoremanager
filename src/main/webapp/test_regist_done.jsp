<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">登録完了</c:param>
    <c:param name="content">
        <section class="p-5 text-center">
            <div class="mb-4">
                <i class="bi bi-check-circle text-success" style="font-size: 4rem;"></i>
                <h2 class="mt-3">成績の登録が完了しました</h2>
            </div>
            <div class="mt-5">
                <a href="TestRegist.action" class="btn btn-primary me-2">戻る</a>
                <a href="TestList.action" class="btn btn-outline-secondary">成績参照</a>
            </div>
        </section>
    </c:param>
</c:import>