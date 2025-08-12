package org.testapp.moviebookingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.testapp.moviebookingapp.model.Movie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByTitleContainingIgnoreCase(String title);

    List<Movie> findByRating(String rating);

    List<Movie> findByReleaseDateBetween(LocalDate startDate, LocalDate endDate);

    List<Movie> findByReleaseDateAfter(LocalDate date);

    List<Movie> findByReleaseDateBefore(LocalDate date);

    @Query("SELECT m FROM Movie m WHERE m.lengthMinutes BETWEEN :minLength AND :maxLength")
    List<Movie> findByLengthBetween(@Param("minLength") Integer minLength, @Param("maxLength") Integer maxLength);

    @Query("SELECT m FROM Movie m WHERE m.description LIKE %:keyword%")
    List<Movie> findByDescriptionContaining(@Param("keyword") String keyword);

    @Query("SELECT m FROM Movie m JOIN m.shows s WHERE s.startTime >= :date GROUP BY m ORDER BY COUNT(s) DESC")
    List<Movie> findMostPopularMovies(@Param("date") LocalDateTime date);

    @Query("SELECT m FROM Movie m ORDER BY m.releaseDate DESC")
    List<Movie> findLatestMovies();
}