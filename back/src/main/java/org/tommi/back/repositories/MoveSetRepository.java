package org.tommi.back.repositories;

import org.springframework.stereotype.Repository;
import org.tommi.back.entities.MoveSet;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MoveSetRepository extends JpaRepository<MoveSet, Long> {

}
