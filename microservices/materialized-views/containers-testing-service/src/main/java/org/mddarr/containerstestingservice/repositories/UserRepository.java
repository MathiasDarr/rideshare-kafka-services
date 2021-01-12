package org.mddarr.containerstestingservice.repositories;


import org.mddarr.containerstestingservice.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
