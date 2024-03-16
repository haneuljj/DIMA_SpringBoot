package main.java.net.kdigital.spring6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import net.kdigital.spring6.entity.FriendEntity;

public interface FriendRepository extends JpaRepository<FriendEntity, Long> { // <엔티티명, 아이디의 데이터 타입>

    // @Transactional
    // @Modifying
    // @Query(value = """
    // UPDATE friend
    // SET
    // fname = :#{#entity.fname}
    // , phone = :#{#entity.phone}
    // WHERE friend_seq = :#{#entity.friendSeq}
    // """, nativeQuery = true) // placeholder: :#{#매개변수로온 객체의 속성값가져오기}
    // public int updateFriend(@Param("entity") FriendEntity entity); // @Param: ?

}
