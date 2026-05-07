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
  .content-area {
    flex: 1;
    padding: 2rem;
  }
  footer {
    text-align: center;
    padding: 0.5rem;
    background: #f8f9fa;
    border-top: 1px solid #ddd;
  }
</style>
</head>

<body>

<header class="d-flex justify-content-between align-items-center p-3 border-bottom">
  <h2 class="m-0">得点管理システム</h2>

  <div>
    <c:if test="${not empty sessionScope.user}">
      ${sessionScope.user.name} 様　
    </c:if>
    <a href="Logout.action">ログアウト</a>
  </div>
</header>

<div class="layout">

  <nav class="sidebar">
    
    <ul class="nav flex-column">
      <li class="nav-item"><a class="nav-link" href="menu.jsp">メニュー</a>
      <li class="nav-item"><a class="nav-link" href="StudentList.action">学生管理</a></li>
      <h6> 成績管理 </h6>
      <li class="nav-item"><a class="nav-link" href="TestRegist.action">成績登録</a></li>
      <li class="nav-item"><a class="nav-link" href="TestList.action">成績参照</a></li>
      <li class="nav-item"><a class="nav-link" href="SubjectList.action">科目管理</a></li>
      <li class="nav-item"><a class="nav-link" href="ClassList.action">クラス管理</a></li>
    </ul>
  </nav>

  <main class="content-area">
    ${param.content}
  </main>

</div>

<footer>
  © 2023 TIC
  大原学園
</footer>

</body>
</html>