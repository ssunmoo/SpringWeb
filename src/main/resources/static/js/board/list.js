
let bcno = 0; // 선택된 카테고리 기본값 0 : 전체보기

// 1. 전체글 출력 [ 1. 페이지 오픈 시, 2. 카테고리 버튼 클릭 시 ]
boardlist()
function boardlist(){

    $.ajax({
         url        : "/board/boardlist",
         type       : "get",
         data       : { "bcno" : bcno }, // 카테고리 번호
         success    : re => {
            let html = '<tr>'
                     + '<th> 번호 </th> <th> 제목 </th> <th> 작성자 </th> <th> 조회수 </th>'
                     + '</tr>';

            re.forEach( (b)=> {
                html += '<tr>'
                     + '<td>'+ b.bno +'</td><td onclick="getview('+b.bno+')">'+ b.btitle +'</td><td>'+ b.memail +'</td><td>'+ b.bview +'</td>'
                     + '</tr>'
            })
            document.querySelector('.btable').innerHTML = html;
         }
    })
} // boardlist e


// 2. 게시물 조회 페이지 [ 페이지 전환 시 클릭한 게시물 번호 기록 ]
// java : 세션 - 서버종료될 때 초기화, js : 페이지 전환 시 초기화 세션/쿠키
function getview( bno ){

    // 1. 클릭한 게시물 번호 저장
    sessionStorage.setItem( "bno", bno );
    // 2. 페이지 전환
    location.href = "/board/view";

} // viewload e


// 3. 카테고리 출력 메소드
bcategorylist()
function bcategorylist(){
    $.ajax({
         url        : "/board/bcategorylist",
         type       : "get",
         success    : re => {
            console.log(re);
            let html = '<button type="button" onclick="bcnochage(0)"> 전체보기 </button> ';
            re.forEach( (b) => {
                html += '<button type="button" onclick="bcnochage('+b.bcno+')">'+b.bcname+'</button> '
            })
            document.querySelector('.bcategorytbox').innerHTML = html;
        }
    })
}

// 4. 카테고리를 선택했을 때 선택된 카테고리 번호 변경 [ 스크립트 실행시 바로 가져옴 ]
function bcnochage( cno ){
    bcno = cno;
    alert(bcno + " 번의 카테고리 선택");
    boardlist(); // 전체 게시물 재출력
}



