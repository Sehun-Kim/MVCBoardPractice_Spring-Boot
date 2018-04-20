<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script>
var bNo = '${detail.bNo}'; //게시글 번호
 
$('[name=commentInsertBtn]').click(function(){ //댓글 등록 버튼 클릭시 
    var insertData = $('[name=commentInsertForm]').serialize(); //commentInsertForm의 내용을 가져옴
    commentInsert(insertData); //Insert 함수호출(아래)
});
 

$(document).ready(function(){
    commentList(); //페이지 로딩시 댓글 목록 출력 
});
 
//댓글 목록 
function commentList(){
    $.ajax({
        url : '/springMVCBoard/comment/list',
        type : 'get',
        data : {'bNo':bNo},
        success : function(data){
            var a =''; 
            $.each(data, function(key, value){ 
                a += '<div class="commentArea" style="border-bottom:1px solid darkgray; margin-bottom: 15px;">';
                a += '<div class="commentInfo'+value.cNo+'">'+'댓글번호 : '+value.cNo+' / 작성자 : '+value.writer;
                a += '<a onclick="commentUpdate('+value.cNo+',\''+value.content+'\');"> 수정 </a>';
                a += '<a onclick="commentDelete('+value.cNo+');"> 삭제 </a> </div>';
                a += '<div class="commentContent'+value.cNo+'"> <p> 내용 : '+value.content +'</p>';
                a += '</div></div>';
            });
            
            $(".commentList").html(a);
        }
    });
}
 
//댓글 등록
function commentInsert(insertData){
    $.ajax({
        url : '/springMVCBoard/comment/insert',
        type : 'post',
        data : insertData,
        success : function(data){
            if(data == 1) {
                commentList(); //댓글 작성 후 댓글 목록 reload
                $('[name=content]').val('');
            }
        }
    });
}
 
//댓글 수정 - 댓글 내용 출력을 input 폼으로 변경 
function commentUpdate(cNo, content){
    var a ='';
    
    a += '<div class="input-group">';
    a += '<input type="text" class="form-control" name="content_'+cNo+'" value="'+content+'"/>';
    a += '<span class="input-group-btn"><button class="btn btn-default" type="button" onclick="commentUpdateProc('+cNo+');">수정</button> </span>';
    a += '</div>';
    
    $('.commentContent'+cNo).html(a);
    
}
 
//댓글 수정
function commentUpdateProc(cNo){
    var updateContent = $('[name=content_'+cNo+']').val();
    
    $.ajax({
        url : '/springMVCBoard/comment/update',
        type : 'post',
        data : {'content' : updateContent, 'cNo' : cNo},
        success : function(data){
            if(data == 1) commentList(bNo); //댓글 수정후 목록 출력 
        }
    });
}
 
//댓글 삭제 
function commentDelete(cNo){
    $.ajax({
        url : '/springMVCBoard/comment/delete/'+cNo,
        type : 'post',
        success : function(data){
            if(data == 1) commentList(bNo); //댓글 삭제후 목록 출력 
        }
    });
}
 
</script>