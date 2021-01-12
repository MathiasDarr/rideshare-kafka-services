package org.mddarr.containerstestingservice.repositories.model;


import org.mddarr.containerstestingservice.models.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModelRepository extends JpaRepository<Model, UUID> {
}
