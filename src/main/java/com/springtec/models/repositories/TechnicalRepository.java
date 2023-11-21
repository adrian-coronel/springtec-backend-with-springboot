package com.springtec.models.repositories;

import com.springtec.models.entity.Technical;
import com.springtec.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// (input_latitude DOUBLE,input_longitude DOUBLE,max_distance DOUBLE,professionId INT,availabilityId INT)
@Repository
public interface TechnicalRepository extends JpaRepository<Technical, Integer> {
    boolean existsByDniAndUserState(String dni, char user_state);
    boolean existsByIdAndUserState(Integer id, char user_state);
    List<Technical> findAllByUserState(char user_state);
    Technical findByIdAndUserState(Integer id, char user_state);
    //List<Technical> findAllByAvailabilityIdAndUserState(Integer availability_id, char user_state);

    Technical findByUser(User user);


    @Procedure(procedureName = "findNearbyTechnicals")
    List<Technical> findAllNeabyByProfessionIdAndAvailabilityId(
        double input_latitude,double input_longitude,double max_distance,int profession_id,int exclude_availability_id
    );

    @Query("SELECT t FROM Technical t " +
        "INNER JOIN DetailsTechnical dt ON t = dt.technical " +
        "WHERE dt.profession.id = :professionId " +
        "AND dt.availability.id != :excludeAvailabilityId")
    List<Technical> findByProfessionAndExcludeAvailability(
        @Param("professionId") int professionId,
        @Param("excludeAvailabilityId") int excludeAvailabilityId
    );

    @Query("SELECT t FROM Technical t "+
        "INNER JOIN DetailsTechnical dt ON t = dt.technical "+
        "WHERE dt.profession.id = :professionId")
    List<Technical> findAllByDetailsTechnicalsProfessionId(@Param("professionId") int professionId);

    @Query("SELECT t FROM Technical t "+
        "INNER JOIN DetailsTechnical dt ON t = dt.technical "+
        "WHERE dt.availability.id != :excludeAvailabilityId")
    List<Technical> findAllByDetailsTechnicalsExcludeAvailabilityId(@Param("excludeAvailabilityId") int excludeAvailabilityId);
}
