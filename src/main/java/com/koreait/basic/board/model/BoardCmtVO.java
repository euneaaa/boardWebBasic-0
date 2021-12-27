package com.koreait.basic.board.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BoardCmtVO {
    private int icmt;
    private String ctnt;
    private int writer;
    private String rdt;
    private String writerNm;
    private String profileImg;
}
