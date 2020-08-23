package org.tommi.back.repositories;

import org.tommi.back.entities.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tommi.back.entities.Cycle;

public interface CycleRepository extends JpaRepository<Cycle, Long> {

}
