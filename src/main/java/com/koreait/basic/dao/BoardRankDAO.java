package com.koreait.basic.dao;

import com.koreait.basic.DbUtils;
import com.koreait.basic.board.model.BoardVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BoardRankDAO {
    public static List<BoardVO> selBoardHitsRankList() {
        List<BoardVO> list = new ArrayList<>();
        String sql = " SELECT A.iboard, A.title, A.writer, A.rdt, A.hit as cnt " +
                " , B.nm AS writerNm , B.profileImg " +
                " FROM t_board A " +
                " INNER JOIN t_user B " +
                " ON A.writer = B.iuser " +
                " WHERE A.hit > 0 " +
                " ORDER BY A.hit DESC, A.iboard DESC" +
                " LIMIT 10 ";
        procResultSet(sql, list);
        return list;
    }

    private static void procResultSet(String sql, List<BoardVO> list) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                BoardVO vo = BoardVO.builder()
                        .iboard(rs.getInt("iboard"))
                        .title(rs.getString("title"))
                        .writer(rs.getInt("writer"))
                        .cnt(rs.getInt("cnt"))
                        .rdt(rs.getString("rdt"))
                        .writerNm(rs.getString("writerNm"))
                        .profileImg(rs.getString("profileImg"))
                        .countcmt(BoardCmtDAO.countCmt(rs.getInt("iboard")))
                        .build();
                list.add(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }
    }

    public static List<BoardVO> selBoardCmtRankList() {
        List<BoardVO> list = new ArrayList<>();
        String sql = " SELECT A.iboard, A.title, A.writer, A.rdt, C.cnt " +
                " , B.nm AS writerNm , B.profileImg " +
                " FROM t_board A " +
                " INNER JOIN t_user B " +
                " ON A.writer = B.iuser " +
                " INNER JOIN " +
                " (SELECT iboard, COUNT(icmt) AS cnt FROM t_board_cmt GROUP BY iboard) C " +
                " ON A.iboard = C.iboard " +
                " ORDER BY C.cnt DESC " +
                " LIMIT 10 ";
        procResultSet(sql, list);
        return list;
    }

    // 좋아요 TOP 10
    public static List<BoardVO> selBoardHeartRankList() {
        List<BoardVO> list = new ArrayList<>();
        String sql = " SELECT A.iboard, A.title, A.writer, A.rdt, C.cnt " +
                " , B.nm AS writerNm , B.profileImg " +
                " FROM t_board A " +
                " INNER JOIN t_user B " +
                " ON A.writer = B.iuser " +
                " INNER JOIN " +
                " (SELECT iboard, COUNT(iuser) AS cnt FROM t_board_heart GROUP BY iboard) C " +
                " ON A.iboard = C.iboard " +
                " ORDER BY C.cnt DESC " +
                " LIMIT 10 ";
        procResultSet(sql, list);
        return list;
    }
}
