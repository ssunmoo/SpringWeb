import React, {useEffect, useState} from "react";
import axios from "axios";

// 리액트 텍스트 api
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import {useParams} from "react-router-dom";

let bcontent = '';
export default function BoardUpdate( props ){

    // 경로상 변수 가져오기
    const params = useParams();

    const [ board, setBoard ] = useState( {} ); // 게시판 메모리 : 객체여서 중괄호 초기 값
    useEffect(() =>
            axios
                .get("/board/getboard", { params : { bno : params.bno } } )
                .then( res => {
                    setBoard( res.data )
                })
                .catch( err => { console.log(err)})
        , []
    )

    // 1. 서버로부터 수정된 정보를 이용한 게시물 수정 요청
    const upboard = () => {
        // 수정할 게시물 번호, 내용들 [ 제목, 내용, 첨부파일 ]
        let boardform = document.querySelector('.boardform');
        let formdata = new FormData( boardform );
        formdata.set( "bno", board.bno );       // 폼데이터에 게시글 번호 추가 [ 수정할 식별자 필수 ]
        formdata.set( "bcontent", bcontent );   // 폼데이터에 내용 추가

        axios
            .put("/board/upboard", formdata, { headers : { 'Content-Type' : 'multipart/form-data' }} )
            .then( res => {
                if( res.data == true){
                    alert('게시글 수정 성공');
                }else {
                    alert('게시글 수정 실패');
                }
            })
            .catch( err => { console.log(err)})
    }


    return (
        <div>
            <h1> 글 수정 페이지 </h1>
            <form className="boardform">
                <input type="text" className="btitle" name="btitle" defaultValue={ board.btitle } /> <br />
                <CKEditor
                    editor={ ClassicEditor }
                    data={ board.bcontent }
                    onChange={ ( event, editor ) => {
                        const data = editor.getData();
                        bcontent = data;
                    } }
                />
                첨부파일 : <input type="file" className="bfile" name="bfile" /> <br />
                <button type="button" onClick={ upboard }> 등록</button>
            </form>
        </div>

    );

}


/*
    1. input type="text" vlaue="" : 벨류값 에 값 넣을 경우 ReadOnly : trye [ 읽기모드 기본 값 ] 자동설정
        [ 수정 풀기 방법 ]
        1. value={ board.btitle } readOnly = true
        2. defaultValue={ board.btitle }

 */