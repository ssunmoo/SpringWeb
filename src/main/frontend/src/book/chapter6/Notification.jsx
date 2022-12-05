// p. 186
import React from "react";
import Styles from './Notification.css';

// 클래스를 이용한 컴포넌트 생성 [ React.Component 에게 상속받기 ]
class Notification extends React.Component{

    // 1. 생성자
    constructor(props) {
        super(props);    // 슈퍼 클래스 생성자 호출
        this.state = {  // 상태관리 변수 [ 비어있음 ]

        }; 
    }

    // * 생명주기 함수 [ 클래스 컴포넌트 생명주기 함수 사용 o / 함수 컴포넌트 생명주기 함수는 거의 사용 x ]
    componentDidMount() {
        console.log( `${this.props.id} 출생 함수 호출` );
    }
    componentDidUpdate() {
        console.log( `${this.props.id} 업데이트 함수 호출` );
    }
    componentWillUnmount() {
        console.log( `${this.props.id} 사망 함수 호출` );
    }

    // 2. 렌더링 함수
    render() {
        return(
            <div className="wrapper">
                <span className="messageText">
                    { this.props.message }
                </span>
            </div>
        );
    }

}

// 3. 컴포넌트 내보내기
export default Notification;