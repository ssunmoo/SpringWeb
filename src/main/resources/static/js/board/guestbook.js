
let gbcno = 0;

// 1 .게시글 작성
function setguestbook(){

let data = {
        gbcontent : document.querySelector('.gbcontent').value,
        gbname : document.querySelector('.gbname').value
    }

    $.ajax({
         url        : "/board/setguestbook",
         type       : "post",
         data       : JSON.stringify( data ), // 객체로 통신하기 위해
         contentType: "application/json", // 전송 타입 : application/json
         success    : re => {
            console.log(re);
            if( re == true ){
                alert("글 작성 성공");
                location.href = "/board/guestbook"
            }else{
                alert("글 작성 실패");
            }
         }
    })
}

// 2. 전체 글 출력
getguestbook()
function getguestbook(){
    $.ajax({
         url        : "/board/getguestbook",
         type       : "get",
         success    : re => {
            console.log(re);
            let html = '<tr>'
                     + '<th> 번호 </th><th> 내용 </th><th> 작성자 </th>'
                     + '</tr>';
            re.forEach( (b) => {
                html += '<tr>'
                     +  '<td>'+b.gbno+'</td><td>'+b.gbcontent+'</td><td>'+b.gbname+'</td>'
                     +  '</tr>'
            })
            document.querySelector('.guestbooklist').innerHTML = html;
         }
    })
}


// 3. 방명록 카테고리 등록
function setgustcategory(){

let data = {
        gbcname : document.querySelector('.gbcname').value,
    }

    $.ajax({
         url        : "/board/setgustcategory",
         type       : "post",
         data       : JSON.stringify( data ), // 객체로 통신하기 위해
         contentType: "application/json", // 전송 타입 : application/json
         success    : re => {
            console.log(re);
            if( re == true ){
                alert("카테고리 등록 성공");
                location.href = "/board/guestbook"
            }else{
                alert("카테고리 등록 실패");
            }
         }
    })
}


// 4. 방명록 카테고리 출력
getgustcategorylist()
function getgustcategorylist(){
    $.ajax({
         url        : "/board/getgustcategorylist",
         type       : "get",
         success    : re => {
            console.log(re);
            let html = '<button onclick="bcnochage(0)"> 전체글보기 </button>' ;
            re.forEach( (b) => {
                html += '<button onclick="bcnochage('+b.gbcno+')">'+b.gbcname+'</button> '
            })
            document.querySelector('.gbcbox').innerHTML = html;
         }
    })
}

// 5. 카테고리 체인지
function bcnochage( cno ){
    gbcno = cno;
    alert(gbcno + " 번의 카테고리 선택");
    getguestbook(); // 전체 게시물 재출력
}