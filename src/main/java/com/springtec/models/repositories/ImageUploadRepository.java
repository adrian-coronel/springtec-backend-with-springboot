package com.springtec.models.repositories;

import com.springtec.models.entity.ImageUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageUploadRepository extends JpaRepository<ImageUpload, Integer> {

   List<ImageUpload> findAllByDirectRequestId(Integer directRequestId);

}
