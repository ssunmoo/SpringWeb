package com.Ezenweb.service;

import com.Ezenweb.domain.dto.MemberDto;
import com.Ezenweb.domain.dto.OauthDto;
import com.Ezenweb.domain.entity.member.MemberEntity;
import com.Ezenweb.domain.entity.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Service    // 해당 클래스가 서비스임을 명시
public class MemberService implements UserDetailsService, OAuth2UserService<OAuth2UserRequest, OAuth2User > {

    // UserDetailsService : 일반회원
    // OAuth2UserService<OAuth2UserRequest, OAuth2User : SNS 회원

    // 로그인 성공한 소셜 회원 정보를 받는 메소드
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // userRequest : 인증 결과 요청 변수
        // 1. 인증[ 로그인 ] 결과 정보 요청
        System.out.println("1. userRequest " + userRequest.toString());

        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser( userRequest ); // oAuth2User.getAttributes() 임

        System.out.println("2. oAuth2User " + oAuth2User.toString());

        // 2. oauth2 클라이언트 식별 [ 카카오, 네이버, 구글 등 ]
        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId(); // 프로퍼티스에 입력한 클라이언트 아이디 가져오기
        System.out.println("3. oauth2 회사명 " + registrationId );

        // 3. 회원 정보를 담는 객체명 [ JSON 형태 ]
        String oauth2UserInfo = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        System.out.println("4. 회원정보 객체명 " + oauth2UserInfo );
        System.out.println("5. 인증 결과 " + oAuth2User.getAttributes() );
        // 4. Dto 처리
        OauthDto oauthDto = OauthDto.of( registrationId, oauth2UserInfo, oAuth2User.getAttributes() );

        // 5. DB처리 ***
        // 5-1. 기존 회원인지 아닌지 확인을 위해 엔티티에 이메일 검색
        Optional<MemberEntity> optional =  memberRepository.findByMemail(oauthDto.getMemail()); // Optional 클래스 [ nill 예외처리 방지 ]
        MemberEntity memberEntity = null;
        if( optional.isPresent()){ // 기존 회원이면
            memberEntity = optional.get();
//            // 이메일이 존재하면서 sns 구분[ 카카오, 네이버 등 ]이 동일할 경우
//            if( optional.get().getRole().equals( registrationId )){
//                memberEntity = optional.get(); // 검색된 내용을 memberEntity에 담기
//            }else {  // 이메일이 존재하면서 sns[ 카카오, 네이버 등 ] 구분이 다를 경우 새로 저장
//                memberEntity = memberRepository.save( oauthDto.toEntity() );
//            }
        }else { // 기존 회원이 아니면 새로 저장
            memberEntity = memberRepository.save( oauthDto.toEntity() );
        }
//        memberRepository.findByMemail( oauthDto.getMemail())
//                .orElseThrow( ()-> {});

        // 5-2. 권한 부여
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add( new SimpleGrantedAuthority(memberEntity.getRole())); // 권한이름

        // 6. 반환 [ 세션 역할 부분 : DB에 있는 정보를 꺼내서 세션에 저장 ]
        MemberDto memberDto = new MemberDto();
        memberDto.setMemail( memberEntity.getMemail());
        memberDto.setAuthorities( authorities ); // 권한 이름 전달 kakaoUser 등
        memberDto.setAttributes( oauthDto.getAttributes() );

        return memberDto; // dto에 담았으니 dto 리턴
    }

    // -------------------------------- 전역 객체 -------------------------------------
    @Autowired
    private MemberRepository memberRepository;
    @Autowired // 스프링 컨테이너 [ 메모리] 위임
    private HttpServletRequest request; // 요청 객체 @Autowired 사용 시 new 생성자 안씀!!!
    @Autowired
    private JavaMailSender javaMailSender; // 메일 전송 객체


    // -------------------------------- 서비스 메소드 ----------------------------------


    // ----------------------------[ 로그인된 엔티티 호출 ]----------------------------
    public MemberEntity getEntity() {
        // 1. 로그인 정보 확인
        // Object object = request.getSession().getAttribute("loginMno");
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 시큐리티 지원
        if (object == null) {
            return null;
        }
        // 2. 로그인된 회원번호 호출
        // int mno = (Integer) object;
        MemberDto memberDto = (MemberDto) object;

        // 3. 회원번호 --> 회원정보 호출
        // Optional<MemberEntity> optional = memberRepository.findById(mno);
        Optional<MemberEntity> optional = memberRepository.findByMemail( memberDto.getMemail() );
        if (!optional.isPresent()) {
            return null;
        }
        // 4. 로그인된 회원의 엔티티 반환
        return optional.get();
    }

    // ----------------------------------------------------------------------


    // 1. [일반] 회원가입
    @Transactional
    public int setmember(MemberDto memberDto) {
        // 패스워드 암호화 : 해시 함수를 사용하는 암호화 기법 중 하나 [ 비크립트 ]
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 입력받은 패스워드 암호화 해서 저장하는 코드
        memberDto.setMpw(passwordEncoder.encode( memberDto.getPassword()) );

        // 1. DAO 처리 [ dto --> entity
        MemberEntity entity = memberRepository.save(memberDto.toEntity());
        // memberRepository.save( 엔티티 객체 ) : 해당 엔티티 객체가 insert 생성된 엔티티객체 반환

        // 2. 회원 등급 넣어주기
        entity.setRole("ROLE_MEMBER");

        // 3. 결과 반환 [ 생성된 엔티티의 pk값 반환 ]
        return entity.getMno();
    } // setmember e

//    // 2. 로그인 [ 시큐리티 사용 시 필요 x ]
//    @Transactional
//    public int getmember(MemberDto memberDto) {
//        // dao 처리 [ select ]
//
//        // 1. 모든 엔티티 호출 [ select * from member 이랑 같음 ]
//        List<MemberEntity> entityList = memberRepository.findAll();
//
//        // 2. 입력 받은 데이터와 일치 값 찾기
//        for (MemberEntity entity : entityList) { // entityList 을 돌려서 entity 변수에 담기
//            if (entity.getMemail().equals(memberDto.getMemail())) { // 해당 레코드랑 입력받은 이메일이 같으면
//                if (entity.getMpw().equals(memberDto.getMpw())) { // 비밀번호도 같으면
//                    // [세션 부여] 로그인 성공 시 loginMno 라는 이름으로 세션 저장
//                    request.getSession().setAttribute("loginMno" , entity.getMno() );
//                                                        // 엔티티 = 레코드 = 로그인 성공한 객체의 회원번호
//                    return 1; // 1: 로그인 성공
//                } else {
//                    return 2; // 2. 패스워드 틀림 [ 아이디 중복 구현안했지만 없다는 전제조건 ]
//                }
//            }
//        }
//        return 0; // 0 : 로그인 실패 [ 아이디 없음 ]
//    } // getmember e

    // 2. 로그인 인증 메소드 [ 시큐리티 사용 시 ]
    @Override
    public UserDetails loadUserByUsername(String memail) throws UsernameNotFoundException {

        // 1. 입력받은 아이디 [memail]로 엔티티가 있는지 확인
        MemberEntity memberEntity = memberRepository
                        .findByMemail( memail )
                        .orElseThrow( ()-> new UsernameNotFoundException("사용자가 존재하지 않습니다."));
                        // .orElseThrow() : 검색 결과가 없으면 화살표 함수를 이용함
                        // new UsernameNotFoundException() : 유저 아이디가 없을 경우 출력

        // 2. 검증된 토큰 생성 [ 일반 유저 ]  Dto에 implements한 UserDetails이 토큰을 가지고 있음
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add( new SimpleGrantedAuthority(memberEntity.getRole()) ); // 토큰 정보에 엔티티에서 회원 등급 꺼내오기
        
        // 3. 엔티티 -> dto
        MemberDto memberDto = memberEntity.toDto(); // 엔티티 -> Dto 
        memberDto.setAuthorities( authorities );    // Dto에 토큰 추가
        return memberDto; // dto 반환 [ MemberDto는 UserDetails 의 구현체 ]
            // 구현체 : 해당 인터페이스의 추상메소드 [ 선언만 ]를 구현해준 클래스의 객체
    }

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
        // 2. 세션 확인
        if( object != null ){ // 만약에 세션이 null이 아니면 [ 로그인이 있으면 ]
            int mno = (Integer) object; // 형변환 [ object -> int ]

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


    // 5. 비밀번호 변경
    @Transactional  // 데이터 수정 [ update ] 시 필수
    public int setupdate( String mpw  ){

        // 1. 세션호출
        Object object = request.getSession().getAttribute("loginMno");
        // 2. 세션 존재 여부 확인
        if( object != null ){
            int mno = (Integer)object;

           // 3. PK 값으로 엔티티[레코드] 검색
           Optional<MemberEntity> optional = memberRepository.findById( mno );

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


//    // 6. 로그인할 경우 회원 버튼 출력 로그인 여부 판단 [ http 세션 ]
//    public int getloginMno(){
//        // 1. 세션 호출
//        Object object = request.getSession().getAttribute("loginMno");
//
//        // 2. 세션 값 확인
//        if( object != null ){
//            int mno = (Integer)object;
//            return mno;
//        } return 0;
//    }

    // 6. 로그인할 경우 회원 버튼 출력 시큐리티로 로그인 여부 판단 [ principal 세션 ]
    public String getloginMno(){
        // 1. 인증된 토큰 확인  [SecurityContextHolder 인증된 토큰 보관소 -> UserDetails(MemberDto) ]
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 2. 인증된 토큰 내용 확인
        Object principal = authentication.getPrincipal(); // Principal 접근 주체 [ UserDetails(MemberDto) ]
        System.out.println( "토큰내용확인 :: " + principal );
        // 3. 토큰 내용에 따른 제어
        if( principal.equals("anonymousUser")) { // 로그인 전 기본 값 : anonymousUser
            return null;

        }else { // 로그인 후
            MemberDto memberDto = (MemberDto) principal;
//            if( memberDto.getAuthorities().contains("일반회원") ){
//            }else if( memberDto.getAuthorities().contains("관리자") ){
//            }
            // return memberDto.getMemail()+"_"+memberDto.getAuthorities(); //+memberDto.getAuthorities();
            return memberDto.getMemail();
        }
    }


//    // 7. 로그아웃 [ http 세션 ]
//    public void logout(){
//        // 기본 세션명의 세션데이터를 null
//        request.getSession().setAttribute("loginMno" , null );
//    }

    // 8. 회원 목록
    public List<MemberDto> list(){
        // 1. JPA 이용한 모든 엔티티 호출
        List<MemberEntity> list = memberRepository.findAll();
        // 2. 엔티티 --> DTO
        // Dto list 선언
        List<MemberDto> dtoList = new ArrayList<>();
        for( MemberEntity entity : list ){
            dtoList.add( entity.toDto() ); // 형변환
        }
        return dtoList;
    }

    // 9. 메일 인증코드 발송
    public String getauth( String toemail ){
        String auth = ""; // 인증 코드
        String html = "<html><body> <h1> SpringWeb 회원 가입 이메일 인증코드 입니다.</h1>";

        Random random = new Random();   // 난수 객체
        for ( int i = 0; i < 6; i++ ){  // 6번 반복
            char randomchar = (char)(random.nextInt(26)+97); // 97부터 26개 : 97~122까지 : 알파벳 소문자
            // char randomchar = (char)(random.nextInt(10)+48); // 48부터 10개 : 48~57까지 : 0~9
            auth += randomchar;
        }
        html += "<div> 인증코드 : " + auth + "</div>";
        html += "</body></html>";

        emailsend( toemail, "인증코드입니다.", html ); // 메일 전송
        return auth; // 인증 코드를 반환
    } // getauth e
    
    // **. 메일 전송 서비스
    public void emailsend( String toemail, String title, String content ) {

        try{
        // 1. Mime 프로토콜 객체 생성
        MimeMessage message = javaMailSender.createMimeMessage();
        // 2. Mime 설정 객체 생성 new MimeMessageHelper( mime객체명, 첨부파일여부, 인코딩타입 )
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper( message, true, "UTF-8");
        // 3. 보내는 사람의 정보
        mimeMessageHelper.setFrom("14hhy@naver.com", "관리자");
        // 4. 받는 사람의 정보
        mimeMessageHelper.setTo( toemail );
        // 5. 메일 제목
        mimeMessageHelper.setSubject( title );
        // 6. 메일의 내용
        mimeMessageHelper.setText( content.toString(), true ); // HTML 형식
        // 7. 메일 전송
        javaMailSender.send( message );

        } catch ( Exception e ) {
            System.out.println("메일 전송 실패 :: " + e);
        }

    } // emailsend e



} // 서비스 클래스 종료

