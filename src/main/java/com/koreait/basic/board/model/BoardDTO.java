package com.koreait.basic.board.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {
    private int iboard;
    private int page;
    private int startIdx;
    private int rowCnt;
    private int searchType;
    private String searchText;
    private int loginUserPk;

    public void setStartIdx(int page) {
        this.page = page;
        this.startIdx = (page-1)*rowCnt;
    }
}
