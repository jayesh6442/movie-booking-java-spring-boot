package org.testapp.moviebookingapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.testapp.moviebookingapp.model.Booking;
import org.testapp.moviebookingapp.model.Show;
import org.testapp.moviebookingapp.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUser(User user);

    List<Booking> findByShow(Show show);

    List<Booking> findByPaymentStatus(Booking.PaymentStatus paymentStatus);

    List<Booking> findByUserAndPaymentStatus(User user, Booking.PaymentStatus paymentStatus);

    List<Booking> findByBookingTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId ORDER BY b.bookingTime DESC")
    List<Booking> findByUserIdOrderByBookingTimeDesc(@Param("userId") Long userId);

    @Query("SELECT b FROM Booking b WHERE b.show.id = :showId AND b.paymentStatus = 'PAID'")
    List<Booking> findPaidBookingsForShow(@Param("showId") Long showId);

    @Query("SELECT b FROM Booking b WHERE b.show.startTime >= :startTime AND b.user.id = :userId")
    List<Booking> findUpcomingBookingsByUser(@Param("userId") Long userId, @Param("startTime") LocalDateTime startTime);

    @Query("SELECT SUM(b.totalPrice) FROM Booking b WHERE b.paymentStatus = 'PAID' AND b.bookingTime BETWEEN :start AND :end")
    BigDecimal findTotalRevenueByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.show.id = :showId AND b.paymentStatus = 'PAID'")
    long countPaidBookingsForShow(@Param("showId") Long showId);

    @Query("SELECT b FROM Booking b WHERE b.paymentStatus = 'PENDING' AND b.bookingTime < :cutoffTime")
    List<Booking> findExpiredPendingBookings(@Param("cutoffTime") LocalDateTime cutoffTime);

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId AND b.show.movie.id = :movieId")
    List<Booking> findByUserIdAndMovieId(@Param("userId") Long userId, @Param("movieId") Long movieId);

    @Query("SELECT b FROM Booking b JOIN b.tickets t WHERE t.seat.theater.id = :theaterId AND b.paymentStatus = 'PAID'")
    List<Booking> findPaidBookingsByTheaterId(@Param("theaterId") Long theaterId);

    List<Booking> findByTotalPriceGreaterThan(BigDecimal amount);

    @Query("SELECT AVG(b.totalPrice) FROM Booking b WHERE b.paymentStatus = 'PAID'")
    BigDecimal findAverageBookingAmount();
}