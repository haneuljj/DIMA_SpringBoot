package net.kdigital.spring6.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.kdigital.spring6.dto.FriendDTO;
import net.kdigital.spring6.entity.FriendEntity;
import net.kdigital.spring6.repository.FriendRepository;

@Service // 서비스 객체가 생성해서 소멸할때까지 라이프사이클을 스프링이 관리해줌
@Slf4j
public class FriendService {

    private FriendRepository friendRepository;

    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    /**
     * DB에 데이터 저장
     * 
     * @param friendDTO
     */

    public void insertFriend(FriendDTO friendDTO) {
        log.info("Service에 도착");

        // 1) DTO -> Entity로 변환
        FriendEntity entity = FriendDTO.toEntity(friendDTO);

        // Entity -> DTO로 변환
        // FriendDTO dto = FriendEntity.toDTO(entity);

        // 2) Repository로 넘겨서 저장
        friendRepository.save(entity);

    }

    /**
     * 데이터 전체 조회
     * 
     * @return List<FriendDTO>
     */

    public List<FriendDTO> selectAll() {
        // List<FriendEntity> list = friendRepository.findAll(); -> 정렬되지 않는 방법

        // 이름 순으로 정렬하기: order by문으로 정렬되는것
        List<FriendEntity> list = friendRepository.findAll(Sort.by(Sort.Direction.ASC, "fname")); // Sort.Direiction.오름/내림차순,정렬기준
        List<FriendDTO> friendDTOList = new ArrayList<>(); // friendDTO를 담을 리스트 배열

        // forEach(Consumer(반환하는 게 없음)), 람다식으로 friendDTOList에 엔티티를 DTO로 변환한거 넣기
        list.forEach((entity) -> friendDTOList.add(FriendEntity.toDTO(entity)));

        return friendDTOList;
    }

    public void deleteOne(Long friendSeq) {
        friendRepository.deleteById(friendSeq);

    }

    @SuppressWarnings("static-access") // 나중에
    public FriendDTO selectOne(Long friendSeq) {
        Optional<FriendEntity> entity = friendRepository.findById(friendSeq); // finById: 반환값이 Optional

        // entity에 데이터가 존재한다면
        if (entity.isPresent()) {
            FriendEntity friendEntity = entity.get();
            return friendEntity.toDTO(friendEntity);
        }

        return null;
    }

    @Transactional
    public void updateProc(FriendDTO friendDTO) {
        System.out.println("service - updateProc()" + friendDTO.toString());
        // update: find한 데이터 값을 set으로 바꾸기
        Optional<FriendEntity> entity = friendRepository.findById(friendDTO.getFriendSeq());

        if (entity.isPresent()) {
            FriendEntity f = entity.get();
            f.setFname(friendDTO.getFname());
            f.setAge(friendDTO.getAge());
            f.setPhone(friendDTO.getPhone());
            f.setBirthday(friendDTO.getBirthday());
            f.setActive(friendDTO.isActive());
        }

        // FriendEntity entity = friendDTO.toEntity(friendDTO);
        // friendRepository.updateFriend(entity);

    }
}
