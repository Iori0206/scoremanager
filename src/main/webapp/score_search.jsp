<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成績参照</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
<style>
  body { min-height: 100vh; display: flex; flex-direction: column; }
  .layout { flex: 1; display: flex; }
  .sidebar { width: 200px; background: #f8f9fa; border-right: 1px solid #ddd; padding: 1rem; }
  .content-area { flex: 1; padding: 2rem; }
  .filter-card { background-color: #f8f9fa; border-radius: 8px; padding: 20px; margin-bottom: 20px; }
  footer { text-align: center; padding: 0.5rem; background: #f8f9fa; border-top: 1px solid #ddd; }
  .table-container { background: white; border-radius: 8px; padding: 15px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); }
</style>
</head>
<body>

<header class="d-flex justify-content-between align-items-center p-3 border-bottom">
  <h2 class="m-0">得点管理システム</h2>
  <div>
    <c:if test="${not empty sessionScope.user}">
      ${sessionScope.user.name} 様
    </c:if>
    <a href="Logout.action" class="ms-2">ログアウト</a>
  </div>
</header>

<div class="layout">
  <nav class="sidebar">
    <ul class="nav flex-column">
      <li class="nav-item"><a class="nav-link" href="Menu.action">メニュー</a></li>
      <li class="nav-item"><a class="nav-link" href="StudentList.action">学生管理</a></li>
      <h6 class="mt-3 ps-3">成績管理</h6>
      <li class="nav-item"><a class="nav-link" href="TestRegist.action">成績登録</a></li>
      <li class="nav-item"><a class="nav-link" href="TestList.action">成績参照</a></li>
      <li class="nav-item"><a class="nav-link" href="SubjectList.action">科目管理</a></li>
      <li class="nav-item"><a class="nav-link" href="ClassList.action">クラス管理</a></li>
    </ul>
  </nav>

  <main class="content-area">
    <div class="container-fluid">
      <h2 class="mb-4 border-bottom pb-2">成績参照</h2>

      <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
      </c:if>

      <div class="filter-card shadow-sm">
        <h6 class="mb-3 text-muted">科目情報から検索</h6>
        <form action="TestList.action" method="get">
          <div class="row align-items-end">
            <div class="col-md-3">
              <label class="form-label">入学年度</label>
              <select name="ent_year" class="form-select">
                <option value="">選択してください</option>
                <c:forEach var="y" items="${years}">
                  <option value="${y}" ${y == ent_year ? 'selected' : ''}>${y}</option>
                </c:forEach>
              </select>
            </div>
            <div class="col-md-3">
              <label class="form-label">クラス</label>
              <select name="class_num" class="form-select">
                <option value="">選択してください</option>
                <c:forEach var="c" items="${classes}">
                  <option value="${c}" ${c == class_num ? 'selected' : ''}>${c}</option>
                </c:forEach>
              </select>
            </div>
            <div class="col-md-4">
              <label class="form-label">科目</label>
              <select name="subject_cd" class="form-select">
                <option value="">選択してください</option>
                <c:forEach var="s" items="${subjects}">
                  <option value="${s.cd}" ${s.cd == subject_cd ? 'selected' : ''}>${s.name}</option>
                </c:forEach>
              </select>
            </div>
            <div class="col-md-2">
              <button type="submit" class="btn btn-secondary w-100">検索</button>
            </div>
          </div>
        </form>
      </div>

      <div class="filter-card shadow-sm">
        <h6 class="mb-3 text-muted">学生情報から検索</h6>
        <form action="TestList.action" method="get">
          <div class="row align-items-end">
            <div class="col-md-10">
              <label class="form-label">学生番号</label>
              <select name="student_no" class="form-select">
                <option value="">学生番号を選択してください</option>
                <c:forEach var="stu" items="${students}">
                  <option value="${stu.no}" ${stu.no == student_no ? 'selected' : ''}>
                    ${stu.no}：${stu.name}
                  </option>
                </c:forEach>
              </select>
            </div>
            <div class="col-md-2">
              <button type="submit" class="btn btn-secondary w-100">検索</button>
            </div>
          </div>
        </form>
      </div>

      <c:if test="${not empty tests}">
        <div class="table-container mt-4">
          <c:choose>
            <c:when test="${not empty student}">
              <div class="mb-2 fw-bold">氏名：${student.name} (${student.no})</div>

              <table class="table table-bordered table-hover">
                <thead class="table-light">
                  <tr>
                    <th>科目名</th>
                    <th>科目コード</th>
                    <th class="text-center">回数</th>
                    <th class="text-center">点数</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="t" items="${tests}">
                    <tr>
                      <td>${t.subjectName}</td>
                      <td>${t.subjectCd}</td>
                      <td class="text-center">${t.num}</td>
                      <td class="text-center fw-bold">
                        <c:choose>
                          <c:when test="${t.point == -1}">-</c:when>
                          <c:otherwise>${t.point}</c:otherwise>
                        </c:choose>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </c:when>

            <c:otherwise>
              <div class="mb-2">科目：${subject.name}</div>

              <table class="table table-bordered table-hover">
                <thead class="table-light">
                  <tr>
                    <th>入学年度</th>
                    <th>クラス</th>
                    <th>学生番号</th>
                    <th>氏名</th>
                    <th class="text-center">1回</th>
                    <th class="text-center">2回</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="t" items="${tests}">
                    <tr>
                      <td class="text-center">${t.student.entYear}</td>
                      <td class="text-center">${t.student.classNum}</td>
                      <td>${t.student.no}</td>
                      <td>${t.student.name}</td>
                      <td class="text-center">
                        <c:choose>
                          <c:when test="${t.point == -1}">-</c:when>
                          <c:otherwise>${t.point}</c:otherwise>
                        </c:choose>
                      </td>
                      <td class="text-center">
                        <c:choose>
                          <c:when test="${t.point2 == -1}">-</c:when>
                          <c:otherwise>${t.point2}</c:otherwise>
                        </c:choose>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </c:otherwise>
          </c:choose>
        </div>
      </c:if>
    </div>
  </main>
</div>

<footer>© 2026 熊本校 学生管理システム</footer>
</body>
</html>