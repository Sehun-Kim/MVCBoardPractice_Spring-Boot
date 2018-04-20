package com.example.mvcBoard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.mvcBoard.dto.CommentDto;
import com.example.mvcBoard.service.CommentService;



@Controller
@RequestMapping("/comment")
public class CommentController {
 
    @Autowired
    CommentService mCommentService;
    
    
    @RequestMapping("/list") //댓글 리스트
    @ResponseBody // 메소드에서 리턴되는 값은 View 를 통해서 출력되지 않고 HTTP Response Body 에 직접 쓰여짐
    private List<CommentDto> mCommentServiceList(Model model, @RequestParam int bNo) throws Exception{
        System.out.println(bNo);
        return mCommentService.commentListService(bNo);
    }
    
    @RequestMapping("/insert") //댓글 작성 
    @ResponseBody
    private int mCommentServiceInsert(@RequestParam int bNo, @RequestParam String content) throws Exception{
        
        CommentDto comment = new CommentDto();
        comment.setbNo(bNo);
        comment.setContent(content);
        //로그인 기능을 구현했거나 따로 댓글 작성자를 입력받는 폼이 있다면 입력 받아온 값으로 사용하면 됩니다. 저는 따로 폼을 구현하지 않았기때문에 임시로 "test"라는 값을 입력해놨습니다.
        comment.setWriter("test");  
        
        return mCommentService.commentInsertService(comment);
    }
    
    @RequestMapping("/update") //댓글 수정  
    @ResponseBody
    private int mCommentServiceUpdateProc(@RequestParam int cNo, @RequestParam String content) throws Exception{
        
        CommentDto comment = new CommentDto();
        comment.setcNo(cNo);
        comment.setContent(content);
        
        return mCommentService.commentUpdateService(comment);
    }
    
    @RequestMapping("/delete/{cNo}") //댓글 삭제  
    @ResponseBody
    private int mCommentServiceDelete(@PathVariable int cNo) throws Exception{
        
        return mCommentService.commentDeleteService(cNo);
    }
    
}
