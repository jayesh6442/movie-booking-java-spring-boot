package org.testapp.moviebookingapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.testapp.moviebookingapp.model.Movie;
import org.testapp.moviebookingapp.model.Show;
import org.testapp.moviebookingapp.model.Theater;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByMovie(Movie movie);

    List<Show> findByTheater(Theater theater);

    List<Show> findByMovieAndTheater(Movie movie, Theater theater);

    List<Show> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT s FROM Show s WHERE s.startTime >= :startTime ORDER BY s.startTime")
    List<Show> findUpcomingShows(@Param("startTime") LocalDateTime startTime);

    @Query("SELECT s FROM Show s WHERE s.movie.id = :movieId AND s.startTime >= :startTime ORDER BY s.startTime")
    List<Show> findUpcomingShowsByMovie(@Param("movieId") Long movieId, @Param("startTime") LocalDateTime startTime);

    @Query("SELECT s FROM Show s WHERE s.theater.id = :theaterId AND s.startTime BETWEEN :start AND :end ORDER BY s.startTime")
    List<Show> findShowsByTheaterAndDateRange(@Param("theaterId") Long theaterId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT s FROM Show s WHERE s.movie.id = :movieId AND s.theater.location LIKE %:location% AND s.startTime >= :startTime")
    List<Show> findShowsByMovieAndLocation(@Param("movieId") Long movieId, @Param("location") String location, @Param("startTime") LocalDateTime startTime);

    List<Show> findByPricePerTicketLessThanEqual(BigDecimal maxPrice);

    List<Show> findByPricePerTicketBetween(BigDecimal minPrice, BigDecimal maxPrice);

    @Query("SELECT s FROM Show s WHERE DATE(s.startTime) = DATE(:date) ORDER BY s.startTime")
    List<Show> findShowsForDate(@Param("date") LocalDateTime date);

    @Query("SELECT COUNT(t) FROM Show s JOIN s.tickets t WHERE s.id = :showId AND t.status = 'BOOKED'")
    long countBookedTicketsForShow(@Param("showId") Long showId);

    @Query("SELECT COUNT(t) FROM Show s JOIN s.tickets t WHERE s.id = :showId AND t.status = 'AVAILABLE'")
    long countAvailableTicketsForShow(@Param("showId") Long showId);
}
