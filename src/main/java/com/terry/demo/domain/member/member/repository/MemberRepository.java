package com.terry.demo.domain.member.member.repository;


import com.terry.demo.core.entity.PfMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<PfMember, Long> {

    Optional<PfMember> findByIdEmail(String email);

}
