package com.itsoul.lab.app.repository;

import com.itsoul.lab.app.beans.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerService extends JpaRepository<Passenger, Integer> {
    Optional<Passenger> findByName(String name);
}