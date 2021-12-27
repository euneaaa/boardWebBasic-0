<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" href="/res/css/board/list.css?ver=15">
<div>
    <form action="/board/list" method="get" id="searchFrm" class="searchheader">
        <div>
            <select name="searchType">
                <option value="1" ${param.searchType == 1 ? 'selected' : ''}>제목</option>
                <option value="2" ${param.searchType == 2 ? 'selected' : ''}>내용</option>
                <option value="3" ${param.searchType == 3 ? 'selected' : ''}>제목/내용</option>
                <option value="4" ${param.searchType == 4 ? 'selected' : ''}>글쓴이</option>
                <option value="5" ${param.searchType == 5 ? 'selected' : ''}>전체</option>
            </select>
            <input type="search" name="searchText" value="${param.searchText}">
            <input type="submit" value="검색">
        </div>
        <div>
            게시글 수:
            <select name="rowCnt">
                <c:forEach var="cnt" begin="5" end="30" step="5">
                    <option value="${cnt}" ${cnt == param.rowCnt ? 'selected' : ''}>${cnt}개</option>
                </c:forEach>
            </select>
        </div>
    </form>
</div>
<c:choose>
    <c:when test="${requestScope.maxPageNum == 0}">
        <div>글이 없습니다.</div>
    </c:when>
    <c:otherwise>
        <div>
            <table class="boardTable">
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
                    <th>hits</th>
                    <th>writer</th>
                    <th>reg-datetime</th>
                </tr>
                <c:forEach items="${requestScope.list}" var="item">
                    <c:set var="eachTitle" value="${fn:replace(fn:replace(item.title, '>', '&gt;'), '<', '&lt;')}" />
                    <c:set var="eachwriterNm" value="${fn:replace(fn:replace(item.writerNm, '>', '&gt;'), '<', '&lt;')}" />
                    <c:if test="${param.searchType != null&& param.searchType == 1 || param.searchType == 3 || param.searchType == 5}">
                        <c:set var="eachTitle" value="${fn:replace(eachTitle, param.searchText, '<mark>' += param.searchText += '</mark>')}" />
                    </c:if>
                    <c:if test="${param.searchType != null&& param.searchType == 4 || param.searchType == 5}">
                        <c:set var="eachwriterNm" value="${fn:replace(eachwriterNm, param.searchText, '<mark>' += param.searchText += '</mark>')}" />
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
                            <c:if test="${item.showNew==true}">
                            <input class="newbtn" type="button" value="new">
                            </c:if>
                        </td>
                        <td>${item.hit}</td>
                        <td><div class="writerImg"><div class="circular--img circular--size40"><img src="/res/img/${pImg}"></div>${eachwriterNm}</div>
                        </td>

                        <td>${item.rdt}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="pageContainer">
            <c:set var="selectedPage" value="${param.page == null? 1: param.page}"/>
            <c:forEach var = "page" begin= "1" end = "${maxPageNum}">
                <div><a href="/board/list?page=${page}&searchType=${param.searchType}&searchText=${param.searchText}&rowCnt=${param.rowCnt}"><span class="${selectedPage==page? 'selected' : ''}"><c:out value="${page}"/></span> </a></div>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>

<script src="/res/js/board/list.js?ver=1"></script>