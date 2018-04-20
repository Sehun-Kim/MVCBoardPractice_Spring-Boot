package com.example.mvcBoard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mvcBoard.dao.CommentDao;
import com.example.mvcBoard.dto.CommentDto;



@Service("com.example.mvcBoard.service.CommentService")
public class CommentService {
 
    @Autowired
    CommentDao mCommentDao;
    
    public List<CommentDto> commentListService(int bNo) throws Exception{
        
    	System.out.println(bNo);
    	
        return mCommentDao.commentList(bNo);
    }
    
    public int commentInsertService(CommentDto comment) throws Exception{
        
        return mCommentDao.commentInsert(comment);
    }
    
    public int commentUpdateService(CommentDto comment) throws Exception{
        
        return mCommentDao.commentUpdate(comment);
    }
    
    public int commentDeleteService(int cNo) throws Exception{
        
        return mCommentDao.commentDelete(cNo);
    }
}

