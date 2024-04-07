package net.kdigital.market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kdigital.market.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, String>{

}
