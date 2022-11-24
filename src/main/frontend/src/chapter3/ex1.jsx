

const name = '소플'; // js 문자 변수
const element = <h1> 안녕, {name}</h1> // js+html => jsx


ReactDOM.render( element, document.querySelector("root"))

function formatName(user){
    return user.firstName + '' + user.lastName;
}

// 객체
const user = {
    firstName : 'Inje',
    lastName : 'Lee'
}

// 활용
function getGreeting( user ){
    if(user){
        return <h1> Hello, {formatName(user)}!</h1> // 유저가 있을 경우
    }
    return <h1> Hello, Stranger. </h1> // 유저가 없을 경우

}

// jsx식 html
const element = ( <div> <h1>안녕하세요</h1> <h2> 리액트 </h2> </div>)