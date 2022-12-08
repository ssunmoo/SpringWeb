// p.255
import React from "react";

class ConfirmButton extends React.Component{

    constructor( props ){
        super( props );
        this.state = {} // 리액트 컴포넌트의 데이터 변수 [ 1. set~~ 컴포넌트 업데이트 가능 ]
        this.handleConfirm = this.handleConfirm.bind( this );

    }

    // 3. 이벤트 함수 정의
    handleConfirm(){
        this.setState( (prevState ) =>
            ({ isConfirmed : !prevState.isConfirmed })
        )
    }

    // 2. 랜더링 함수
    render(){
        return(
            <button onClick={ this.handleConfirm}
                disabled={ this.state.isConfirmed}>
                { this.state.isConfirmed ? "확인됨" : "확인하기"}
            </button>
        );
    }
}


export default ConfirmButton;