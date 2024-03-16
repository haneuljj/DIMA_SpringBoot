package net.kdigital.test2.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kdigital.test2.dto.GuestbookDTO;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString

@Entity
@Table(name = "guestbook")
public class GuestbookEntity {

    @SequenceGenerator(name = "guest_seq", sequenceName = "guestbook_seq", initialValue = 1, allocationSize = 1)
    @Id
    @Column(name = "guest_seq")
    @GeneratedValue(generator = "guest_seq")
    private Long guestSeq;

    @Column(name = "guest_name", nullable = false)
    private String name;

    @Column(name = "guest_pwd", nullable = false)
    private String pwd;

    @Column(name = "guest_text")
    private String content;

    @CreationTimestamp
    @Column(name = "regdate")
    private LocalDate regDate = LocalDate.now();

    public static GuestbookDTO toDTO(GuestbookEntity guest) {
        return GuestbookDTO.builder()
                .guestSeq(guest.getGuestSeq())
                .name(guest.getName())
                .pwd(guest.getPwd())
                .content(guest.getContent())
                .regDate(guest.getRegDate())
                .build();
    }

}
