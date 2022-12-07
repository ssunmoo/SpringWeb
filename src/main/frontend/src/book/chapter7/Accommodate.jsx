// p.235
import React, { useState, useEffect }  from "react";
import useCounter from "./UseCounter";

const MAX_CAPACITY = 10; // 전역변수, 최대수용인원

// 컴포넌트 선언
export default function Accommodate( props ){

    // 1. 커스텀 훅
    const [ count, increaseCount, decreaseCount ] = useCounter(0);

    // 2. 최대 인원 여부 확인 메모리 생성
    const [ isFull, setIsFull ] = useState( false );

    // 의존성 배열 없음 [ 생략 ]
    useEffect( () => {
        console.log("-----------------")
        console.log("이펙트 훅 실행")
        console.log("isFull " + isFull)

    })

    // 3. 만약에 현재 인원이 최대 수용 인원보다 크거나 같으면 true가 isFull 변수에 저장되고 아니면 false
        // 의존성 배열 존재 [ count ]
        // 실행조건 : 의존성 배열이 업데이트 될 때
    useEffect( ()=> {
        setIsFull( count >= MAX_CAPACITY ) }, [count] );

    // 4. 렌더링 구역
    return (
        <div style={{padding:16 }}>
            <p> 총 {count}명을 수용했습니다. </p>
            <button onClick={increaseCount} disabled={isFull}> 입장 </button>
            <button onClick={decreaseCount}> 퇴장 </button>
            { isFull && <p style={{color:"red"}}> 정원이 가득 찼습니다. </p> }
        </div>
    );
}