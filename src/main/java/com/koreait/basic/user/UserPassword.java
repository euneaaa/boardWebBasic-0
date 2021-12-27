package com.koreait.basic.user;

import com.koreait.basic.Utils;
import com.koreait.basic.dao.UserDAO;
import com.koreait.basic.user.model.UserEntity;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/password")
public class UserPassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String title = "비밀번호 변경";
        req.setAttribute("subPage", "user/password");
        Utils.displayView(title,"user/myPage", req,res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String upw = req.getParameter("upw");
        String changedUpw = req.getParameter("changedUpw");
        UserEntity loginUser =new UserEntity();
        loginUser.setIuser(Utils.getLoginUserPk(req));
        UserEntity myInfo = UserDAO.selUser2(loginUser);
        if(BCrypt.checkpw(upw, myInfo.getUpw())){
            String hashPw = BCrypt.hashpw(changedUpw, BCrypt.gensalt());
            myInfo.setUpw(hashPw);
            int result = UserDAO.updUser(myInfo);
            res.sendRedirect("/user/logout");
        }else {
            String err = "비밀번호가 다릅니다.";
            req.setAttribute("err", err);

            doGet(req,res);
        }
    }
}
