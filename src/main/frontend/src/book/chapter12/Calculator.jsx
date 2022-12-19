import React,{useState} from "react";
import TemperatureInput from "./TemperatureInput";


// 1. 매개변수 [props]의 온도가 100 이상이면 렌더링 아니면 렌더링
function BoilingVerdict( props ){
    if( props.celsius >= 100 ){
        return <p> 물이 끓습니다. </p>
    }
    return <p> 물이 끓지 않습니다.</p>
}

// 화씨 -> 섭씨 변환하는 함수
function toCelsius( fahrenheit ){
    return(( fahrenheit - 32 ) * 5 / 9 );
}

// 섭씨 -> 화씨 변환하는 함수
function toFahrenheit( celsius ){
    return( celsius * 9 ) /5 + 32;
}

function tryConvert( temperature, convert ){
    const input = parseFloat( temperature ); // 매개변수 temperature 의 실수형 형변환
    if( Number.isNaN(input)){
        return "";
    }

    const output = convert(input); // 매개변수를 convert 함수에 대입
    const rounded = Math.round(output * 1000) / 1000; // 반올림
    return rounded.toString(); // 결과를 문자열로 반환
}

export default function Calculator( props ){

    const [ temperature , setTemperature ] = useState(""); // 온도 메모리
    const [ scale, setScale ] = useState("c");

    // 온도 업데이트 함수
    const handleCelsiusChange = (t) => {
        setTemperature(t); setScale("c");
    }
    const handleFahrenheitChange = (t) => {
        setTemperature(t); setScale("f");
    }

    // 온도 표시 함수
    const fahrenheit = scale == "c" ? tryConvert( temperature , toCelsius()) : temperature;
    const celsius = scale == "f" ? tryConvert( temperature , toFahrenheit()) : temperature;


    return(
        <div>
            <TemperatureInput
                scale = "c"
                temperature = { celsius }
                onTemperatureChange = { handleCelsiusChange }
            />
            <TemperatureInput
                scale = "f"
                temperature = { fahrenheit }
                onTemperatureChange = { handleFahrenheitChange }
            />
            <BoilingVerdict celsius = { parseFloat(celsius) } />
        </div>

    );
}