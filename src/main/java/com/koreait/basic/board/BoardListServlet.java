package com.koreait.basic.board;

import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardCmtDTO;
import com.koreait.basic.board.model.BoardDTO;
import com.koreait.basic.board.model.BoardVO;
import com.koreait.basic.dao.BoardCmtDAO;
import com.koreait.basic.dao.BoardDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/board/list")
public class BoardListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int searchType = Utils.getParameterInt(req,"searchType",0);
        String searchText = req.getParameter("searchText");
        int rowCnt = Utils.getParameterInt(req,"rowCnt", 5);
        int page = Utils.getParameterInt(req,"page",1);

        BoardDTO param = new BoardDTO();
        param.setSearchText(searchText);
        param.setSearchType(searchType);
        param.setRowCnt(rowCnt);
        param.setStartIdx(page);
        List<BoardVO> list = BoardDAO.selBoardList(param);
        req.setAttribute("maxPageNum", BoardDAO.getMaxPageNum(param));
        req.setAttribute("list", list);


        Utils.displayView("게시판", "board/list", req, res);
    }
}