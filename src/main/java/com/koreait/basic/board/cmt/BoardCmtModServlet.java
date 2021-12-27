package com.koreait.basic.board.cmt;

import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardCmtEntity;
import com.koreait.basic.dao.BoardCmtDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/cmt/mod")
public class BoardCmtModServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        BoardCmtEntity cmtEntity = new BoardCmtEntity();
        int iboard = Utils.getParameterInt(req,"iboard");
        cmtEntity.setIcmt(Utils.getParameterInt(req,"icmt"));
        cmtEntity.setCtnt(req.getParameter("ctnt"));
        cmtEntity.setWriter(Utils.getLoginUserPk(req));
        int result = BoardCmtDAO.updBoardCmt(cmtEntity);

        res.sendRedirect("/board/detail?iboard="+iboard);
    }
}
