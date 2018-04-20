package com.example.mvcBoard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.mvcBoard.dto.BoardDto;
import com.example.mvcBoard.dto.FileDto;

//@Repository : DB에 접근하는 클래스임을 명시 
@Repository("com.example.mvcBoard.dao.BoardDao")
@Mapper // mapper를 선언해주어야 Bean 객체로 사용할 수 있다
public interface BoardDao {
	
	//게시글 개수  
	public int boardCount() throws Exception;

	//게시글 목록  
	public List<BoardDto> boardList() throws Exception;

	//게시글 상세
	public BoardDto boardDetail(int bNo) throws Exception;

	//게시글 작성  
	public int boardInsert(BoardDto board) throws Exception;

	//게시글 수정  
	public int boardUpdate(BoardDto board) throws Exception;

	//게시글 삭제  
	public int boardDelete(int bNo) throws Exception;
		
	//파일 업로드
	public int fileInsert(FileDto file) throws Exception;
		
	//파일 상세
	public FileDto fileDetail(int bNo) throws Exception;

	
}
