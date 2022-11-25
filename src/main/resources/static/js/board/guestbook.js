
let gbcno = 0;
let gbno = 0;

// 1 .게시글 작성
function setguestbook(){

    let formbox = document.querySelector('.formbox');
    let formdata = new FormData( formbox );
    formdata.set( "gbcno", gbcno ); // 카테고리 번호 추가 저장
    $.ajax({
         url        : "/board/setguestbook",
         type       : "post",
         data       : formdata, // 객체로 통신하기 위해
         contentType: false,
         processData: false,
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
         data       : { "gbcno" : gbcno }, // 카테고리번호 넘기기
         success    : re => {
            console.log(re);
            let html = '<tr>'
                     + '<th> 번호 </th><th> 내용 </th><th> 작성자 </th><th> 첨부파일 </th>'
                     + '</tr>';

            re.forEach( (b) => {
                html += '<tr>'
                     +  '<td>'+b.gbno+'</td><td>'+b.gbcontent+'</td><td>'+b.gbname+'</td><td>'+b.gbfile+'</td>'
                     +  '<td> <button type="button" onclick="gbupdatebtn('+b.gbno+')" class="gbupdate"> 수정하기 </button> <button type="button" onclick="gbdelete('+b.gbno+')" class="gbdelete"> 삭제 </button> </td>'
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
            let html = '<button onclick="bcnochage(0)"> 전체글보기 </button> ';
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

// 업데이트 버튼 클릭
function gbupdatebtn( bno ){
    gbno = bno;
    console.log("gbno" + gbno )
    let html = '<h3>수정내용작성</h3>'
           + '내용 <input type="text" class="gbcontent2"> <br>'
           + '작성자 <input type="text" class="gbname2">'
           + '<button type="button" onclick="gbupdate()"> 수정완료 </button>'
    document.querySelector('.updatebox').innerHTML = html;
}

// 게시글 수정
function gbupdate(){
    let data = {
                gbcontent : document.querySelector('.gbcontent2').value,
                gbname : document.querySelector('.gbname2').value,
                gbno : gbno // 수정할 대상
            }
    console.log( data );
    $.ajax({
         url        : "/board/gbupdate",
         type       : "put",
         data       : JSON.stringify( data ),   // 객체로 통신하기 위해
         contentType: "application/json",       // 전송 타입 : application/json
         success    : re => {
            if( re == true ){
                alert("글 수정 성공");
                location.href = "/board/guestbook"
            }else{
                alert("글 수정 실패");
            }
         }
    })
}

// 게시글 삭제
function gbdelete( gbno ){
console.log( "삭제 gbno : " + gbno );
    $.ajax({
         url        : "/board/gbdelete",
         type       : "delete",
         data       : { "gbno" : gbno },
         success    : re => {
            if( re == true ){
                alert("글 삭제 성공");
                location.href = "/board/guestbook"
            }else{
                alert("글 삭제 실패");
            }
         }
    })

}
