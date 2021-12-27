package com.koreait.basic.board;

import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardEntity;
import com.koreait.basic.board.model.BoardVO;
import com.koreait.basic.dao.BoardDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/board/del")
public class BoardDelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int iboard = Utils.getParameterInt(req,"iboard");
        BoardEntity entity = new BoardEntity();
        entity.setWriter(Utils.getLoginUserPk(req));
        entity.setIboard(iboard);
        int rs = BoardDAO.delBoard(entity);
        switch (rs){
            case 1 :
                res.sendRedirect("/board/list");
                break;
            default:
                req.setAttribute("err", "글 삭제를 실패하였습니다.");
                req.getRequestDispatcher("/board/detail?iboard="+iboard).forward(req,res);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }
}
