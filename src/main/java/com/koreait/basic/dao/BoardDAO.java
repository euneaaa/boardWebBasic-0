package com.koreait.basic.dao;

import com.koreait.basic.DbUtils;
import com.koreait.basic.board.model.BoardDTO;
import com.koreait.basic.board.model.BoardEntity;
import com.koreait.basic.board.model.BoardVO;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
    public static int insBoardWithPk(BoardEntity entity) {
        int result = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "INSERT INTO t_board(title, ctnt, writer)" +
                "VALUES (?, ?, ?)";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getCtnt());
            ps.setInt(3, entity.getWriter());
            result = ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if(rs.next()) {
                int iboard = rs.getInt(1);
                entity.setIboard(iboard);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }
        return result;
    }

    public static int insBoard(BoardEntity entity) { //title, ctnt, writer
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO t_board(title, ctnt, writer)" +
                    "VALUES (?, ?, ?)";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getCtnt());
            ps.setInt(3, entity.getWriter());
            return ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps);
        }
        return 0;
    }

    private static String getSearchWhereString(BoardDTO param) {
        if(param.getSearchText() != null && !"".equals(param.getSearchText())) {
            switch(param.getSearchType()) {
                case 1: // 제목
                    return String.format(" WHERE A.title LIKE '%%%s%%'", param.getSearchText());
                case 2: // 내용
                    return String.format(" WHERE A.ctnt LIKE '%%%s%%'", param.getSearchText());
                case 3: // 제목,내용
                    return String.format(" WHERE A.title LIKE '%%%s%%' OR A.ctnt LIKE '%%%s%%'", param.getSearchText(), param.getSearchText());
                case 4: // 글쓴이
                    return String.format(" WHERE B.nm LIKE '%%%s%%'", param.getSearchText());
                case 5: //전체
                    return String.format(" WHERE A.title LIKE '%%%s%%' OR A.ctnt LIKE '%%%s%%' OR B.nm LIKE '%%%s%%'"
                            , param.getSearchText(), param.getSearchText(), param.getSearchText());
            }
        }
        return "";
    }

    public static int getMaxPageNum(BoardDTO param){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String
                sql = "SELECT CEIL(count(A.iboard)/?) FROM t_board A inner join t_user B on A.writer = B.iuser";
        sql += getSearchWhereString(param);

        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, param.getRowCnt());
            rs = ps.executeQuery();
            if(rs.next()){
                int maxPageNum = rs.getInt(1);
                return maxPageNum;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtils.close(con,ps,rs);
        }
        return 0;
    }

    public static List<BoardVO> selBoardList(BoardDTO param) {
        List<BoardVO> list = new ArrayList();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT A.iboard, A.title, A.writer, A.hit, A.rdt, B.nm as writerNm, B.profileImg " +
                " FROM t_board A " +
                " INNER JOIN t_user B " +
                " ON A.writer = B.iuser ";
                sql += getSearchWhereString(param);
                sql += " ORDER BY A.iboard DESC LIMIT ?,? ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1,param.getStartIdx());
            ps.setInt(2,param.getRowCnt());
            rs = ps.executeQuery();
            while(rs.next()) {
                int iboard = rs.getInt("iboard");
                String title = rs.getString("title");
                int writer = rs.getInt("writer");
                int hit = rs.getInt("hit");
                String rdt = rs.getString("rdt");
                String writerNm = rs.getString("writerNm");
                String profileImg = rs.getString("profileImg");
                int countcmt = BoardCmtDAO.countCmt(iboard);
                boolean showNew = BoardDAO.showRdt(iboard);
                BoardVO vo = BoardVO.builder()
                        .iboard(iboard)
                        .title(title)
                        .writer(writer)
                        .countcmt(countcmt)
                        .hit(hit)
                        .rdt(rdt)
                        .writerNm(writerNm)
                        .profileImg(profileImg)
                        .showNew(showNew)
                        .build();
                list.add(vo);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(con, ps, rs);
        }

        return list;
    }

    public static BoardVO selBoardDetail(BoardDTO param){
        BoardVO vo = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT A.iboard, A.title, A.writer, A.ctnt, A.hit, A.rdt, B.nm as writerNm, B.profileImg  " +
                " FROM t_board A " +
                " INNER JOIN t_user B " +
                " ON A.writer = B.iuser WHERE iboard = ?";
        try {
            con = DbUtils.getCon();
            ps= con.prepareStatement(sql);
            ps.setInt(1,param.getIboard());
            rs=ps.executeQuery();
            if(rs.next()){
                int iboard = rs.getInt("iboard");
                String title = rs.getString("title");
                String ctnt = rs.getString("ctnt");
                int writer = rs.getInt("writer");
                int hit = rs.getInt("hit");
                String rdt = rs.getString("rdt");
                String writerNm = rs.getString("writerNm");
                String profileImg = rs.getString("profileImg");
                int countcmt = BoardCmtDAO.countCmt(iboard);
                vo = BoardVO.builder()
                        .iboard(iboard)
                        .title(title)
                        .writer(writer)
                        .ctnt(ctnt)
                        .hit(hit)
                        .rdt(rdt)
                        .writerNm(writerNm)
                        .profileImg(profileImg)
                        .countcmt(countcmt)
                        .build();
                return vo;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtils.close(con,ps,rs);
        }
        return vo;
    }

    public static void updBoardHitup(BoardDTO param){
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "UPDATE t_board SET hit = hit + 1 WHERE iboard = ?";
        try {
            con= DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, param.getIboard());
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtils.close(con, ps);
        }
    }

    public static int delBoard(BoardEntity entity){
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM t_board WHERE iboard=? AND writer = ?";
        try {
            con= DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1, entity.getIboard());
            ps.setInt(2, entity.getWriter());
            return ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtils.close(con,ps);
        }
        return 0;
    }

    public static int updBoard(BoardEntity entity){
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "UPDATE t_board SET title =?, ctnt =? ,mdt WHERE iboard =? AND writer =?";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setString(1,entity.getTitle());
            ps.setString(2,entity.getCtnt());
            ps.setInt(3,entity.getIboard());
            ps.setInt(4,entity.getWriter());
            return ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtils.close(con,ps);
        }
        return 0;
    }

    public static boolean showRdt (int iboard){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT DATE_FORMAT(rdt, '%Y-%m-%d') AS rdt FROM t_board WHERE iboard=? ";
        try {
            con = DbUtils.getCon();
            ps = con.prepareStatement(sql);
            ps.setInt(1,iboard);
            rs= ps.executeQuery();
            if(rs.next()){
            String rdt = rs.getString(1);
            LocalDate day = LocalDate.parse(rdt);
            return day.isAfter(LocalDate.now().minusDays(2));}
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbUtils.close(con,ps, rs);
        }
        return false;
    }
}
