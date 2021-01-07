package org.mddarr.processingservice.repositories;

import org.mddarr.processingservice.models.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {


}

