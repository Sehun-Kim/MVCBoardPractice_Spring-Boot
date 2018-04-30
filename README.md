# MVC Board Practice
#### first Spring Boot Project

###### Tomcat 9.0 
###### DB : MySQL
###### Spring Boot : 2.0.1
###### Mybatis : 3.2.8

### ver 1.0
- 게시글 생성
- 게시물 열람
- 게시물 삭제
- 게시물 수정
- 댓글기능(Ajax)
- file upload & Download

### ver 1.1
- DB에 저장된 모든 data를 csv파일로 다운

### ver 1.2
- DB 데이터를 분할해서 가져오기 위해 메소드 수정(100개씩 가져오기)
- Stream 사용 X, 쿼리문만 반복

### ver 1.3
- DB 데이터를 원하는 구간만큼 잘라서 내 서버에 저장
- 저장 시 Stream을 사용하여 csv 파일을 생성하고 해당파일에 이어 씀
- 저장 후 다운로드
