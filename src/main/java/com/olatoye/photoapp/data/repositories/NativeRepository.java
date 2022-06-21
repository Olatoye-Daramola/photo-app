package com.olatoye.photoapp.data.repositories;

import com.olatoye.photoapp.data.models.Native;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NativeRepository extends JpaRepository<Native, Long> {
    List<Native> findByCohortCohortNumber(Integer cohortNumber);
    boolean existsByEmail(String email);
    Optional<Native> findByEmail(String email);
    Optional<Native> findByFirstName(String firstname);
    Optional<Native> findByLastName(String lastname);
    Optional<Native> findByUserName(String userName);
}
