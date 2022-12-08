// p. 249
// 1. 컴포넌트 첫글자는 대문자로
// 2. 클래스 컴포넌트 / 함수 컴포넌트
// 3. this.필드명 : 현재 클래스에 필드명


import React from "react";

class Ex1_Event extends React.Component{

    // 1. 생성자
    constructor( props ) {
        super( props );
        this.state = { isToggleOn : true }; // 메모리 관리
        this.handleClick = this.handleClick.bind( this )
    }

    // // 2. 이벤트 함수
    // handleClick() {
    //     this.setState( prevState => ({
    //         isToggleOn : !prevState.isToggleOn
    //     }));
    // }

// 2. 이벤트 함수
    handleClick = () => {
        this.setState( prevState => ({
            isToggleOn : !prevState.isToggleOn
        }));
    }


    // 3. 렌더링 함수
    render() {

        return(
            <button onClick={this.handleClick}>
                { this.state.isToggleOn ? '켜짐' : '꺼짐' }
            </button>
        );
    }

}

export default Ex1_Event