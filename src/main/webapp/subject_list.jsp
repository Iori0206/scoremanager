<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目管理</c:param>

    <c:param name="content">
        <style>
            .subject-page {
                width: 100%;
                max-width: 1100px;
            }

            .subject-title-box {
                background: #f1f1f1;
                padding: 12px 22px;
                font-size: 18px;
                font-weight: bold;
                margin-bottom: 16px;
            }

            .subject-link-row {
                text-align: right;
                margin-bottom: 10px;
                font-size: 13px;
            }

            .subject-link-row a {
                color: #2f6df6;
                text-decoration: underline;
            }

            .subject-table {
                width: 100%;
                border-collapse: collapse;
                table-layout: fixed;
                border: 1px solid #dcdcdc;
            }

            .subject-table th,
            .subject-table td {
                border-bottom: 1px solid #dcdcdc;
                padding: 16px 18px;
                font-size: 14px;
                text-align: left;
                vertical-align: middle;
            }

            .subject-table th {
                font-weight: bold;
                background: #ffffff;
            }

            .subject-table tr:last-child td {
                border-bottom: none;
            }

            .subject-code-col {
                width: 20%;
            }

            .subject-name-col {
                width: 50%;
            }

            .subject-action-col {
                width: 10%;
                text-align: center !important;
            }

            .subject-table a {
                color: #2f6df6;
                text-decoration: underline;
            }

            .subject-empty {
                text-align: center;
                color: #666;
                padding: 60px 0;
            }
        </style>

        <div class="subject-page">
            <div class="subject-title-box">科目管理</div>

            <div class="subject-link-row">
                <a href="SubjectCreate.action">新規登録</a>
            </div>

            <table class="subject-table">
                <thead>
                    <tr>
                        <th class="subject-code-col">科目コード</th>
                        <th class="subject-name-col">科目名</th>
                        <th class="subject-action-col"></th>
                        <th class="subject-action-col"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty list}">
                            <c:forEach var="s" items="${list}">
                                <tr>
                                    <td>${s.cd}</td>
                                    <td>${s.name}</td>
                                    <td class="subject-action-col">
                                        <a href="SubjectUpdate.action?cd=${s.cd}">変更</a>
                                    </td>
                                    <td class="subject-action-col">
                                        <a href="SubjectDelete.action?cd=${s.cd}">削除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="4" class="subject-empty">科目情報が存在しませんでした</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </c:param>
</c:import>