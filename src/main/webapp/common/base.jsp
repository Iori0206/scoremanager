<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${param.title}</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<style>
  body {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
  }
  .layout {
    flex: 1;
    display: flex;
  }
  .sidebar {
    width: 200px;
    background: #f8f9fa;
    border-right: 1px solid #ddd;
    padding: 1rem;
  }
  
  /* ─── 【重要】コンテンツエリア全体の余白と配置 ─── */
  .content-area {
    flex: 1;
    padding: 0px !important; /* 親要素の余白を完全ゼロにして最上部に密着させます */
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
  }

  /* ログイン画面（サイドバーがない時）だけ中央配置＆余白を戻す */
  .layout:not(:has(.sidebar)) .content-area {
    padding: 2rem !important;
    justify-content: center;
    align-items: center;
  }

  footer {
    text-align: center;
    padding: 0.5rem;
    background: #e0e0e0; /* 画像に合わせたグレー背景 */
    border-top: 1px solid #ddd;
  }

  /* ─── 以下、ログインカード用のスタイル ─── */
  .login-box {
    width: 460px;
    background: white;
    border: 1px solid #e0e0e0;
    border-radius: 4px;
    box-shadow: 0 2px 5px rgba(0,0,0,0.05);
    overflow: hidden;
    margin: 40px auto;
  }
  .card-top-header {
    background-color: #f5f5f5;
    text-align: center;
    padding: 12px;
    font-size: 18px;
    font-weight: bold;
    color: #444;
    border-bottom: 1px solid #e0e0e0;
  }
  .card-main-body {
    padding: 35px 40px;
  }
  .custom-input-group {
    background-color: #eaf2fc;
    border-radius: 6px;
    padding: 6px 14px;
    margin-bottom: 18px;
    text-align: left;
  }
  .custom-input-group label {
    display: block;
    font-size: 11px;
    color: #778899;
    margin-bottom: 1px;
    font-weight: bold;
  }
  .custom-input-group .form-control {
    background: transparent;
    border: none;
    padding: 2px 0;
    font-size: 14px;
    color: #333;
    box-shadow: none;
  }
  .custom-input-group .form-control:focus {
    background: transparent;
    border: none;
    box-shadow: none;
  }
  .form-check {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 4px;
    margin-bottom: 25px;
    font-size: 14px;
  }
  .btn-custom-login {
    background-color: #0066ff;
    border: none;
    border-radius: 6px;
    padding: 8px 0;
    font-size: 16px;
    font-weight: bold;
    width: 35% !important;
    margin: 0 auto;
    display: block;
    color: white;
  }
  .btn-custom-login:hover {
    background-color: #0052cc;
  }
  .error-list {
    color: #dc3545;
    font-size: 13px;
    margin-bottom: 20px;
    padding-left: 20px;
    text-align: left;
  }
</style>
</head>

<body>

<%-- 
  【修正ポイント①】
  最上部のヘッダーの「下線の境界線（border-bottom）」とお手本のグレーの帯（科目情報登録）が、
  隙間なくピッタリくっつくように Bootstrap の内側余白（p-3）を「p-2」に狭め、下側のマージンをゼロに固定しました。
--%>
<header class="d-flex justify-content-between align-items-center p-2 border-bottom m-0">
  <h2 class="m-0" style="font-weight: bold; color: #333; font-size: 24px; padding-left: 10px;">得点管理システム</h2>

  <div style="padding-right: 20px;">
    <c:if test="${not empty sessionScope.user}">
      ${sessionScope.user.name} 様　
      <a href="Logout.action">ログアウト</a>
    </c:if>
  </div>
</header>

<div class="layout">

  <c:if test="${not empty sessionScope.user}">
    <nav class="sidebar">
      <ul class="nav flex-column">
        <li class="nav-item"><a class="nav-link" href="menu.jsp">メニュー</a></li>
        <li class="nav-item"><a class="nav-link" href="StudentList.action">学生管理</a></li>
        <h6 class="ps-3 mt-2 text-muted">成績管理</h6>
        <li class="nav-item"><a class="nav-link" href="TestRegist.action">成績登録</a></li>
        <li class="nav-item"><a class="nav-link" href="TestList.action">成績参照</a></li>
        <li class="nav-item"><a class="nav-link" href="SubjectList.action">科目管理</a></li>
        <li class="nav-item"><a class="nav-link" href="ClassList.action">クラス管理</a></li>
      </ul>
    </nav>
  </c:if>

  <main class="content-area">
    ${param.content}
  </main>

</div>

<footer>
  © 2023 TIC<br>
  大原学園
</footer>

</body>
</html>