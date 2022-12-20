import React, {useEffect, useRef, useState} from "react";
import {useDaumPostcodePopup} from 'react-daum-postcode'; // npm i react-daum-postcode
import axios from "axios";
import icon from '../../img/roomicon.png' // 아이콘 이미지 불러오기
// 스프링 통합 배포시 : resources -> static -> static -> media -> 이미지 복사 됨


export default function RoomWrite(props){

    /* -----------------------[ 카카오주소API ]-------------------------- */
    // 0.검색된 주소와 위도를 저장하는 state
    const [ address , setAddress ] = useState({ name:'', lat:'', lng:'' });
    // 1. 다음 주소 API 사용하기 위한 API 스트립트
    const open  = useDaumPostcodePopup("//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js");
    // 2. 주소 검색 버튼을 클릭했을때 이벤트
    const handleClick =  () => { open({ onComplete: handleComplete });  };
    // 3. 다음 주소 검색 결과 이벤트
    const handleComplete = (data) => {
        // 4. 주소 -> 좌표변환
        axios
                // 1. 카카오 주소 정보 검색  REST API [ 쿼리스트링을 이용한 주소 전달]
            .get("https://dapi.kakao.com/v2/local/search/address.json?query="+data.address,
                // 2. 요청 인증 키 [ RESTAPI KEY]
            { headers : { Authorization : 'KakaoAK 7dc37d88ec2a5f582f8261fb80980d3c' } } )
                // 3. 요청 결과 [ 카카오 문서 참고 ]
            .then( res => {
                // 4. JSON 결과에서 좌표 추출
                const location = res.data.documents[0];
                console.log( location );
                console.log( location.x );
                console.log( location.y );

                // 5. state 업데이트 [ mapOption 에 찾은 좌표 넣어주기 ]
                setAddress( { name:data.address, lat:location.y, lng:location.x })
            })
    };

    /* -----------------------[ 카카오지도 ]-------------------------- */

    const mapContainer = useRef(null); // 재렌더링시 초기화 x
    const { kakao } = window; // window 객체

    const mapOption = {
            center: new kakao.maps.LatLng( address.lat, address.lng ), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
    };

    // 4. 렌더링할때마다 map 생성
    useEffect( () => {
        // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
        var map = new kakao.maps.Map(mapContainer.current, mapOption);

        // 마커 이미지의 주소
        var markerImageUrl = 'http://localhost:8080/static/media/roomicon.4d0c17c206ed03b1d823.png',
            markerImageSize = new kakao.maps.Size(40, 42), // 마커 이미지의 크기
            markerImageOptions = {
                offset : new kakao.maps.Point(20, 42)// 마커 좌표에 일치시킬 이미지 안의 좌표
            };

        // 마커 이미지를 생성한다
        var markerImage = new kakao.maps.MarkerImage(markerImageUrl, markerImageSize, markerImageOptions);

        // 지도에 마커를 생성하고 표시한다
        var marker = new kakao.maps.Marker({
            position: new kakao.maps.LatLng( address.lat, address.lng ), // 마커의 좌표
            image : markerImage, // 마커의 이미지
            map: map // 마커를 표시할 지도 객체
        });
    })

    /* ----------------------------------------------------- */

    // 4. 방등록 버튼 눌렀을 때 이벤트
    const onWrite = () => {
        // 1. 방이름, 가격, 거래방식, 이미지[여러개], 주소, 위도, 경도 전송
        let roomform = document.querySelector('.roomform')
        let formdata = new FormData(roomform)

        formdata.set( "rname", address.name )
        formdata.set( "rlat", address.lat )
        formdata.set( "rlng", address.lng )

        axios
            .post("/room/setroom", formdata, {
                headers : { 'Content-Type' : 'multipart/form-data'}
            })
            .then( re => {
                if( re.data == true ){
                    alert('방 등록 성공');
                    window.location.href="/";
                }else {
                    alert('방 등록 실패');
                }
            })
    }


    return(
        <>
            <h3> 방등록 </h3>
            <form className="roomform">
                방이름  <input type="text" name="rtitle" />
                가격  <input type="text" name="rprice" />
                거래방식
                <select name="rtrans">
                    <option value="매매"> 매매 </option>
                    <option value="전세"> 전세 </option>
                    <option value="월세"> 월세 </option>
                </select>
                이미지  <input type="file" multiple="multiple" name="rimg" /> <br />
                위치[좌표] : 카카오주소 api
                <div> { address.name }</div>
                <button type='button' onClick={handleClick}>
                    위치찾기
                </button>

                <div id="map" ref={ mapContainer } style={{width:'100%',height:'350px'}} ></div>

                <button type="button" onClick={ onWrite }> 등록 </button>
            </form>

        </>
    );
}