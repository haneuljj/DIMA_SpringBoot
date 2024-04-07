package net.kdigital.spring7.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kdigital.spring7.entity.ReplyEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class ReplyDTO {
    private Long replyNum;
    private String replyWriter;
    private String replyText;
    private Long boardNum;
    private LocalDateTime createDate;

    // Entity -> DTO
    public static ReplyDTO toDTO(ReplyEntity replyEntity, Long boardNum){
        return ReplyDTO.builder()
                    .replyNum(replyEntity.getReplyNum())
                    .replyWriter(replyEntity.getReplyWriter())
                    .replyText(replyEntity.getReplyText())
                    .boardNum(boardNum)
                    .createDate(replyEntity.getCreateDate())
                    .build();
    }
}
