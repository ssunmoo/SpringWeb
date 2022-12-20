package com.Ezenweb.config;


import com.Ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfigration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberService memberService;

    // 로그인 인증 관련 메소드 정의 [ 토큰 생성 ] 후 서비스에 전달
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService( memberService ).passwordEncoder(new BCryptPasswordEncoder());
        // memservice 에서 UserDetailsService 구현했기 때문에 가능
    }

    @Override // 재정의 : 상속 받은 클래스로부터 메소드 구현
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http); // 주석처리하면 인증 다 풀림
        http
                // 권한[role]에 따른 http 접근 제한
                .authorizeRequests() // 인증 요청 URL [ 로그인 ]
                    .antMatchers("/board/write").hasRole("MEMBER") // 해당 URL에는 MEMBER만 접근 가능
                    .antMatchers("/**").permitAll() // 그외 모든 Role 접근 가능
                    .antMatchers("/board/update").hasRole("MEMBER") // 해당 URL에는 MEMBER만 접근 가능
                    .antMatchers("/**").permitAll() // 그외 모든 Role 접근 가능
                    .antMatchers("/admin").hasRole("ADMIN") // 해당 URL에는 MEMBER만 접근 가능
                    .antMatchers("/**").permitAll() // 그외 모든 Role 접근 가능
                    .and()
                // 일반 로그인 보안 설정
                .formLogin()
                    .loginPage("/member/login") // 아이디와 비밀번호를 입력 받을 페이지 URL
                    .loginProcessingUrl("/member/getmember") // 로그인을 처리할 URL
                    .defaultSuccessUrl("/") // 로그인 성공 시 이동할 URL
                    .failureUrl("/member/login") // 로그인 실패 시 이동할 URL [다시 로그인 하도록 로그인 페이지]
                    .usernameParameter("memail") // 입력 받을 아이디 변수명
                    .passwordParameter("mpw")  // 입력 받을 비밀번호 변수명
                    .and()

                // 로그아웃 설정
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃 처리 URL
                    .logoutSuccessUrl("/") // 로그아웃 성공 시
                    .invalidateHttpSession( true ) // 세션 [ principal ] 초기화
                    .and()

                // 요청 위조 방지 설정 [ 해당 주소는 모두 들어갈 수 있게 ]
                .csrf() // 시큐리티는 post 타입만 막음 post 열어줘야 함
                    .ignoringAntMatchers("/member/getmember")   // 로그인
                    .ignoringAntMatchers("/member/setmember")   // 회원가입
                    .ignoringAntMatchers("/board/setbcategory") // 회원가입
                    .ignoringAntMatchers("/board/setboard")     // 게시글 작성
                    .ignoringAntMatchers("/board/boardlist")    // 게시글 보기
                    .ignoringAntMatchers("/board/delboard")     // 게시글 삭제
                    .ignoringAntMatchers("/board/upboard")      // 게시글 수정
                    .ignoringAntMatchers("/room/setroom")      // 게시글 수정
                    .and()

                // SNS 로그인 보안 설정
                .oauth2Login()
                    .defaultSuccessUrl("/") // 로그인 성공 시 이동할 URL
                    .userInfoEndpoint() // Endpoint[종착점] : 소셜 회원 정보가 들어오는 곳
                    .userService( memberService ); // 해당 서비스 loadUser 메소드 구현



    }


}
