package net.kdigital.spring7.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.ToString;

@ToString
public class LoginUserDetails implements UserDetails{
    private static final long serialVersionUID = 1L;
    private String userId;
    private String userName;
    private String userPwd;
    private String email;
    private String roles;
    private boolean enabled;

    // 생성자
    public LoginUserDetails(UserDTO userDTO){
        super();
        this.userId = userDTO.getUserId();
        this.userName = userDTO.getUserName();
        this.userPwd = userDTO.getUserPwd();
        this.email = userDTO.getEmail();
        this.roles = userDTO.getRoles();
        this.enabled = userDTO.isEnabled();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 자격에 관한 값 
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            private static final long serialVersionUID = 1L;
            
            @Override
            public String getAuthority() {
                return roles; // role을 반환
            }
        });
        return null;
    }

    @Override
    public String getPassword() {
        return this.userPwd;
    }

    // ID반환(Override된 것, Security가 설정한 값이므로 변경x)
    @Override
    public String getUsername() {
        return this.userId;
    }
    // 이름 반환을 원한다면 직접 사용자 지정
    public String getUserName() {
        return this.userName;
    }

    // 원래 DB에서 읽어와야하는 부분
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
    
    
}
