import React, {useEffect, useRef, useState} from "react";
import axios from "axios";

// 부트스트랩 import
import "bootstrap/dist/css/bootstrap.min.css"
import Button from 'react-bootstrap/Button';
import Offcanvas from 'react-bootstrap/Offcanvas';

export default function Home( props ){

    // 부트스트랩 사이브바 상태 변수
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const [ selectIndex, setSelectIndex ] = useState(0) // 마커 클릭 시 클릭된 room index 저장

    /* -----------------------[ 룸데이터 ]-------------------------- */

    const [ roomList, setRoomList ] = useState([{ getrimg : [] }]);

    useEffect( ()=>{
        axios
            .get("/room/getroomlist")
            .then( re => {
                setRoomList( re.data );
                console.log( re.data );
            })
            .catch( err => {
                console.log( err );
            })
    }, [])


    /* -----------------------[ 카카오지도 ]-------------------------- */

    const mapContainer = useRef(null); // 재렌더링시 초기화 x
    const { kakao } = window; // window 객체
    const mapOption = {
        center: new kakao.maps.LatLng( 36.2683, 127.6358 ), // 지도의 중심좌표
        level: 10 // 지도의 확대 레벨
    };

    // 4. 렌더링할때마다 map 생성
    useEffect( () => {
        // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
        var map = new kakao.maps.Map(mapContainer.current, mapOption);
        // 마커 클러스터러를 생성합니다
        var clusterer = new kakao.maps.MarkerClusterer({
            map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
            averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
            minLevel: 3 // 클러스터 할 최소 지도 레벨
        });

        // 마커 이미지의 주소
        var markerImageUrl = 'http://localhost:8080/static/media/roomicon.4d0c17c206ed03b1d823.png',
            markerImageSize = new kakao.maps.Size(40, 42), // 마커 이미지의 크기
            markerImageOptions = {
                offset : new kakao.maps.Point(20, 42)// 마커 좌표에 일치시킬 이미지 안의 좌표
            };
        // 마커 이미지를 생성한다
        var markerImage = new kakao.maps.MarkerImage(markerImageUrl, markerImageSize, markerImageOptions);

        // ** 데이터를 가져와서 마커 생성
        var markers = roomList.map( ( position, i ) => {
            // 가져온 데이터의 좌표들을 반본문 돌리면서 마커 생성
            // 생성된 마커들을 markers에 저장
            let marker = new kakao.maps.Marker({
                position : new kakao.maps.LatLng(position.rlat, position.rlng), // 마커의 좌표
                image : markerImage, // 마커의 이미지
                map: map // 마커를 표시할 지도 객체
            });
            kakao.maps.event.addListener(marker, 'click', function() {
                setSelectIndex( i ); // 클릭된 마커의 room index 저장
                setShow(true);
            });
            return marker;
        });
        // 클러스터러에 마커들을 추가합니다
        clusterer.addMarkers(markers);
        
    }, [roomList]); // 룸리스트 변경될때마다 리 렌더링

        /*// 마커 이미지의 주소
        var markerImageUrl = 'http://localhost:8080/static/media/roomicon.4d0c17c206ed03b1d823.png',
            markerImageSize = new kakao.maps.Size(40, 42), // 마커 이미지의 크기
            markerImageOptions = {
                offset : new kakao.maps.Point(20, 42)// 마커 좌표에 일치시킬 이미지 안의 좌표
            };

        // 마커 이미지를 생성한다
        var markerImage = new kakao.maps.MarkerImage(markerImageUrl, markerImageSize, markerImageOptions);

        // 지도에 마커를 생성하고 표시한다
        var marker = new kakao.maps.Marker({
            position: new kakao.maps.LatLng( 33.450701, 126.570667 ), // 마커의 좌표
            image : markerImage, // 마커의 이미지
            map: map // 마커를 표시할 지도 객체
        });*/
    // })





    return(

        <>
            {/* 부트스트랩 사이드 바 */}
            <Offcanvas show={show} onHide={handleClose}>
                <Offcanvas.Header closeButton>
                    <Offcanvas.Title>Offcanvas</Offcanvas.Title>
                </Offcanvas.Header>
                <Offcanvas.Body>
                    { selectIndex }
                    {
                        roomList[selectIndex].getrimg.map( ( img ) => {
                            //return <img src={"http://localhost:8080/bupload/"+img} />
                            return <img src={"http://localhost:8080/static/media/"+img} />
                        })
                    }
                </Offcanvas.Body>
            </Offcanvas>


            <div>
                <div id="map" ref={ mapContainer } style={{width:'100%',height:'600px'}} ></div>
            </div>

        </>
    )
}