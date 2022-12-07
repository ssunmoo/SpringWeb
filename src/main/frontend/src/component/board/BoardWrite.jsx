import React from "react";

export default function BoardWrite( props ){
    const setbcategory = () => {
        alert('카테고리를 추가합니다.')
    }
    const setboard = () => {
        alert('게시물을 추가합니다.')
    }

    return (
        <div>
            <h1> 글 쓰기 페이지 </h1>
            <span> 카테고리 추가 </span>
            <span>
                <input type="text" className="bcname" />
                <button type="button" onClick={ setbcategory }> 등록 </button>
            </span>

            <div className="bcategorytbox">
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