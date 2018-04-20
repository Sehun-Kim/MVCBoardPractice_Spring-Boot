package com.example.mvcBoard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.mvcBoard.dto.CommentDto;



@Repository("com.example.mvcBoard.dao.CommentDao")
@Mapper // mapper를 선언해주어야 함
public interface CommentDao {
    // 댓글 개수
    public int commentCount() throws Exception;
 
    // 댓글 목록
    public List<CommentDto> commentList(int bNo) throws Exception;
 
    // 댓글 작성
    public int commentInsert(CommentDto comment) throws Exception;
    
    // 댓글 수정
    public int commentUpdate(CommentDto comment) throws Exception;
 
    // 댓글 삭제
    public int commentDelete(int cNo) throws Exception;
 
}
