package net.kdigital.spring7.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kdigital.spring7.dto.ReplyDTO;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString

@Entity
@Table(name = "reply")
public class ReplyEntity {
    @SequenceGenerator(
        name = "reply_seq"
        , sequenceName = "reply_seq"
        , initialValue = 1
        , allocationSize = 1
    )

    @Id
    @Column(name = "reply_num")
    @GeneratedValue(generator = "reply_seq")
    private Long replyNum;

    @Column(name = "reply_writer")
    private String replyWriter;

    @Column(name = "reply_text")
    private String replyText;
    
    @Column(name = "create_date")
    @CreationTimestamp
    private LocalDateTime createDate;

    /*
     * Board Table과 Reply Table은 1:다 관계
     * Reply가 일대다에서 다의 위치이고, 조인컬럼은 BoardEntity객체의 boardNum
     * 부모인 Board가 객체로 선언되어있어야하고, 관계 맺을 시 @ManyToOne으로 설정 필요
     */
    //Many-> 현재클래스(Reply)
    //FetchType ==> EAGER: 부모가 끌려오면 같이 끌려옴-미리데이터를 차지함, LAZY: 나중에 요청시 끌려옴(기본값)
    @ManyToOne(fetch = FetchType.LAZY)
    //조인컬럼명 설정, 멤버이름이 아닌 컬럼명으로 
    @JoinColumn(name = "board_num") 
    private BoardEntity boardEntity;

    // FK컬럼 값을 테이블 전체로 받음
    public static ReplyEntity toEntity(ReplyDTO replyDTO, BoardEntity boardEntity){
        return ReplyEntity.builder()
                    .replyNum(replyDTO.getReplyNum())
                    .replyWriter(replyDTO.getReplyWriter())
                    .replyText(replyDTO.getReplyText())
                    .createDate(replyDTO.getCreateDate())
                    .boardEntity(boardEntity)
                    .build();
    }
}
