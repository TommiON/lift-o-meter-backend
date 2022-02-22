package org.tommi.back.repositories;

import org.springframework.stereotype.Repository;
import org.tommi.back.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface WorkoutRepository extends JpaRepository <Workout, Long> {

}
