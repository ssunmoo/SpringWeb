import React, {useEffect, useState} from "react";
import axios from "axios";

let bcno = 0; // 선택한 카테고리 번호
export default function BoardWrite( props ){

    const [ category, setCategory] = useState(''); // 입력받은 카테고리 명
    const [ categoryList, setCategoryList] = useState([]); // 서버로부터 가져온 카테고리 리스트
    
    // 카테고리 등록 [ 등록 버튼 누를 경우 ]
    const setbcategory = () => {
        if( category == '' ){ // 입력받은 카테고리 명이 없으면
            alert('카테고리를 입력해주세요');
            return;
        }

        axios
            .post( "/board/setbcategory", { bcname : category } ) // 키[변수명] : 값
            .then( res => {
                if( res.data == true ){
                    alert('카테고리 등록 성공');
                }else {
                    alert('카테고리 등록 실패');
                }
            })
            .catch( err => { console.log( err ); })
    }

    // 카테고리 출력 [ 페이지 렌더링 시 ]
    const getbcategory = () =>{
        axios
            .get("/board/bcategorylist")
            .then( res => {
                setCategoryList(res.data);
                console.log(res);
            })
            .catch( err => { console.log(err)});
    }
    useEffect( getbcategory, [])  // 훅 : 페이지가 생성되거나[mount], 삭제됐을때[unmount]

    // 3. 입력받은 게시물 등록 함수 [ 실행조건 : 글쓰기 등록 버튼 클릭 시 ]
    const setboard = () => {
        if( bcno == 0 ){
            alert('카테고리를 선택해주세요');
            return;
        }
        let boardform = document.querySelector('.boardform');
        let formdata = new FormData( boardform );
        formdata.set( "bcno", bcno );   // 폼데이터에 카테고리 번호 추가

        axios
            .post("/board/setboard", formdata, { headers : { 'Content-Type' : 'multipart/form-data' }} )
            .then( res => {
                if( res.data == true){
                    alert('게시글 작성 성공');    
                }else {
                    alert('게시글 작성 실패');
                }
            })
            .catch( err => { console.log(err)} );
    }




    return (
        <div>
            <h1> 글 쓰기 페이지 </h1>
            <span> 카테고리 추가 </span>
            <span>
                <input type="text" className="bcname" value={category} onChange={ (e) => {
                    setCategory( e.target.value );
                }} />
                <button type="button" onClick={ setbcategory }> 등록 </button>
            </span>

            <div className="bcategorytbox">
                {
                    categoryList.map( (c) => {
                            return (
                            <button key = { c.bcno } type="button" onClick={ () => { bcno = c.bcno; alert(bcno) }}>
                                {c.bcname}
                            </button>
                        );
                    })
                }
            </div>
            <br />
            <form className="boardform">
                제목 : <input type="text" className="btitle" name="btitle" /> <br />
                내용 : <input type="text" className="bcontent" name="bcontent" /> <br />
                첨부파일 : <input type="file" className="bfile" name="bfile" /> <br />
                <button type="button" onClick={ setboard }> 등록</button>
            </form>
        </div>

    );

}