<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" href="/res/css/board/list.css?ver=15">
<h1>${requestScope.title}</h1>
<c:choose>
    <c:when test="${fn:length(requestScope.list) == 0}">
        <div>랭킹이 없습니다.</div>
    </c:when>
    <c:otherwise>
    <div>
        <table id="boardTable">
            <colgroup>
                <col width="8%">
                <col>
                <col width="5%">
                <col width="10%">
                <col width="15%">
            </colgroup>
            <tr>
                <th>no</th>
                <th>title</th>
                <th>${param.type == 1 ? 'hit' : 'count'}</th>
                <th>writer</th>
                <th>reg-datetime</th>
            </tr>
            <c:forEach items="${requestScope.list}" var="item">
                <c:set var="eachTitle" value="${fn:replace(fn:replace(item.title, '>', '&gt;'), '<', '&lt;')}"/>
                <c:if test="${param.searchText != null && (param.searchType == 1 || param.searchType == 3 || param.searchType == 5)}">
                    <c:set var="eachTitle" value="${fn:replace(eachTitle, param.searchText, '<mark>' += param.searchText += '</mark>')}" />
                </c:if>

                <c:set var="eachWriterNm" value="${item.writerNm}" />
                <c:if test="${param.searchText != null && (param.searchType == 4 || param.searchType == 5)}">
                    <c:set var="eachWriterNm" value="${fn:replace(eachWriterNm, param.searchText, '<mark>' += param.searchText += '</mark>')}" />
                </c:if>
                <c:set var="pImg" value="defaultProfile.jpg"/>
                <c:if test="${item.profileImg !=null}">
                    <c:set var="pImg" value="profile/${item.writer}/${item.profileImg}"/>
                </c:if>
                <tr class="record" onclick="moveToDetail(${item.iboard});">
                    <td>${item.iboard}</td>
                    <td>${eachTitle}
                        <c:if test="${item.countcmt != 0}">
                            <input class="cmtbtn" type="button" value="${item.countcmt}">
                        </c:if>
                    </td>
                    <td>${item.cnt}</td>
                    <td><div><div class="circular--img circular--size40"><img src="/res/img/${pImg}"></div>${eachWriterNm}</div></td>
                    <td>${item.rdt}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    </c:otherwise>
</c:choose>
<script src="/res/js/board/list.js?ver=2"></script>