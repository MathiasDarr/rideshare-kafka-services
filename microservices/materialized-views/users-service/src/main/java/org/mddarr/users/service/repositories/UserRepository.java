package org.mddarr.users.service.repositories;

import org.mddarr.users.service.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT userid, first_name, last_name, email FROM users;", nativeQuery = true)
    List<User> getUserByID();

}

