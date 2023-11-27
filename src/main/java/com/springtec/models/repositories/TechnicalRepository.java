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


    @Query(nativeQuery = true, value = "CALL UpdateTechnicalLocation(:pTechnicalId, :pLatitude, :pLongitude, :pWorkingStatus);")
    String updateTechnicalLocation(
        @Param("pTechnicalId") int technicalId,
        @Param("pLatitude") double latitude,
        @Param("pLongitude") double longitude,
        @Param("pWorkingStatus") char workingStatus
    );

    @Query(nativeQuery = true, value = "CALL FilterTechnicalsAvailabilityNoInLocal(:pAvailabilityId,:pProfessionId, :pRango, :pLatitude, :pLongitude, :pWorkingStatus);")
    List<Technical> filterTechnicalsAvailabilityNoInLocal(
        @Param("pAvailabilityId") int availabilityId,
        @Param("pProfessionId") int professionId,
        @Param("pRango") int rango,
        @Param("pLatitude") double latitude,
        @Param("pLongitude") double longitude,
        @Param("pWorkingStatus") char workingStatus
    );

    @Query(nativeQuery = true, value = "CALL FilterTechnicalsAvailabilityIsLocal(:pAvailabilityId, :pProfessionId, :pRango, :pLatitude, :pLongitude);")
    List<Technical> filterTechnicalsAvailabilityIsLocal(
        @Param("pAvailabilityId") int availabilityId,
        @Param("pProfessionId") int professionId,
        @Param("pRango") int rango,
        @Param("pLatitude") double latitude,
        @Param("pLongitude") double longitude
    );

    @Query(nativeQuery = true, value = "CALL FilterNearbyAllTechnicals(:pProfessionId, :pRango, :pLatitude, :pLongitude, :pWorkingStatus);")
    List<Technical> FilterNearbyAllTechnicals(
        @Param("pProfessionId") int professionId,
        @Param("pRango") int rango,
        @Param("pLatitude") double latitude,
        @Param("pLongitude") double longitude,
        @Param("pWorkingStatus") char workingStatus
    );


}
