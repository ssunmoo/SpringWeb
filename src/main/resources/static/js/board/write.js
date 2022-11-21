
// 카테고리 기본값 [ 카테고리 선택안 할 경우 기본 값 ]
let bcno = 2; // 카테고리 번호 -> 2 : 자유게시판

// 1. 게시물 등록 메소드
function setboard(){

    let data = {
        btitle : document.querySelector('.btitle').value,
        bcontent : document.querySelector('.bcontent').value,
        bfile : document.querySelector('.bfile').value,
        bcno : bcno
    }

    $.ajax({
         url        : "/board/setboard",
         type       : "post",
         data       : JSON.stringify( data ), // 객체로 통신하기 위해
         contentType: "application/json", // 전송 타입 : application/json
         success    : re => {
            console.log(re);
            if( re == true ){
                location.href = "/board/list"
            }else{
                alert("글 작성 실패");
            }
         }
    })
}

// 2. 카테고리 추가 메소드
function setbcategory(){
    let data = { bcname : document.querySelector('.bcname').value }

    $.ajax({
         url        : "/board/setbcategory",
         type       : "post",
         data       : JSON.stringify( data ), // 객체로 통신하기 위해
         contentType: "application/json",    // 전송 타입 : application/json
         success    : re => {
            if( re == true ){
                alert("카테고리 등록 성공");
                bcategorylist();
            }else{
                alert("카테고리 등록 실패");
            }
         }
    })
}

// 3. 카테고리 출력 메소드
bcategorylist()
function bcategorylist(){
    $.ajax({
         url        : "/board/bcategorylist",
         type       : "get",
         success    : re => {
            console.log(re);
            let html = '';
            re.forEach( (b) => {
                html += '<button type="button" onclick="bcnochage('+b.bcno+')">'+b.bcname+'</button> '
            })
            document.querySelector('.bcategorytbox').innerHTML = html;
            cbtn = document.querySelectorAll('.cbtn'); // 추가 or 변경 시 생성된 카테고리 버튼들의 리스트 호출
        }
    })
}

// 4. 카테고리를 선택했을 때 선택된 카테고리 번호 변경 [ 스크립트 실행시 바로 가져옴 ]
function bcnochage( cno ){
    bcno = cno;
    alert(bcno + " 번의 카테고리 선택");
}