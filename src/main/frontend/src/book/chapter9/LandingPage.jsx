import React,{useState} from 'react';
import Toolbar from './Toolbar'

export default function LandingPage(props) {
    const [isLoginIn,setIsLoginIn] = useState(true);

    const onClickLogin = () => {
        setIsLoginIn(true);
    };

    const onClickLogout = () => {
        setIsLoginIn(false);
    };

    return (
        <div>
            <Toolbar
                isLoginIn = {isLoginIn}
                onClickLogin = {onClickLogin}
                onClickLogout = {onClickLogout}
            />
            <div style={{padding:16}}>소플과 함께하는 리액트 공부!</div>
        </div>
    );
}