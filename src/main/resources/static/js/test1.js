
// GetMapping

// p.57
function GetMapping1(){
    $.ajax({
        url : "http://192.168.17.131:8081/api/v1/get-api/hello",
        success : function(re) {
            alert(re);
        }
    })
}

// p.58
function GetMapping2(){
    $.ajax({
        url : "http://192.168.17.131:8081/api/v1/get-api/name",
        success : re => {
            alert(re);
        }
    })
}

// p.59
function GetMapping3(){
    $.ajax({
        url : "http://192.168.17.131:8081/api/v1/get-api/variable1/aaa",
        success : re => {
            alert(re);
        }
    })
}

// p.60
function GetMapping4(){
    $.ajax({
        url : "http://192.168.17.131:8081/api/v1/get-api/variable2/aaa",
        success : re => {
            alert(re);
        }
    })
}

// test
function GetMapping5(){
    $.ajax({
        url : "http://192.168.17.131:8081/api/v1/get-api/variable3?variable=aa",
        success : re => {
            alert(re);
        }
    })
}

// p.61
function GetMapping6(){
    $.ajax({
        url : "http://192.168.17.131:8081/api/v1/get-api/requst1?name=dd&email=dd&organization=dd",
        success : re => {
            alert(re);
        }
    })
}

// p.62
function GetMapping7(){
    $.ajax({
        url : "http://192.168.17.131:8081/api/v1/get-api/requst2?key1=aa&key2=aa&key3=aa",
        success : re => {
            alert(re);
        }
    })
}

// p.66
function GetMapping8(){
    $.ajax({
        url : "http://192.168.17.131:8081/api/v1/get-api/requst3?name=aa&email=aa&organization=aa",
        success : re => {
            alert(re);
        }
    })
}


// PostMapping

// p.68
function PostMapping1(){
    $.ajax({
        url : "/api/v1/post-api/domain",
        type : "post",
        success : re => {
            alert(re);
        }
    })
}

// p.69-1
function PostMapping2(){

    let member = {
        name : "김아무개",
        email : "ggg@naver.com",
        organization : "aaaaa"
    }

    $.ajax({
        url : "/api/v1/post-api/member",
        type : "post",
        data : JSON.stringify( member ),
        contentType : "application/json", // 전송 타입 : application/json
        success : re => {
            alert(re);
        }
    })
}

// p.69-1
function PostMapping3(){

    let member = {
        name : "김아무개",
        email : "ggg@naver.com",
        organization : "aaaaa"
    }

    $.ajax({
        url : "/api/v1/post-api/member2",
        type : "post",
        data : JSON.stringify( member ),
        contentType : "application/json", // 전송 타입 : application/json
        success : re => {
            alert(re);
        }
    })
}



// PutMapping

// p.70
function PutMapping1(){
     let member = {
        name : "김아무개",
        email : "ggg@naver.com",
        organization : "aaaaa"
     }

     $.ajax({
         url : "/api/v1/put-api/member",
         type : "put",
         data : JSON.stringify( member ),
         contentType : "application/json", // 전송 타입 : application/json
         success : re => {
             alert(re);
         }
     })
}

// p.71
function PutMapping2(){
     let member = {
        name : "김아무개",
        email : "ggg@naver.com",
        organization : "aaaaa"
     }

     $.ajax({
         url : "/api/v1/put-api/member1",
         type : "put",
         data : JSON.stringify( member ),
         contentType : "application/json", // 전송 타입 : application/json
         success : re => {
             console.log(re);
         }
     })
}

// p.72
function PutMapping3(){
     let member = {
        name : "김아무개",
        email : "ggg@naver.com",
        organization : "aaaaa"
     }

     $.ajax({
         url : "/api/v1/put-api/member2",
         type : "put",
         data : JSON.stringify( member ),
         contentType : "application/json", // 전송 타입 : application/json
         success : re => {
            console.log(re);
            console.log(re.name);
            // let json = JSON.parse(re); 이미 JSON 객체로 받아서 파싱 필요 x [ @RequestBody 가 바꿔줌 ]

         }
     })
}


// p.75
function DeleteMapping1(){
    $.ajax({

         url : "/api/v1/delete-api/안녕하세요",
         type : "delete",
         success : re => {
            alert(re)

         }
    })
}

// p.76
function DeleteMapping2(){
    $.ajax({

         url : "/api/v1/delete-api/request1?variable=반가븝니다", // ?키=값
         type : "delete",
         success : re => {
            alert(re)

         }
    })
}

