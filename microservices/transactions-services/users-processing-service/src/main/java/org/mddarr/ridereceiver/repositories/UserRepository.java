package org.mddarr.ridereceiver.repositories;


import org.mddarr.ridereceiver.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {


}

