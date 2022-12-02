
// 1. Props 사용법 p.149
function App( props ) {
    // 1. < 컴포넌트명 / >
    // 2. < 컴포넌트명 속석명 = "문자열" 속성명={숫자}>

    return(
        <Profile
            name="혜영"
            introduction="안녕하세요"
            viewCount={1500}
        />
    )
}


function App( props ){
    return(
        <Layout             // Layout 컴포넌트
            width={2560}    // Layout 컴포넌트에 width 속성 값 전달
            height={1400}   // Layout 컴포넌트에 height 속성 값 전달
            header={<Header title="소플의 블로그입니다" />} // Layout 컴포넌트에 헤더 컴포넌트 전달
            footer={<Footer/>}  // Layout 컴포넌트에 푸터 컴포넌트 전달
        />
    )
}