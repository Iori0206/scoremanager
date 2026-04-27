<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>成績参照</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">

    <h2 class="mb-4">成績参照（科目・クラス毎）</h2>

    <!-- エラー表示 -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <form action="ScoreList.action" method="post" class="card p-4 shadow-sm">

        <!-- 年度 -->
        <div class="mb-3">
            <label class="form-label">年度</label>
            <select name="ent_year" class="form-select">
                <option value="">選択してください</option>
                <c:forEach var="y" items="${years}">
                    <option value="${y}">${y}</option>
                </c:forEach>
            </select>
        </div>

        <!-- クラス -->
        <div class="mb-3">
            <label class="form-label">クラス</label>
            <select name="class_num" class="form-select">
                <option value="">選択してください</option>
                <c:forEach var="c" items="${classes}">
                    <option value="${c}">${c}</option>
                </c:forEach>
            </select>
        </div>

        <!-- 科目 -->
        <div class="mb-3">
            <label class="form-label">科目</label>
            <select name="subject_cd" class="form-select">
                <option value="">選択してください</option>
                <c:forEach var="s" items="${subjects}">
                    <option value="${s.subjectCd}">${s.name}</option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="btn btn-primary w-100">検索</button>
    </form>

    <div class="mt-3">
        <a href="Menu.action" class="btn btn-secondary">メニューへ戻る</a>
    </div>

</div>

</body>
</html>