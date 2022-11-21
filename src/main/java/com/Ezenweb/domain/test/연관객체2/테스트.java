package com.Ezenweb.domain.test.연관객체2;

public class 테스트 {
    public static void main(String[] args) {

        // 1. 제품 객체 [ PK ] 생성
        제품 제품1 = new 제품();
        제품1.제품명 = "나이키신발";

        // 2. 이미지 객체 [ FK ] 생성
        이미지 이미지1 = new 이미지();
        이미지1.이미지명 = "나이키신발1.jpg";

        이미지 이미지2 = new 이미지();
        이미지2.이미지명 = "나이키신발2.jpg";

        // 3. 이미지 객체[ FK ] --> 제품 객체 [ PK ] 대입  * 단방향 *
        이미지1.제품 = 제품1;
        이미지2.제품 = 제품1;

        // 4. 제품 객체 [ PK ] --> 이미지 객체 [ FK ] 넣기  * 양방향 *
        제품1.이미지list.add( 이미지1 );
        제품1.이미지list.add( 이미지2 );

        // 5. 확인
        System.out.println( 제품1.이미지list.toString() );



    }
}
