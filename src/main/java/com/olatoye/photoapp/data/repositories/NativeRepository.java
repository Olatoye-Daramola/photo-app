package com.olatoye.photoapp.data.repositories;

import com.olatoye.photoapp.data.models.Native;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NativeRepository extends JpaRepository<Native, Long> {
    List<Native> findByCohortNumber(Integer cohortNumber);
    List<Native> findByEmail(String email);
    List<Native> findByFirstName(String firstname);
    List<Native> findByLastName(String lastname);
    List<Native> findByUserName(String userName);
}
