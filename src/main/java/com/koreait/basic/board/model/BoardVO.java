package com.koreait.basic.board.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardVO {
    private int iboard;
    private String title;
    private String ctnt;
    private int writer;
    private int hit;
    private int cnt;
    private String rdt;
    private String mdt;
    private int countcmt;
    private boolean showNew;

    private String writerNm;
    private String profileImg;
}
