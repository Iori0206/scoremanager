<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">学生情報変更</c:param>

    <c:param name="content">
        <section class="me-4">
            
            <div class="bg-secondary bg-opacity-10 py-3 px-4 mb-4">
                <h2 class="h3 mb-0 fw-bold">学生情報変更</h2>
            </div>

            <form action="StudentUpdateExecute.action" method="post" class="ms-4 w-75">
            
                <%-- 入学年度 --%>
                <div class="mb-4">
                     <label class="form-label">入学年度</label>
               <div class="col-md-9">
                <%-- 見た目用のテキスト入力（変更不可にするため readonly を追加しています） --%>
                <input type="text" class="form-control bg-light text-muted" 
                     value="${student.entYear}" readonly>
               </div>
                <input type="hidden" name="ent_year" value="${student.entYear}">
               </div>
                
                <%-- 学生番号 --%>
                <div class="mb-4">
                    <label class="form-label">学生番号</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control bg-light text-muted" 
                               value="${student.no}" readonly>
                    </div>
                    <input type="hidden" name="no" value="${student.no}">
                </div>

                <%-- 氏名 --%>
                <div class="mb-4">
                    <label class="form-label">氏名</label>
                    <div class="col-md-9">
                        <input type="text" name="name" class="form-control shadow-sm" 
                               value="${student.name}" placeholder="氏名を入力してください" 
                               maxlength="30" required>
                    </div>
                </div>


                <%-- クラス --%>
                <div class="mb-4">
                    <label class="form-label">クラス</label>
                    <div class="col-md-9">
                        <select name="class_num" class="form-select shadow-sm">
                            <c:forEach var="c" items="${class_num_list}">
                                <option value="${c}" ${c == student.classNum ? 'selected' : ''}>${c}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <%-- 在学中フラグ --%>
                <div class="mb-4 form-check ms-1">
                    <input type="checkbox" name="is_attend" value="1" class="form-check-input" id="isAttendCheck" 
                           ${student.attend ? 'checked' : ''}>
                    <label class="form-check-label" for="isAttendCheck">在学中</label>
                </div>

                <%-- 変更ボタン：classをbtn-primaryにし、styleで鮮やかな青(#0d6efd)を強制適用 --%>
                <div class="mt-5 pt-2">
                    <button type="submit" class="btn btn-primary text-white px-4 py-2" 
                            style="background-color: #0d6efd !important; border-color: #0d6efd !important;">変更</button>
                </div>

                <div class="mt-3">
                    <a href="StudentList.action" class="text-decoration-none">戻る</a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>