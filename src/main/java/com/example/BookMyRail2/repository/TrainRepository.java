package com.example.BookMyRail2.repository;

import com.example.BookMyRail2.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRepository extends JpaRepository<Train, Long> {
}
