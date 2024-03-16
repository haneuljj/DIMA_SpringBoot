package net.kdigital.test2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.kdigital.test2.dto.GuestbookDTO;
import net.kdigital.test2.entity.GuestbookEntity;
import net.kdigital.test2.repository.GuestbookRepository;

@Service
@Slf4j
public class GuestbookService {
    private GuestbookRepository guestbookRepository;

    public GuestbookService(GuestbookRepository guestbookRepository) {
        this.guestbookRepository = guestbookRepository;
    }

    public void insertGuestbook(GuestbookDTO guestbookDTO) {
        // dto -> entity, DB에는 엔터티로 저장해야함
        GuestbookEntity entity = GuestbookDTO.toEntity(guestbookDTO);

        // repository로 넘겨서 DB에 저장
        guestbookRepository.save(entity);

    }

    public List<GuestbookDTO> selectAll() {
        List<GuestbookEntity> entityList = guestbookRepository.findAll();
        List<GuestbookDTO> list = new ArrayList<>();
        entityList.forEach((guest) -> list.add(GuestbookEntity.toDTO(guest)));

        return list;
    }

    public void deleteOne(Long guestSeq) {
        guestbookRepository.deleteById(guestSeq);

        // public void deleteOne(Long Seq, String userpwd) {
        // Optional<GuestbookEntity> opEntity = guestbookRepository.findById(Seq);
        // GuestbookEntity entity = opEntity.get();
        // log.info("{}", entity.toString());

        // if (entity.getPwd() == userpwd) {
        // guestbookRepository.deleteById(Seq);
        // log.info("삭제됨");
        // }
        // }
    }

    @SuppressWarnings("static-access")
    public GuestbookDTO selectOne(Long guestSeq) {
        Optional<GuestbookEntity> entity = guestbookRepository.findById(guestSeq);

        if (entity.isPresent()) {
            GuestbookEntity guestEntity = entity.get();
            return guestEntity.toDTO(guestEntity);
        }

        return null;
    }

    public void updateProc(GuestbookDTO guestbookDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProc'");
    }

}
