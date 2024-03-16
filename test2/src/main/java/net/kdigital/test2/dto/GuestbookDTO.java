package net.kdigital.test2.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kdigital.test2.entity.GuestbookEntity;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class GuestbookDTO {
    private Long guestSeq;
    private String name;
    private String pwd;
    private String content;
    private LocalDate regDate;

    // dto -> entity
    public static GuestbookEntity toEntity(GuestbookDTO guestbookDTO) {
        return GuestbookEntity.builder()
                .name(guestbookDTO.getName())
                .pwd(guestbookDTO.getPwd())
                .content(guestbookDTO.getContent())
                .build();
    }
}
