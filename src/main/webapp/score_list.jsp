<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>科目別成績一覧</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">

    <h2 class="mb-4">科目別成績一覧</h2>

    <div class="card p-3 mb-4 shadow-sm">
        <p>年度：${ent_year}</p>
        <p>クラス：${class_num}</p>
        <p>科目：${subject_cd}</p>
    </div>

    <table class="table table-bordered table-striped shadow-sm bg-white">
        <thead class="table-dark">
            <tr>
                <th>学生番号</th>
                <th>氏名</th>
                <th>点数</th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="s" items="${list}">
                <tr>
                    <td>${s.studentNo}</td>
                    <td>${s.studentName}</td>
                    <td>
                        <c:choose>
                            <c:when test="${s.point == 0}">
                                -
                            </c:when>
                            <c:otherwise>
                                ${s.point}
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="mt-3">
        <a href="ScoreSearch.action" class="btn btn-secondary">検索画面へ戻る</a>
        <a href="Menu.action" class="btn btn-secondary">メニューへ戻る</a>
    </div>

</div>

</body>
</html>