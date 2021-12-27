<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="/res/css/board/detail.css?ver=3">
<div>
    <c:if test="${sessionScope.loginUser.iuser==requestScope.detail.writer}">
    <div>
        <a href="/board/del?iboard=${requestScope.detail.iboard}"><button>삭제</button></a>
        <a href="/board/regmod?iboard=${requestScope.detail.iboard}"><button>수정</button></a>
    </div>
    </c:if>
    <c:if test="${sessionScope.loginUser != null}">
        <div class="fav">
            <c:choose>
                <c:when test="${requestScope.isHeart == 1}">
                    <a href="/board/heart?proc=2&iboard=${requestScope.detail.iboard}"><i class="fas fa-heart"></i></a>
                </c:when>
                <c:otherwise>
                    <a href="/board/heart?proc=1&iboard=${requestScope.detail.iboard}"><i class="far fa-heart"></i></a>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>
    <c:set var="pImg" value="defaultProfile.jpg"/>
    <c:if test="${requestScope.detail.profileImg !=null}">
        <c:set var="pImg" value="profile/${requestScope.detail.writer}/${requestScope.detail.profileImg}"/>
    </c:if>
<div> 번호 ${requestScope.detail.iboard}</div>
<div><b> ${requestScope.detail.title}</b></div>
<div> 작성자 : <div class="circular--img circular--size20"><img src="/res/img/${pImg}"></div>${requestScope.detail.writerNm}</div>
<div> ${requestScope.detail.rdt}    조회수 : ${requestScope.detail.hit}</div>
<div> ${requestScope.detail.ctnt}</div>
<br></br>
    <c:if test="${sessionScope.loginUser!=null}">
    <div>
    <form action="/board/cmt/reg" method="post" class="cmtwrite">
        <input type="hidden" name="iboard" value="${requestScope.detail.iboard}">
        <input style="line-height: 2" type="text" name="ctnt" placeholder="댓글 입력">
        <input type="submit" value="댓글 달기">
    </form>
    </div>
    </c:if>
    <div style="margin-top: 10px"> 댓글 목록 ${requestScope.detail.countcmt}
        <table class="cmtTable">
            <colgroup>
                <col>
                <col width="10%">
                <col width="9%">
                <col width="5%">
            </colgroup>
            <c:forEach items="${requestScope.cmtList}" var="item">
                <c:set var="pImg" value="defaultProfile.jpg"/>
                <c:if test="${item.profileImg !=null}">
                    <c:set var="pImg" value="profile/${item.writer}/${item.profileImg}"/>
                </c:if>
                <tr>
                    <td>${item.ctnt}</td>
                    <td><div><div class="circular--img circular--size40"><img src="/res/img/${pImg}"></div>${item.writerNm}</div></td>
                    <td>${item.rdt}</td>
                    <td>
                        <c:if test="${sessionScope.loginUser.iuser==item.writer}">
                            <button onclick="openModForm(${item.icmt}, '${item.ctnt}')">수정</button>
                            <button onclick="isDelCmt(${requestScope.detail.iboard}, ${item.icmt});">삭제</button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<div class="cmtModContainer">
    <div class="cmtBody">
        <form action="/board/cmt/mod" method="post" id="cmtModFrm">
            <input type="hidden" name="iboard" value="${requestScope.detail.iboard}">
            <input type="hidden" name="icmt">
            <div><input type="text" name="ctnt" placeholder="댓글 내용"></div>
            <div><input type="submit" value="수정"><input type="button" value="취소" id="btnCancel"></div>
        </form>
    </div>
</div>
<script src="/res/js/board/detail.js"></script>