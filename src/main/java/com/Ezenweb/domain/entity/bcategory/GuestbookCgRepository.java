package com.Ezenweb.domain.entity.bcategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestbookCgRepository extends JpaRepository< GuestbookCgEntity, Integer > {
}
