package net.kdigital.spring7.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.spring7.dto.UserDTO;
import net.kdigital.spring7.entity.UserEntity;
import net.kdigital.spring7.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 회원가입 처리
     * @param userDTO
     */
    public boolean joinProc(UserDTO userDTO) {
        
        boolean isExistUser = userRepository.existsById(userDTO.getUserId());

        // DB에 존재하는 아이디인 경우 회원가입 실패
        if(isExistUser) return false;

        // 비밀번호 암호화
        userDTO.setUserPwd(bCryptPasswordEncoder.encode(userDTO.getUserPwd()));

        // DTO --> Entity 후 저장
        userRepository.save(UserEntity.toEntity(userDTO));

        return true;
    }

    public boolean userIdCheck(String userId) {
        boolean isExistUser = userRepository.existsById(userId);

        return !isExistUser;
    }
    

}
