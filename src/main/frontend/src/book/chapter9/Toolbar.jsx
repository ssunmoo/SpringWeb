import React from 'react';

export default function Toolbar(props) {
    const {isLoginIn,onClickLogin,onClickLogout} = props;

    return(
        <div className="wrapper">
            {isLoginIn && <span className="greeting">환영합니다!</span>}
            {
                isLoginIn ?

                    (<button onClick={onClickLogout}>로그아웃</button>)
                    :
                    (<button onClick={onClickLogin}>로그인</button>)
            }
        </div>
    );
}