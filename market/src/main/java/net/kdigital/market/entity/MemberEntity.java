package net.kdigital.market.entity;

import org.hibernate.annotations.Collate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kdigital.market.dto.MemberDTO;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder

@Entity
@Table(name = "market_member")
public class MemberEntity {

    @Id
    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_pw", nullable = false)
    private String memberPw;

    @Column(name = "member_name", nullable = false)
    private String memberName;
    
    @Column(nullable = false)
    private String phone;
    private boolean enabled;
    private String rolename;

    public static MemberEntity toEntity(MemberDTO memberDTO){
        return MemberEntity.builder()
            .memberId(memberDTO.getMemberId())
            .memberPw(memberDTO.getMemberPw())
            .memberName(memberDTO.getMemberName())
            .phone(memberDTO.getPhone())
            .enabled(memberDTO.isEnabled())
            .rolename(memberDTO.getRolename())
            .build();
    }
    
}
