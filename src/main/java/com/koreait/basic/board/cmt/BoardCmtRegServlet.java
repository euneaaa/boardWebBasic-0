package com.koreait.basic.board.cmt;

import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardCmtEntity;
import com.koreait.basic.board.model.BoardEntity;
import com.koreait.basic.dao.BoardCmtDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/cmt/reg")
public class BoardCmtRegServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        BoardCmtEntity cmtEntity = new BoardCmtEntity();
        int iboard = Utils.getParameterInt(req,"iboard");
        cmtEntity.setIboard(iboard);
        cmtEntity.setWriter(Utils.getLoginUserPk(req));
        cmtEntity.setCtnt(req.getParameter("ctnt"));
        int result = BoardCmtDAO.insBoardCmt(cmtEntity);

        res.sendRedirect("/board/detail?nohits=1&iboard="+iboard);
    }
}
