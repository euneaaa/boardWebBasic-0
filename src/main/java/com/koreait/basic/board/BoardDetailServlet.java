package com.koreait.basic.board;

import com.koreait.basic.Utils;
import com.koreait.basic.board.model.BoardCmtDTO;
import com.koreait.basic.board.model.BoardDTO;
import com.koreait.basic.board.model.BoardHeartEntity;
import com.koreait.basic.board.model.BoardVO;
import com.koreait.basic.dao.BoardCmtDAO;
import com.koreait.basic.dao.BoardDAO;
import com.koreait.basic.dao.BoardHeartDAO;
import com.koreait.basic.user.model.LoginResult;
import com.koreait.basic.user.model.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/board/detail")
public class BoardDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        BoardDTO param = new BoardDTO();
        int nohits = Utils.getParameterInt(req, "nohits");
        int iboard = Utils.getParameterInt(req,"iboard");
        param.setIboard(iboard);
        BoardVO data = BoardDAO.selBoardDetail(param);

        BoardCmtDTO cmtParam = new BoardCmtDTO();
        cmtParam.setIboard(iboard);
        req.setAttribute("cmtList", BoardCmtDAO.selBoardCmtList(cmtParam));

        int loginUserPk = Utils.getLoginUserPk(req);
        if(loginUserPk > 0) {
            BoardHeartEntity bhParam = new BoardHeartEntity();
            bhParam.setIuser(loginUserPk);
            bhParam.setIboard(iboard);
            req.setAttribute("isHeart", BoardHeartDAO.selIsHeart(bhParam));
        }
        if(data.getWriter() != loginUserPk && nohits != 1){
            BoardDAO.updBoardHitup(param);
        }
        req.setAttribute("detail", data);
        Utils.displayView("디테일", "board/detail", req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    }
}
