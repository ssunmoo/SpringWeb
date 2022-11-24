
// 1. 세션에 저장한 bno 세션스토리지 호출
let bno = sessionStorage.getItem("bno");

// 2. 수정 전 게시물 정보 호출
getboard()
function getboard(){

    $.ajax({
        url : "/board/getboard",
        type : "get",
        data : { "bno" : bno },
        success : function(re){
            console.log(re);
        }
    })
}

// 3. 수정버튼 클릭 시 호출되는 메소드
function upboard(){

    let boardupdate = document.querySelector('.boardupdate')
    let formdata = new FormData( boardupdate );
    formdata.set("bno", bno );

    console.log( formdata );
    $.ajax({
         url        : "/board/upboard",
         type       : "put",
         data       : formdata,   // 객체로 통신하기 위해
        contentType: false, // multipart 전송방법 2
        processData: false, // multipart 전송방법 3
         success    : re => {
            if( re == true ){
                alert("글 수정 성공");
                location.href = "/board/list"
            }else{
                alert("글 수정 실패");
            }
         }
    })
}