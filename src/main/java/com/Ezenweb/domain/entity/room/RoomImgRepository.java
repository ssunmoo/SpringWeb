package com.Ezenweb.domain.entity.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomImgRepository extends JpaRepository<RoomImgEntity, Integer> {
}
