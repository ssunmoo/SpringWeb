import React, {useEffect, useState} from "react";
import axios from "axios";
import Pagination from 'react-js-pagination'

export default function BoardList(){

    // 1. 게시물 리스트 state
    const [ pageInfo, setPageInfo ] = useState({  bcno: 0, page : 1 , key : "", keyword : "" }); // 1. 검색 정보 객체 state
    const [ pageDto, setPageDto ] = useState( { list : []} ); // 게시물 리스트 state
    const [ bcategory, setBcategoryList ] = useState( [] ); // 카테고리 리스트 state

    // 2. 서버로부터 게시물 리스트를 가져오는 함수
    function getboardList(){
        axios
            .post("/board/boardlist", pageInfo)
            .then( res => {
                setPageDto( res.data )
                console.log( res )
            })
            .catch( err => { console.log(err)})
    }
    // 3. 렌더링 될때와 pageInfo가 변경될 때마다
    useEffect( getboardList, [pageInfo] );


    // 4. 모든 카테고리 가져오기
    function getBcategory(){
        axios
            .get("/board/bcategorylist")
            .then( res => {
                setBcategoryList(res.data);
                console.log(res);
            })
            .catch( err => { console.log(err)});
    }
    useEffect( getBcategory, [] ); // mount , unmount


    // ** 카테고리 버튼을 클릭했을 때
    const onCategory = ( bcno ) => {
        setPageInfo({
            bcno: bcno,
            page : 1 ,
            key : "",
            keyword : "" });
    }

    // ** 페이지 번호 클릭 시
    const onPage = ( page ) => {
        setPageInfo({
            bcno: pageInfo.bcno, // 기존카테고리
            page : page ,
            key : pageInfo.key, // 기존 검색 필드명
            keyword : pageInfo.keyword }); // 기존 검색할 단어
    }

    // ** 검색
    const onSearch = () => {
        setPageInfo({
            bcno: pageInfo.bcno, //   카테고리 유지
            page : 1 ,  // 검색 시 1페이지
            key : document.querySelector('.key').value, // 검색할 필드명
            keyword : document.querySelector('.keyword').value }); // 검색할 단어
    }

    // 게시글 선택 시 게시판 번호를 경로에 저장하여 페이지 이동
    function loadView( bno ) {
        window.location.href = '/board/view/'+bno
    }


    // 렌더링
    return(
        <div>
            <a href="/board/write">
                <button type="button">글쓰기[로그인했을때만]</button>
            </a>
            <h1> 글 목록 페이지 </h1>
            <div className="bcategorytbox">
                <button type="button" onClick={ () => onCategory(0) }> 전체보기 </button>
                {
                    bcategory.map( (c) => {
                        return(
                            <button type="button" onClick={ () => onCategory(c.bcno) }> { c.bcname }</button>
                        );
                    })
                }
            </div>

            <table className="btable">
                {
                    pageDto.list.map( (b) => {
                        return(
                          <tr>
                              <td> { b.bno }</td>
                              <td onClick={ () => loadView( b.bno ) }> { b.btitle }</td>
                              <td> { b.memail }</td>
                              <td> { b.bdate }</td>
                              <td> { b.bview }</td>
                          </tr>
                        );
                    })
                }
            </table>
                <Pagination
                    activePage={ pageInfo.page }
                    itemsCountPerPage={ 3 }
                    totalItemsCount={ pageDto.totalBoards }
                    pageRangeDisplayed={ 5 }
                    onChange={ onPage }
                />

                <div className="searchBox">
                    <select className="key">
                        <option value="btitle"> 제목 </option>
                        <option value="bcontent"> 내용 </option>
                    </select>
                    <input type="text" className="keyword" />
                    <button type="button" onClick={ onSearch }> 검색 </button>
                </div>
        </div>
    );


}