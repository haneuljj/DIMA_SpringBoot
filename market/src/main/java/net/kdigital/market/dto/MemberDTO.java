package net.kdigital.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kdigital.market.entity.MemberEntity;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class MemberDTO {
    private String memberId;
    private String memberPw;
    private String memberName;
    private String phone;
    private boolean enabled;
    private String rolename;

    public static MemberDTO toDTO(MemberEntity memberEntity) {
        return MemberDTO.builder()
                .memberId(memberEntity.getMemberId())
                .memberPw(memberEntity.getMemberPw())
                .memberName(memberEntity.getMemberName())
                .phone(memberEntity.getPhone())
                .enabled(memberEntity.isEnabled())
                .rolename(memberEntity.getRolename())
                .build();
    }
}
