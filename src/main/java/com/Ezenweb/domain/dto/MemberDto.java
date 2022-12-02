package com.Ezenweb.domain.dto;

import com.Ezenweb.domain.entity.member.MemberEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@Builder
public class MemberDto implements UserDetails, OAuth2User {

    private int mno;
    private String memail;
    private String mpw;
    private String mphone;      // 회원 전화번호 필드
    private Set<GrantedAuthority> authorities; // 인증 권한 [토큰]
    private Map<String, Object> attributes; // oauth2 인증결과





    // Dto --> Entity 로 변환
    public MemberEntity toEntity() {
        return MemberEntity.builder() // 빌더 메소드 시작
                .mno( this.mno )
                .memail( this.memail )
                .mpw( this.mpw )
                .mphone( this.mphone)
                .build(); // 빌더 메소드 끝
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    // UserDetails Override
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
            // 컬렉션의 상속을 받는 GrantedAuthority
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.mpw;
    }

    @Override
    public String getUsername() {
        return this.memail;
    }

    // 아래 애들 true로 안해주면 안돌아감..! p.387 참고
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    // OAuth2User Override
    @Override
    public String getName() {
        return this.memail;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }



}
