package com.example.mvcBoard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mvcBoard.dao.BoardDao;
import com.example.mvcBoard.dto.BoardDto;
import com.example.mvcBoard.dto.FileDto;



//이전에 MVC 패턴에서 사용했던 Command와 비슷하게 비즈니스 로직을 실행하는 역할을 함
@Service("com.example.mvcBoard.service.BoardService")
public class BoardService {

	@Autowired
	BoardDao mBoardDao;

	public List<BoardDto> boardListService() throws Exception{
		return mBoardDao.boardList();
	}

	public BoardDto boardDetailService(int bNo) throws Exception{

		return mBoardDao.boardDetail(bNo);
	}

	public int boardInsertService(BoardDto board) throws Exception{

		return mBoardDao.boardInsert(board);
	}

	public int boardUpdateService(BoardDto board) throws Exception{

		return mBoardDao.boardUpdate(board);
	}

	public int boardDeleteService(int bNo) throws Exception{

		return mBoardDao.boardDelete(bNo);
	}

	public int fileInsertService(FileDto file) throws Exception{
		return mBoardDao.fileInsert(file);
	}

	public FileDto fileDetailService(int bNo) throws Exception{   
		return mBoardDao.fileDetail(bNo);
	}

}
