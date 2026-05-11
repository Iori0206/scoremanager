<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目管理</c:param>

    <c:param name="content">
        <style>
            .main-content {
                flex: 1;
                padding: 28px 44px 0 44px;
                box-sizing: border-box;
            }

            .title-box {
                background: #eef5ff;
                height: 78px;
                box-sizing: border-box;
                padding: 18px 26px;
                font-size: 26px;
                font-weight: bold;
                margin-bottom: 22px;
            }

            .regist-link {
                text-align: right;
                margin-bottom: 14px;
                font-size: 15px;
            }

            .regist-link a {
                color: #2f6df6;
                text-decoration: underline;
            }

            .table-box {
                background: #fff;
                border: 1px solid #dcdcdc;
            }

            .subject-table {
                width: 100%;
                border-collapse: collapse;
                table-layout: fixed;
            }

            .subject-table th,
            .subject-table td {
                border-bottom: 1px solid #dcdcdc;
                padding: 18px 24px;
                font-size: 15px;
                text-align: left;
            }

            .subject-table th {
                font-weight: bold;
                background: #fff;
            }

            .subject-table tr:last-child td {
                border-bottom: none;
            }

            .code-col {
                width: 18%;
            }

            .name-col {
                width: 46%;
            }

            .action-col {
                width: 10%;
                text-align: center !important;
            }

            .subject-table a {
                color: #2f6df6;
                text-decoration: underline;
            }

            .empty-msg {
                text-align: center;
                padding: 90px 0;
                font-size: 15px;
                color: #555;
            }

            .footer-copyright {
                text-align: center;
                margin-top: 18px;
                color: #777;
                font-size: 13px;
                line-height: 1.7;
                padding: 12px 0 14px 0;
                background: #efefef;
                border-top: 1px solid #dcdcdc;
            }
        </style>

        <div class="main-content">
            <div class="title-box">科目管理</div>

            <div class="regist-link">
                <a href="SubjectCreate.action">新規登録</a>
            </div>

            <div class="table-box">
                <table class="subject-table">
                    <thead>
                        <tr>
                            <th class="code-col">科目コード</th>
                            <th class="name-col">科目名</th>
                            <th class="action-col"></th>
                            <th class="action-col"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty list}">
                                <c:forEach var="s" items="${list}">
                                    <tr>
                                        <td>${s.cd}</td>
                                        <td>${s.name}</td>
                                        <td class="action-col">
                                            <a href="SubjectUpdate.action?cd=${s.cd}">変更</a>
                                        </td>
                                        <td class="action-col">
                                            <a href="SubjectDelete.action?cd=${s.cd}">削除</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="4" class="empty-msg">科目情報が存在しませんでした</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </c:param>
</c:import>