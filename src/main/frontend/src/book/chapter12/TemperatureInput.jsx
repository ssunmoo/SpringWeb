import React from "react";


const scaleNames = {
    c:"섭씨",
    f:"화씨"
}

export default function TemperatureInput( props ){

    const handleChange = ( e ) => {
        props.onTemperatureChange( e.target.value );
    }

    return(

        <fieldset>
            <legend>
                온도를 입력해주세요 ( 단위 : { scaleNames [ props.scale ]}
            </legend>
            <input type="text" value={ props.temperature } onChange={ handleChange } />
        </fieldset>

    );
}
