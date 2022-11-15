package com.Ezenweb.service;

import com.Ezenweb.domain.dto.MemberDto;
import com.Ezenweb.domain.entity.MemberEntity;
import com.Ezenweb.domain.entity.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service    // 해당 클래스가 서비스임을 명시
public class MemberService {

    // -------------------------------- 전역 객체 -------------------------------------
    @Autowired
    private MemberRepository memberRepository;
    @Autowired // 스프링 컨테이너 [ 메모리] 위임
    private HttpServletRequest request; // 요청 객체 @Autowired 사용 시 new 생성자 안씀!!!

    // -------------------------------- 서비스 메소드 ----------------------------------

    // 1. 회원가입
    @Transactional
    public int setmember(MemberDto memberDto) {
        // 1. DAO 처리 [ dto --> entity
        MemberEntity entity = memberRepository.save(memberDto.toEntity());
        // memberRepository.save( 엔티티 객체 ) : 해당 엔티티 객체가 insert 생성된 엔티티객체 반환
        // 2. 결과 반환 [ 생성된 엔티티의 pk값 반환 ]
        return entity.getMno();
    } // setmember e

    // 2. 로그인
    @Transactional
    public int getmember(MemberDto memberDto) {
        // dao 처리 [ select ]

        // 1. 모든 엔티티 호출 [ select * from member 이랑 같음 ]
        List<MemberEntity> entityList = memberRepository.findAll();

        // 2. 입력 받은 데이터와 일치 값 찾기
        for (MemberEntity entity : entityList) { // entityList 을 돌려서 entity 변수에 담기
            if (entity.getMemail().equals(memberDto.getMemail())) { // 해당 레코드랑 입력받은 이메일이 같으면
                if (entity.getMpw().equals(memberDto.getMpw())) { // 비밀번호도 같으면
                    // [세션 부여] 로그인 성공 시 loginMno 라는 이름으로 세션 저장
                    request.getSession().setAttribute("loginMno" , entity.getMno() );
                                                        // 엔티티 = 레코드 = 로그인 성공한 객체의 회원번호
                    return 1; // 1: 로그인 성공
                } else {
                    return 2; // 2. 패스워드 틀림 [ 아이디 중복 구현안했지만 없다는 전제조건 ]
                }
            }
        }
        return 0; // 0 : 로그인 실패 [ 아이디 없음 ]
    } // getmember e

    // 3. 비밀번호 찾기
    @Transactional
    public String getpw(String memail) {
        // 1. 모든 레코드/엔티티 꺼내기
        List<MemberEntity> entityList = memberRepository.findAll();

        // 2. 리스트에서 찾기
        for ( MemberEntity entity : entityList ) {
            if ( entity.getMemail().equals(memail) ) { // 저장된 이메일과 입력한 이메일이 같으면
                return entity.getMpw();
            }
        } return null;
    } // getpw e

    // 4. 회원 탈퇴
    @Transactional
    public int setdelete( String mpw ){
        // * 로그인된 회원의 레코드/엔티티 꺼내기
        // 1. 세션 호출
        Object object =  request.getSession().getAttribute("loginMno");
        System.out.println("세션확인11::  " + object);
        // 2. 세션 확인
        if( object != null ){ // 만약에 세션이 null이 아니면 [ 로그인이 있으면 ]
            int mno = (Integer) object; // 형변환 [ object -> int ]
            System.out.println("세션확인22::  " + mno );

            // 3. 세션에 있는 회원번호 [pk]로 리포지토리에서 찾기
            Optional<MemberEntity> optional = memberRepository.findById(mno);
            optional.isPresent();
            if( optional.isPresent() ){
                // Optional 클래스 : null 관련 메소드
                // 4. Optional 객체에서 데이터[엔티티] 빼오기
                MemberEntity entity = optional.get();
                // 5. 탈퇴 [ delete : delete from member where mno = ? ]
                memberRepository.delete( entity );
                // 6. 세션 삭제 [ 로그아웃 ]
                request.getSession().setAttribute("loginMno", null );
                return 1;
            }
        }
        return 0; // 만약에 세션이 null 이면 [ 미로그인 or select 실패 시 ]
    } // setdelete e


    // 5. 비밀번호 수정
    @Transactional  // 데이터 수정 [ update ] 시 필수
    public int setupdate( String mpw  ){

        // 1. 세션호출
        Object object = request.getSession().getAttribute("loginMno");
        System.out.println(" 비번수정 세션 확인1 object ::  " + object);
        // 2. 세션 존재 여부 확인
        if( object != null ){
            int mno = (Integer)object;

           // 3. PK 값으로 엔티티[레코드] 검색
           Optional<MemberEntity> optional = memberRepository.findById( mno );
            System.out.println(" 비번수정 세션 확인2 mno ::  " + mno);

           // 4. 검색된 결과 여부 판단
           if( optional.isPresent() ){ // 엔티티가 존재하면
               MemberEntity entity = optional.get();
               
               // 5. 찾은 엔티티의 필드 값을 입력받은 값으로 변경 [ update member set 필드명 = 값 ]
               entity.setMpw( mpw );
               return 1;
           }
        }
        return 0;
    } // setupdate e


    // 6. 로그인할 경우 회원 버튼 출력
    public int getloginMno(){
        // 1. 세션 호출
        Object object = request.getSession().getAttribute("loginMno");
        System.out.println("로그인했니? : " + object);

        // 2. 세션 값 확인
        if( object != null ){
            int mno = (Integer)object;
            return mno;
        } return 0;
    } // btncreate e


    // 7. 로그아웃
    public int getlogoutMno(){
        Object object = request.getSession().getAttribute("loginMno");
        System.out.println("번호 잘받아왔뉴 : " + object );

        if( object != null ){
            System.out.println("111 : " + object );
            request.getSession().setAttribute("loginMno", null );
            return 1; // 성공 시 1
        }
        return 0;  // 실패 시 0
    }




} // 서비스 클래스 종료

