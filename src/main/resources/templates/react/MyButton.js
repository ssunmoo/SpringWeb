
// 1. root 라는 id를 갖는 html 호출
const domContainer = document.querySelector('#root');
console.log( domContainer );

// 2. 리액트 랜더링 [ render() ]
ReactDOM.render( React.createElement( MyButton ), domContainer );

// 3. 함수
function MyButton( props ){ // properties 의 약자
    const [ isClicked, setIsClicked ] = React.useState( false ); // 초기 값 false

    return React.createElement( // React.createElement( <button> ) 가상 돔
        'button',                                  // 태그명
        { onClick:() => setIsClicked( true ) },     // 옵션 : 이벤트
        isClicked ? 'Clicked!' : 'Click here!'             // 실행 시 html 작성
        // 조건   ?       참   :   거짓
    )
}