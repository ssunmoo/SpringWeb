

let bno = sessionStorage.getItem( "bno" );
alert( bno );

// 1. 선택한 게시물 출력
function getboard(){
    $.ajax({
        url : "/board/getboard",
        type : "get",
        data : { "bno" : bno },
        success : function( re ){
            console.log(re);

//            let html = '<tr>'
//                     + '<th> 번호 </th> <th> 제목 </th> <th> 작성자 </th> <th> 조회수 </th>'
//                     + '</tr>';
//
//            re.forEach( (b)=> {
//                html += '<tr>'
//                     + '<td>'+ b.bno +'</td><td>'+ b.btitle +'</td><td>'+ b.mno +'</td><td>'+ b.bview +'</td>'
//                     + '</tr>'
//            })
//            document.querySelector('.board_view').innerHTML = html;
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

