package org.mddarr.containerstestingservice.repositories.auto;


import org.mddarr.containerstestingservice.models.Auto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutoRepository extends JpaRepository<Auto, UUID> {
}
