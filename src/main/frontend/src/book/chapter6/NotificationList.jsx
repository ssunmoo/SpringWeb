import React from "react";
import Notification from "./Notification"; // Notification.jsx 가져오기
// [ ./ : 현재폴더 ], [ ../ :상위폴더 ]

// 1. 데이터
const reservedNotifications = [
    {
        id : 1,
        message : "안녕하세요"
    },
    {
        id : 2,
        message : "반갑습니다"
    },
    {
        id : 3,
        message : "날씨가 추버요"
    }
];

// 2. 타이머 변수 [ Interval 사용 예정 ]
var timer;

// 3. 클래스를 이용한 컴포넌트 생성
class NotificationList extends React.Component{
    // 1. 생성자
    constructor( props ) {
        super( props );
        this.state = {
            notifications : [] // 처음에는 비어있음
        };
    }

    // 2. 함수1 [ 컴포넌트 생성 ]
    componentDidMount() {
        const { notifications } = this.state; // 생명주기
        timer = setInterval( () => {
            if( notifications.length < reservedNotifications.length ){
                const index = notifications.length;
                notifications.push( reservedNotifications[index]);
                this.setState({
                    notifications: notifications // 상태를 저장 후 렌더
                });
            }else {
                this.setState( {notifications: [] }) // 상태 초기화
            }
        }, 1000) // 1초마다 실행
    }

    // ** 생명 주기 NotificationList 컴포넌트 종료 시 timer 클리어
    componentWillUnmount() {
        if( timer ){ // timer 변수에 setInterval 가 있으면
            clearInterval( timer ); // 종료
        }
    }

    // 3. 함수2
    render() {
        return(
            <div>
                { this.state.notifications.map( (n) => {
                    return < Notification id={n.id} message={n.message} />;
                }) }
            </div>
        );
    }

}

// 4. 컴포넌트 내보내기
export default NotificationList;


/*
    // 1. forEach
    리스트변수명.forEach( (반복변수명) => {
        실행문
    })

    // 2. map
    리스트변수명.map( (반복변수명) => {
        return 반환값
    });
 */

