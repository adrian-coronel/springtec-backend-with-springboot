package com.springtec.models.repositories;

import com.springtec.models.entity.Availability;
import com.springtec.models.entity.Technical;
import com.springtec.models.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

// (input_latitude DOUBLE,input_longitude DOUBLE,max_distance DOUBLE,professionId INT,availabilityId INT)
@Repository
public interface TechnicalRepository extends JpaRepository<Technical, Integer> {
    boolean existsByDniAndUserState(String dni, char user_state);
    boolean existsByIdAndUserState(Integer id, char user_state);
    List<Technical> findAllByUserState(char user_state);
    Technical findByIdAndUserState(Integer id, char user_state);
    List<Technical> findAllByAvailabilityIdAndUserState(Integer availability_id, char user_state);

    Technical findByUser(User user);


    @Procedure(procedureName = "GetNearbyTechnicalsAndProfessionAndAvailability")
    List<Technical> findAllNeabyByProfessionIdAndAvailabilityId(
        double input_latitude,double input_longitude,double max_distance,int professionId,int availabilityId
    );

}
