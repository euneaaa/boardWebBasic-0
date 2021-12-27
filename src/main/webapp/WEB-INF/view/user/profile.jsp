<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
    <c:set var="pImg" value="defaultProfile.jpg"/>
    <c:if test="${requestScope.data.profileImg != null}">
        <c:set var="pImg" value="profile/${sessionScope.loginUser.iuser}/${requestScope.data.profileImg}"/>
    </c:if>
    <div><img class="circular--img circular--size300" src="/res/img/${pImg}"></div>
    <div>
        <div>아이디 : ${sessionScope.loginUser.uid}</div>
        <div>이름 : ${sessionScope.loginUser.nm}</div>
        <div>성별 : ${sessionScope.loginUser.gender}</div>
        <div>가입일 : ${sessionScope.loginUser.rdt}</div>
    </div>
    <div>
        <form action="/user/profile" method="post" enctype="multipart/form-data">
            <div><label>이미지 : <input type="file" name="profileImg" accept="image/*"></label></div>
            <div><input type="submit" value="이미지 업로드"></div>
        </form>
    </div>
</div>