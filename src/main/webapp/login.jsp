<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%-- 共通の base.jsp をそのままインポート --%>
<c:import url="/common/base.jsp">
    <c:param name="title">
        ログイン - 得点管理システム
    </c:param>

    <c:param name="content">
        <div class="login-box">
            <div class="card-top-header">ログイン</div>

            <div class="card-main-body">
                
                <c:if test="${not empty error}">
                    <ul class="error-list">
                        <li><c:out value="${error}" /></li>
                    </ul>
                </c:if>

                <form action="Login.action" method="post">

                    <div class="custom-input-group">
                        <label for="school_cd">ＩＤ</label>
                        <input type="text" 
                               id="school_cd"
                               name="school_cd" 
                               class="form-control" 
                               maxlength="10" 
                               required 
                               placeholder="半角でご入力ください">
                    </div>

                    <div class="custom-input-group">
                        <label for="pw">パスワード</label>
                        <input type="password" 
                               id="pw" 
                               name="password" 
                               class="form-control" 
                               maxlength="30"
                               required
                               placeholder="30文字以内の半角英数字でご入力ください">
                    </div>

                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" id="showPw"
                               onclick="document.getElementById('pw').type = this.checked ? 'text' : 'password'">
                        <label class="form-check-label" for="showPw">パスワードを表示</label>
                    </div>

                    <button class="btn btn-primary btn-custom-login">ログイン</button>

                </form>
            </div>
        </div>
    </c:param>
</c:import>