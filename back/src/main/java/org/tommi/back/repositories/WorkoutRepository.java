package org.tommi.back.repositories;

import org.tommi.back.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository <Workout, Long> {

}
