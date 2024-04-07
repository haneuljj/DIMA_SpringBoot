package net.kdigital.market.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.kdigital.market.dto.MemberDTO;
import net.kdigital.market.entity.MemberEntity;
import net.kdigital.market.repository.MemberRepository;;

@Service
@RequiredArgsConstructor
public class MemberService {
    
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bEncoder;

    public boolean joinProc(MemberDTO memberDTO) {

        // DB에 이미 존재하는 회원정보인지 확인
        boolean isExistMember = memberRepository.existsById(memberDTO.getMemberId());
        
        // 이미 존재하면 DB에 저장하지 않고 반환
        if(isExistMember) return false;

        // DB 저장하기 전에 비밀번호 암호화하기
        memberDTO.setMemberPw(bEncoder.encode(memberDTO.getMemberPw()));

        // DB 저장
        memberRepository.save(MemberEntity.toEntity(memberDTO));

        return true;
    }



}
