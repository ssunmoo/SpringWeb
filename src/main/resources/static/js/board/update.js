
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
    let data = {
            btitle : document.querySelector('.btitle').value,
            bcontent : document.querySelector('.bcontent').value,
            bfile : document.querySelector('.bfile').value,
            bno : bno // 수정할 대상
        }
    console.log( data );
    $.ajax({
         url        : "/board/upboard",
         type       : "put",
         data       : JSON.stringify( data ),   // 객체로 통신하기 위해
         contentType: "application/json",       // 전송 타입 : application/json
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