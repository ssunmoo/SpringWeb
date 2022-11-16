
// 1. 회원 가입 버튼
function setmember(){

    let info = {
        memail : document.querySelector('.memail').value,
        mpw : document.querySelector('.mpw').value,
        mphone : document.querySelector('.mphone').value
    }

    // 이메일 인증을 완료했는지 확인하기
    let timerbox = document.querySelector('.timerbox').innerHTML;
    if( timerbox != "인증완료" ){
        alert("이메일 인증부터 완료해 주세요.");
        return;
    }

    $.ajax({    // 아작스의 기본 전송 타입은 [문자열]
        url : "/member/setmember",
        type : "POST",
        data : JSON.stringify(info),        // 문자열을 제이슨 언어로 변환해서 값 전달
        contentType : "application/json",   // 제이슨 타입으로 보내야 컨트롤러에서 @RequestBody 으로 받을 수 있음
        success : function(re){
            alert(re);
        }
    })
}

let auth = null;        // 발급된 인증 코드 변수
let timer = 0;          // 인증 시간 변수
let timerinter = null;  // setInterval 타이머 함수

// 2. 인증 코드 요청[전송]
function getauth(){

    // 1. 입력받은 이메일
    let toemail = document.querySelector('.memail').value;

    // 2. 입력받은 이메일에게 인증코드를 전송하고 전송된 인증코드를 반환
     $.ajax({    // 아작스의 기본 전송 타입은 [문자열]
        url : "/member/getauth",
        data : { "toemail" : toemail },
        type : "get",
        success : function(re){
            auth = re; // 응답 받은 인증코드를 전역 변수에 대입
            alert("해당 이메일로 인증코드가 발송되었습니다.");
            document.querySelector('.getauthbtn').innerHTML = "인증코드 재발급" // 버튼의 문자 변경
            timer = 120;    // 초 단위
            settimer();     // 타이머 함수 실행
        }
    })
}


// 4. 타이머 함수
function settimer(){
    timerinter = setInterval( function() {
        let minutes, seconds;
        minutes = parseInt(timer / 60); // 분
        seconds = parseInt(timer % 60); // 초

        // 삼항 연산자 조건 ? 참 : 거짓
        // 만약에 10보다 작을 경우 앞에 0 넣어줌 [두자리수 맞추기]
        minutes = minutes < 10 ? "0"+minutes : minutes;
        seconds = seconds < 10 ? "0"+seconds : seconds;

        let timehtml = minutes + " : " + seconds;   // 시 : 분 구성 html
        document.querySelector('.timerbox').innerHTML = timehtml; // 대입

        // 종료 조건
        timer--;    // 1초씩 차감
        if( timer < 0 ){    // 시간이 0초가 되면
            clearInterval( timerinter ); // 타이머 종료
            alert('인증실패');
            auth = null; // 발급 인증코드 초기화
            document.querySelector('.getauthbtn').innerHTML = "인증코드 발급" // 버튼의 문자 변경
        }
    }, 1000); // 1초 간격으로 함수 실행
} // settimer e


// 3. 인증 코드 확인
function authcode(){
    // 입력받은 인증코드 가져오기
    let authinput = document.querySelector('.authinput').value;

    // 입력 받은 코드와 발급된 코드가 동일하면
    if( authinput == auth ) {
        alert("인증이 완료되었습니다.")
        clearInterval( timerinter ); // setInterval 타이머 종료
        auth = null;
        timer = 0;
        document.querySelector('.timerbox').innerHTML = "인증완료"
    }else{
        alert("인증코드가 일치하지 않습니다.")
    }
} // authcode e