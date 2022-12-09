// p. 324
import React, { useState } from "react";

export default function SignUp( props ){

    // 1. 컴포넌트 메모리
    const [ name , setName ] = useState('');
    const [ gender , setGender ] = useState('남자');
    // 2-1. 이벤트 함수
    const hanaleChanmgeName = (e) => {
        setName( e.target.value )
    }
    const hanaleChanmgeGender = (e) => {
        setGender( e.target.value )
    }

    // 2-2. 이벤트 함수
    const hanaleSubmit = (e) => {
        alert(`이름 : ${name}, 성별 : ${gender}`); // state 값 호출
        e.preventDefault();
    }   
    
    
    // 3. 렌더링
    return(
        <form onSubmit={ hanaleSubmit }>
            <label>
                이름
                <input type="text" value={name} onChange={hanaleChanmgeName} />
            </label>
            <br/>
            <label>
                성별
                <select value={gender} onChange={hanaleChanmgeGender}>
                    <option value="남자"> 남자 </option>
                    <option value="여자"> 여자 </option>
                </select>
            </label>
            <button type="submit"> 전송 </button>
        </form>
    );

}