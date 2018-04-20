package com.example.mvcBoard.dto;

import java.util.Date;

public class CommentDto {
 
    private int cNo;
    private int bNo;
    private String content;
    private String writer;
    private Date reg_date;
 
    public int getcNo() {
        return cNo;
    }
 
    public void setcNo(int cNo) {
        this.cNo = cNo;
    }
 
    public int getbNo() {
        return bNo;
    }
 
    public void setbNo(int bNo) {
        this.bNo = bNo;
    }
 
    public String getContent() {
        return content;
    }
 
    public void setContent(String content) {
        this.content = content;
    }
 
    public String getWriter() {
        return writer;
    }
 
    public void setWriter(String writer) {
        this.writer = writer;
    }
 
    public Date getReg_date() {
        return reg_date;
    }
 
    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }
 
}