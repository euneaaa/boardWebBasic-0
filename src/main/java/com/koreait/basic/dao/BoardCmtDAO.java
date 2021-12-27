package com.koreait.basic.dao;

import com.koreait.basic.DbUtils;
import com.koreait.basic.board.model.BoardCmtDTO;
import com.koreait.basic.board.model.BoardCmtEntity;
import com.koreait.basic.board.model.BoardCmtVO;
import com.koreait.basic.board.model.BoardDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoardCmtDAO {
    public static int insBoardCmt(BoardCmtEntity entity){
        int result = 0;
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO t_board_cmt(iboard, ctnt, writer) VALUES (?, ?, ?)";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1,entity.getIboard());
            ps.setString(2,entity.getCtnt());
            ps.setInt(3,entity.getWriter());
            result= ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtils.close(con, ps);
        }
        return result;
    }

    public static List<BoardCmtVO> selBoardCmtList(BoardCmtDTO param){
        List<BoardCmtVO> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT A.icmt, A.ctnt, A.writer, A.rdt, B.nm AS writerNm, B.profileImg " +
                "FROM t_board_cmt A " +
                "INNER JOIN t_user B ON A.writer = B.iuser WHERE A.iboard = ? ORDER BY A.icmt ASC";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, param.getIboard());
            rs = ps.executeQuery();
            while (rs.next()){
                BoardCmtVO vo = BoardCmtVO.builder()
                        .icmt(rs.getInt("icmt"))
                        .ctnt(rs.getString("ctnt"))
                        .writer(rs.getInt("writer"))
                        .rdt(rs.getString("rdt"))
                        .writerNm(rs.getString("writerNm"))
                        .profileImg(rs.getString("profileImg"))
                        .build();
                list.add(vo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtils.close(con, ps, rs);
        }
        return list;
    }

    public static int delBoardCmt(BoardCmtEntity entity) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = " DELETE FROM t_board_cmt WHERE icmt = ? AND writer = ? ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, entity.getIcmt());
            ps.setInt(2, entity.getWriter());
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }

    public static int updBoardCmt(BoardCmtEntity entity){
        Connection con = null;
        PreparedStatement ps = null;
        String sql ="UPDATE t_board_cmt SET ctnt = ? WHERE icmt = ? AND writer = ? ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1,entity.getCtnt());
            ps.setInt(2,entity.getIcmt());
            ps.setInt(3,entity.getWriter());
            return ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtils.close(con,ps);
        }
        return 0;
    }

    public static int countCmt(int iboard){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) FROM t_board_cmt WHERE iboard = ?";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, iboard);
            rs= ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtils.close(con, ps, rs);
        }
        return 0;
    }
}
