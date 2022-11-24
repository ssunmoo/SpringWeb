
// p.109
import React from "react";
import Book from "./Book";

function Libray(props){
    return(
        <div>
            <Book name="처음 만난 파이썬" numOfPage={300}/>
            <Book name="처음 만난 aws" numOfPage={400}/>
            <Book name="처음 만난 리액트" numOfPage={500}/>
        </div>
    );
}

export default Libray;