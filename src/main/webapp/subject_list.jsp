<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目管理</c:param>
    <c:param name="content">
        <style>
            /* メインエリアのレイアウト */
            .main-wrapper {
                display: flex;
                margin-top: 10px;
            }
            /* コンテンツエリア（サイドバーがbase.jspにある場合はここから開始） */
            .main-content {
                flex: 1;
                padding: 0 20px 20px 40px;
            }
            .title-box {
                background-color: #f8f9fa; /* 薄いグレーの背景 */
                padding: 15px;
                font-size: 20px;
                font-weight: bold;
                border-radius: 4px;
                margin-bottom: 20px;
            }
            .regist-link {
                text-align: right;
                margin-bottom: 10px;
            }
            .regist-link a {
                color: #4a76d1;
                text-decoration: none;
                font-size: 14px;
            }
            /* テーブルスタイル */
            .subject-table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 10px;
            }
            .subject-table th {
                border-bottom: 1px solid #ddd;
                padding: 12px;
                text-align: left;
                font-weight: normal;
                color: #333;
            }
            .subject-table td {
                padding: 40px 12px; /* 画像のような広い余白 */
                border-bottom: 1px solid #eee;
                font-size: 14px;
            }
            .empty-msg {
                text-align: center;
                padding: 100px 0;
                font-size: 16px;
                color: #333;
            }
            .footer-copyright {
                text-align: center;
                margin-top: 100px;
                color: #888;
                font-size: 12px;
                line-height: 1.5;
            }
        </style>

        <div class="main-content">
            <div class="title-box">科目管理</div>

            <div class="regist-link">
                <a href="SubjectCreate.action">新規登録</a>
            </div>

            <table class="subject-table">
                <thead>
                    <tr>
                        <th style="width: 30%;">科目コード</th>
                        <th style="width: 50%;">科目名</th>
                        <th style="width: 20%;"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty list}">
                            <c:forEach var="s" items="${list}">
                                <tr>
                                    <td>${s.cd}</td>
                                    <td>${s.name}</td>
                                    <td style="text-align: center;">
                                        <a href="SubjectUpdate.action?cd=${s.cd}" style="color: #4a76d1; margin-right: 15px;">変更</a>
                                        <a href="SubjectDelete.action?cd=${s.cd}" style="color: #4a76d1;">削除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="3" class="empty-msg">
                                    科目情報が存在しませんでした
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>

            <div class="footer-copyright">
                © 2023 TIC<br>
                大原学園
            </div>
        </div>
    </c:param>
</c:import>