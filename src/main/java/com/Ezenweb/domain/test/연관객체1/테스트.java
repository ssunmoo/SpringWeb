package com.Ezenweb.domain.test.연관객체1;

public class 테스트 {
    public static void main(String[] args) {

        // 1. 학급 객체 생성
        학급 학급1 = new 학급();
        학급1.학급명 = "초등학생";

        // 2. 학생 객체 생성
        학생 학생1 = new 학생();
        학생1.학생명 = "김아무개";

        // 3. 단방향 설정
        학생1.학급 = 학급1;
        
        // 4. 양방향
        학급1.학생리스트.add( 학생1 );

        // 5. 양방향 시 JOIN 한 것과 같은 기능
            // 1. 학급에서 학생을 조회 가능
        System.out.println(학급1.학생리스트.get(0).학생명);
            // 2. 학생이 학급을 조회 가능
        System.out.println(학생1.학급.학급명);

    }
}
