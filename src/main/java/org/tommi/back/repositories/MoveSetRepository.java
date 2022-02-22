package org.tommi.back.repositories;

import org.springframework.stereotype.Repository;
import org.tommi.back.entities.MoveSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.tommi.back.entities.Workout;

import java.util.List;

@Repository
public interface MoveSetRepository extends JpaRepository<MoveSet, Long> {

}
