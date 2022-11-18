
boardlist()
function boardlist(){

    $.ajax({
         url        : "/board/boardlist",
         type       : "get",
         success    : re => {
            let html = '<tr>'
                     + '<th> 번호 </th> <th> 제목 </th> <th> 작성자 </th> <th> 조회수 </th>'
                     + '</tr>';

            re.forEach( (b)=> {
                html += '<tr>'
                     + '<td>'+ b.bno +'</td><td onclick="getview('+b.bno+')">'+ b.btitle +'</td><td>'+ b.memail +'</td><td>'+ b.bview +'</td>'
                     + '</tr>'
            })
            document.querySelector('.btable').innerHTML += html;
         }
    })
} // boardlist e


// 2. 게시물 조회 페이지 [ 페이지 전환 시 클릭한 게시물 번호 기록 ]
// java : 세션 - 서버종료될 때 초기화, js : 페이지 전환 시 초기화 세션/쿠키
function getview( bno ){

    // 1. 클릭한 게시물 번호 저장
    sessionStorage.setItem( "bno", bno );
    console.log("getview ::" + bno);
    // 2. 페이지 전환
    location.href = "/board/view";

} // viewload e





