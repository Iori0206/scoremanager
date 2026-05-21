<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- base.jsp のレイアウトを読み込む --%>
<c:import url="/common/base.jsp">
    <c:param name="title">エラー</c:param>
    <c:param name="content">
        
        <section class="mt-5 ms-5">
            <div class="text-dark">
                <p style="font-size: 1.2rem;">エラーが発生しました</p>
            </div>
        </section>
    </c:param>
</c:import>
