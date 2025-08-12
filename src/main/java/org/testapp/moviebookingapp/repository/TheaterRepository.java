package org.testapp.moviebookingapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.testapp.moviebookingapp.model.Theater;

import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {

    List<Theater> findByNameContainingIgnoreCase(String name);

    List<Theater> findByLocationContainingIgnoreCase(String location);

    List<Theater> findByTotalSeatsGreaterThan(Integer seats);

    List<Theater> findByTotalSeatsLessThan(Integer seats);

    @Query("SELECT t FROM Theater t WHERE t.totalSeats BETWEEN :minSeats AND :maxSeats")
    List<Theater> findByTotalSeatsBetween(@Param("minSeats") Integer minSeats, @Param("maxSeats") Integer maxSeats);

    @Query("SELECT t FROM Theater t ORDER BY t.totalSeats DESC")
    List<Theater> findByOrderByTotalSeatsDesc();

    @Query("SELECT DISTINCT t.location FROM Theater t ORDER BY t.location")
    List<String> findDistinctLocations();

    @Query("SELECT t FROM Theater t WHERE t.location = :location ORDER BY t.totalSeats DESC")
    List<Theater> findByLocationOrderByTotalSeatsDesc(@Param("location") String location);
}
