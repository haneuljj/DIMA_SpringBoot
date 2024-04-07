package net.kdigital.spring7.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.spring7.dto.LoginUserDetails;
import net.kdigital.spring7.dto.UserDTO;
import net.kdigital.spring7.entity.UserEntity;
import net.kdigital.spring7.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginUserDetailsService implements UserDetailsService{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        //userId 검증 로직, 테이블에 접근해서 데이터 가져옴
        // 사용자가 로그인 하면 SecurityConfig가 username을 여기로 전달
        log.info("UserId: {}", userId);

        // 따로 optional로 받을 필요없이 orElseThrow()로 객체 못찾을 경우 에러 발생시킴
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("error 발생");
                }); 
        
        UserDTO userDTO = UserDTO.toDTO(userEntity);

        // UserDetails로 반환해야하므로 UserDTO를 UserDetails로 바꿔야함 !
        return new LoginUserDetails(userDTO);


        // if(userEntity.isPresent()) {
        //     UserEntity entity = userEntity.get();
        //     UserDTO userDTO = UserDTO.toDTO(entity);

        //     // UserDetails로 반환해야하므로 UserDTO를 UserDetails로 바꿔야함 !
        //     return new LoginUserDetails(userDTO);
        // }
        // // 저장된 유저 정보가 없을 때
        // return null;
    }
    
}
