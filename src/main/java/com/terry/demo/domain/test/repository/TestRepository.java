package com.terry.demo.domain.test.repository;


import com.terry.demo.core.entity.PfTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<PfTest, Long> {

    Optional<PfTest> findByIdEmail(String email);

}
