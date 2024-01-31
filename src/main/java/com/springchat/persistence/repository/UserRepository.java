package com.springchat.persistence.repository;

import com.springchat.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);

    Boolean existsByUsername(String username);

    /*
    @Query("SELECT "
            + " new com.mailbox.service.dto.UserDTO("
            + " u.mailAddress,"
            + " u.mailPassword)"
            + " FROM User u"
            + " WHERE u.username = :username"
            + " AND u.mailAddress = :mailAddress"
            + " AND u.mailPassword = :mailPassword ")
    MailDTO getUserMails(@Param("mailAddress") String mailAddress,
                         @Param("mailPassword") String mailPassword,
                         @Param("username") String username);

     */
}
