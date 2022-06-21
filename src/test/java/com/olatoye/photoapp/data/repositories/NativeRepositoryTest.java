package com.olatoye.photoapp.data.repositories;

import com.olatoye.photoapp.data.models.Cohort;
import com.olatoye.photoapp.data.models.Native;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class NativeRepositoryTest {

    private final NativeRepository nativeRepository;

    @Autowired
    NativeRepositoryTest(NativeRepository nativeRepository) {
        this.nativeRepository = nativeRepository;
    }

    Native firstNative;

    @BeforeEach
    void setUp() {
        nativeRepository.deleteAll();

        Cohort cohortEight = Cohort.builder()
                .cohortName("Phoenix")
                .cohortNumber(8)
                .build();


        Native aNative = Native.builder()
                .firstName("Native1")
                .lastName("native1")
                .userName("native")
                .cohort(cohortEight)
                .email("n.native1@email.com")
                .build();
        firstNative = nativeRepository.save(aNative);
    }

    @Test
    void findByCohortNumber() {
        List<Native> foundNatives = nativeRepository.findByCohortCohortNumber(8);
        assertThat(foundNatives.size()).isEqualTo(1);
    }

    @Test
    void findByEmail() {
        Optional<Native> foundNative = nativeRepository.findByEmail("n.native1@email.com");
        assert foundNative.isPresent();
        assertThat(foundNative.get()).isNotNull();
    }

    @Test
    void findByFirstNameAndLastName() {
        Optional<Native> foundNative = nativeRepository.findByFirstName("Native1");
        assert foundNative.isPresent();
        assertThat(foundNative.get()).isNotNull();
    }

    @Test
    void findByUserName() {
        Optional<Native> foundNative = nativeRepository.findByUserName("native");
        assert foundNative.isPresent();
        assertThat(foundNative.get()).isNotNull();
    }

//    @Test
//    void
}