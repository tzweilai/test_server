package com.mycompany.myapp.repository;
import com.mycompany.myapp.domain.AircraftModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Spring Data JPA repository for the AircraftModel entity.
 */
@SuppressWarnings("unused")
public interface AircraftModelRepository extends JpaRepository<AircraftModel,Long>,JpaSpecificationExecutor {

}
