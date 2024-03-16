package net.kdigital.test2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kdigital.test2.entity.GuestbookEntity;

public interface GuestbookRepository extends JpaRepository<GuestbookEntity, Long> {

}
