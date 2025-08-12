package org.testapp.moviebookingapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.testapp.moviebookingapp.model.Seat;
import org.testapp.moviebookingapp.model.Theater;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByTheater(Theater theater);

//    List<Seat> findByTheaterOrderByRowAscNumberAsc(Theater theater);

    List<Seat> findByType(Seat.SeatType type);

    List<Seat> findByTheaterAndType(Theater theater, Seat.SeatType type);

    // Commented out until Seat entity structure is confirmed
    // Optional<Seat> findByTheaterAndRowAndNumber(Theater theater, String row, Integer number);

    // List<Seat> findByRow(String row);

    // List<Seat> findByTheaterAndRow(Theater theater, String row);

//    @Query("SELECT s FROM Seat s WHERE s.theater.id = :theaterId ORDER BY s.row, s.number")
//    List<Seat> findByTheaterIdOrderByRowAndNumber(@Param("theaterId") Long theaterId);

    // Commented out until Seat entity structure is confirmed
    // @Query("SELECT COUNT(s) FROM Seat s WHERE s.theater.id = :theaterId AND s.type = :type")
    // long countByTheaterIdAndType(@Param("theaterId") Long theaterId, @Param("type") Seat.SeatType type);

//    @Query("SELECT DISTINCT s.row FROM Seat s WHERE s.theater.id = :theaterId ORDER BY s.row")
//    List<String> findDistinctRowsByTheaterId(@Param("theaterId") Long theaterId);
}