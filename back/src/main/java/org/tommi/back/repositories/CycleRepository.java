package org.tommi.back.repositories;

import org.springframework.stereotype.Repository;
import org.tommi.back.entities.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tommi.back.entities.Cycle;

@Repository
public interface CycleRepository extends JpaRepository<Cycle, Long> {

}
