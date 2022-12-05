import React from "react";

export default function BoardList(){
    return(
        <div>
            <a href="/board/write">
                <button type="button">글쓰기[로그인했을때만]</button>
            </a>
            <h1> 글 목록 페이지 </h1>
            <div className="bcategorytbox"></div>
            <table className="btable">
            </table>

        </div>
    );
}