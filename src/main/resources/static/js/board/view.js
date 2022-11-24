

let bno = sessionStorage.getItem( "bno" );
alert( bno );

getboard();
// 1. 선택한 게시물 출력
function getboard(){
    $.ajax({
        url : "/board/getboard",
        type : "get",
        data : { "bno" : bno },
        success : re => {
           let html = '<tr>'
                    + '<th> 번호 </th><th> 제목 </th><th> 내용 </th><th> 작성자 </th><th> 첨부파일 </th><th> 조회수 </th><th> 다운로드 </th>'
                    + '</tr>'
                    + '<tr>'
                    + '<td>'+ re.bno +'</td><td>'+ re.btitle +'</td><td>'+ re.bcontent
                    + '</td><td>'+ re.mno +'</td><td class="filename">'+ re.bfilename +'</td><td>'+ re.bview +'</td>'
                    + '<td><button onclick="download()"> 파일다운로드 </button></td>'
                    + '</tr>';
            document.querySelector('.board_view').innerHTML = html;
           }
    })
} // getboard e

// 2. 선택한 게시물 삭제
function delboard(){
    $.ajax({
        url : "/board/delboard",
        type : "delete",
        data : { "bno" : bno },
        success : function(re){
            location.href = "/board/list";
        }
    })
} // delboard e

// 3. 선택한 게시물 첨부파일 다운로드
function download(){
    let filename = document.querySelector('.filename').innerHTML;
    location.href="/board/filedownload?filename="+filename;
}