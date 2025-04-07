package com.ecom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecom.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByemailIdIgnoreCase(String emailId);

    Boolean existsByemailId(String emailId);
}
