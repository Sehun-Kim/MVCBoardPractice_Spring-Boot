package com.example.mvcBoard.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.mvcBoard.dto.BoardDto;
import com.example.mvcBoard.dto.FileDto;
import com.example.mvcBoard.service.BoardService;


@Controller
@RequestMapping("/board")
public class BoardController {
 
	// @Autowired 와 @Resource는 같은 역할을 한다. 단 @Autowired는 타입으로 bean 객체를 찾아 맵핑하고 @Resource는 이름으로 먼저 매핑한다.
	// @Resource(name="com.example.mvcboard.service.BoardService")
    @Autowired
    BoardService mBoardService;
    
    // uploadFileDir에 properties에 만든 property 주입
    @Value("${file.upload.directory}")
    String uploadFileDir;

    @RequestMapping("/list") //게시판 리스트 화면 호출  
    private String boardList(Model model) throws Exception{
        
        model.addAttribute("list", mBoardService.boardListService());
        
        return "board/list"; //생성할 jsp
    }
    
    // @RequestMapping은 {변수}를 사용해서 경로 변수로 사용할 수 있다. 
    // @PathVariable 으로 경로 {변수} 값을 파라미터로 전달 받을 수 있음
    @RequestMapping("/{bNo}") 
    private String boardDetail(@PathVariable int bNo, Model model) throws Exception{
        
        model.addAttribute("detail", mBoardService.boardDetailService(bNo));
        model.addAttribute("files", mBoardService.fileDetailService(bNo)); //추가

        return "board/detail";
    }
    
    @RequestMapping("/insert") //게시글 작성폼 호출  
    private String boardInsertForm(){
        
        return "board/insert";
    }
    
    // @RequestPart 어노테이션으로 MultiPart type의 파일을 객체로 넘겨받을 수 있다.
    @RequestMapping("/insertProc")
    private String boardInsertProc(HttpServletRequest request, @RequestPart MultipartFile files) throws Exception{
        
    	BoardDto board = new BoardDto();
    	FileDto  file  = new FileDto();

    	board.setSubject(request.getParameter("subject"));
    	board.setContent(request.getParameter("content"));
    	board.setWriter(request.getParameter("writer"));
    	
        if(files.isEmpty()){ //업로드할 파일이 없을 시
            mBoardService.boardInsertService(board); //게시글 insert
        }else{
            String fileName = files.getOriginalFilename(); // file의 원래 이름을 넘겨 받는다.
            String fileNameExtension = FilenameUtils.getExtension(fileName).toLowerCase(); // file의 확장자를 얻는다.
            File destinationFile; // 실제로 저장될 파일 객체
            String destinationFileName; // 실제로 저장될 파일 이름
           
            do { 
                destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension; 
                destinationFile = new File(uploadFileDir, destinationFileName); 
            } while (destinationFile.exists()); 
            
            destinationFile.getParentFile().mkdirs(); 
            files.transferTo(destinationFile); // 임시 저장되어 있던 파일이 영구저장 된다.
            
            mBoardService.boardInsertService(board); //게시글 insert
            
            file.setbNo(board.getbNo());
            file.setFileName(destinationFileName);
            file.setFileOriName(fileName);
            file.setFileUrl(uploadFileDir);
            
            mBoardService.fileInsertService(file); //file insert
        }

    	return "redirect:/board/list";
    }
    
    @RequestMapping("/update/{bNo}") //게시글 수정폼 호출  
    private String boardUpdateForm(@PathVariable int bNo, Model model) throws Exception{
        
        model.addAttribute("detail", mBoardService.boardDetailService(bNo));
        
        return "board/update";
    }
    
    @RequestMapping("/updateProc")
    private String boardUpdateProc(HttpServletRequest request) throws Exception{
        
        BoardDto board = new BoardDto();
        board.setSubject(request.getParameter("subject"));
        board.setContent(request.getParameter("content"));
        board.setbNo(Integer.parseInt(request.getParameter("bNo")));
        
        mBoardService.boardUpdateService(board);
        
        return "redirect:/board/"+ request.getParameter("bNo"); 
    }
 
    @RequestMapping("/delete/{bNo}")
    private String boardDelete(@PathVariable int bNo) throws Exception{
        
        mBoardService.boardDeleteService(bNo);
        
        return "redirect:/board/list";
    }
    
    // 모든 게시물을 csv파일로 다운로드 받기 위한 메소드
    @RequestMapping("/csvdownload")
    private ResponseEntity<String>  csvDownload() throws Exception {
    	
    	// csv file 이름
    	Calendar cal = Calendar.getInstance(); 
    	String fileName = cal.getTime().toString();
    	
    	int startNum = 0; // table의 데이터를 getLimit개씩 가져올 때 시작 레코드를 지정해줌
    	int limit = 100; // 한번에 가져올 레코드의 크기
    	List<BoardDto> dtoList = new ArrayList<BoardDto>(); // data가 들어갈 list
    	
    	// dtoList에 mybatis를 통해 얻어온 data를 넣어줌
    	while(true) {
    		List<BoardDto> tmpList = mBoardService.boardDivisionService(startNum, limit); // Service 객체로 limit의 크기만큼 레코드를 가져옴
    		if(tmpList.size() == 0) { // 더 이상 가져올 레코드가 없으면 종료
    			break;
    		}else { // 가져온 레코드의 크기 만큼 dtoList에 넣어줌
    			for(int i=0; i<tmpList.size(); i++) {
        			dtoList.add(tmpList.get(i));
        		}
        		startNum += tmpList.size(); // 가져올 레코드의 시작점을 지정(가져온 레코드의 다음 레코드를 지정)
    		}
    	}

    	HttpHeaders header = new HttpHeaders();
    	header.add("Content-Type", "text/csv; charset=MS949");
    	header.add("Content-Disposition", "attachment; filename=\"" + fileName +".csv" + "\"");

    	return new ResponseEntity<String>(setContent(dtoList), header, HttpStatus.CREATED);
    }
    
    // board의 데이터를 csv파일에 옮겨주는 메소드
	private String setContent(List<BoardDto> dtoList) {
		StringBuffer sb = new StringBuffer();
		sb.append("bNo,subject,content,writer,reg_date\n");
		
		for(int i=0; i<dtoList.size(); i++) {
			int bId = dtoList.get(i).getbNo();
			String subject = dtoList.get(i).getSubject();
			String content = dtoList.get(i).getContent();
			String writer = dtoList.get(i).getWriter();
			String reg_date = dtoList.get(i).getReg_date().toString();
			
			sb.append(bId + "," + subject + "," + content + "," + writer + "," + reg_date + ",\n");
		}
		
		return sb.toString();
	}

	// 파일 다운로드를 위한 메소드
    @RequestMapping("/fileDown/{bNo}")
    private void fileDown(@PathVariable int bNo, HttpServletRequest request, HttpServletResponse response) throws Exception{
        
        request.setCharacterEncoding("UTF-8");
        FileDto FileDto = mBoardService.fileDetailService(bNo);
        
        //파일 업로드된 경로 
        try{
            String fileUrl = FileDto.getFileUrl(); // file이 저장된 경로
            fileUrl += "/"; // 경로에 /를 붙혀줌
            String savePath = fileUrl; 
            String fileName = FileDto.getFileName(); // file의 이름
            
            //실제 내보낼 파일명 
            String oriFileName = FileDto.getFileOriName(); // file의 원래 이름
            
            InputStream in = null; // inputStream 
            OutputStream os = null; // outputStream
            File file = null;
            boolean skip = false;
            String client = "";
            
            //파일을 읽어 스트림에 담기  
            try{
                file = new File(savePath, fileName);
                in = new FileInputStream(file);
            } catch (FileNotFoundException fe) {
                skip = true;
            }
            
            client = request.getHeader("User-Agent");
            
            //파일 다운로드 헤더 지정 
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Description", "JSP Generated Data");
            
            if (!skip) { // skip이 false일 때
                // IE
                if (client.indexOf("MSIE") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\""
                            + java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                    // IE 11 이상.
                } else if (client.indexOf("Trident") != -1) {
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
                } else {
                    // 한글 파일명 처리
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(oriFileName.getBytes("UTF-8"), "ISO8859_1") + "\"");
                    response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                }
                response.setHeader("Content-Length", "" + file.length());
                os = response.getOutputStream();
                byte b[] = new byte[(int) file.length()];
                int leng = 0;
                while ((leng = in.read(b)) > 0) {
                    os.write(b, 0, leng);
                }
            } else {
                response.setContentType("text/html;charset=UTF-8");
                System.out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>");
            }
            in.close();
            os.close();
        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
        
    }
    
}

