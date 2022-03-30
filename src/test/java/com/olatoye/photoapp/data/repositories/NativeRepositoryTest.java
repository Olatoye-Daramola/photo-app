package com.olatoye.photoapp.data.repositories;

import com.olatoye.photoapp.data.models.Cohort;
import com.olatoye.photoapp.data.models.Native;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

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
                .name("Phoenix")
                .number(8)
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
        List<Native> foundNatives = nativeRepository.findByCohortNumber(8);
        assertThat(foundNatives.size()).isEqualTo(1);
    }

    @Test
    void findByEmail() {
        List<Native> foundNatives = nativeRepository.findByEmail("n.native1@email.com");
        assertThat(foundNatives.size()).isEqualTo(1);
    }

    @Test
    void findByFirstNameAndLastName() {
        List<Native> foundNatives = nativeRepository.findByFirstName("Native1");
        assertThat(foundNatives.get(0)).isEqualTo(nativeRepository.findByLastName("native1").get(0));
    }

    @Test
    void findByUserName() {
        List<Native> foundNatives = nativeRepository.findByUserName("native");
        assertThat(foundNatives.get(0)).isEqualTo(nativeRepository.findByLastName("native1").get(0));
    }

//    @Test
//    void
}