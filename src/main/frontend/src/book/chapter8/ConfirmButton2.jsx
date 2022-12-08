// p.255
import React, { useState } from "react";

function ConfirmButton2 ( props ){

    // 1. useState 훅을 이용한 state 사용
    const [ isConfirmed, setIsConfirmed ] = useState( false );
    // 2. 함수명 변수
    const handleConfirm = () => {
        setIsConfirmed( ( preIsConfiremd ) => !preIsConfiremd ) ;
    }


    const handleConfirm2 = () => {
        // 1.
        // if( isConfirmed) {
        //     setIsConfirmed((preIsConfirmed) => !preIsConfirmed )
        // } else {
        //     setIsConfirmed( (preIsConfirmed) => !preIsConfirmed )
        // }

        // 2.
        setIsConfirmed( ( preIsConfiremd ) => !preIsConfiremd ) ;
    }

    return(
        <div>
            <button onClick={ handleConfirm } disabled={ isConfirmed }>
                { isConfirmed ? "확인됨" : "확인하기" }
            </button>

            <button onClick={ handleConfirm2 }>
                버튼
            </button>
            { isConfirmed && <input type="text" /> }
        </div>
    );
}

export default ConfirmButton2;