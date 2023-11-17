package com.springtec.models.repositories;

import com.springtec.models.entity.ImgFirebase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgFirebaseRepository extends JpaRepository<ImgFirebase, Integer> {
}
