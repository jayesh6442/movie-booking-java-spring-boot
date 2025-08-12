package org.testapp.moviebookingapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.testapp.moviebookingapp.model.Booking;
import org.testapp.moviebookingapp.model.Seat;
import org.testapp.moviebookingapp.model.Show;
import org.testapp.moviebookingapp.model.Ticket;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByShow(Show show);

    List<Ticket> findBySeat(Seat seat);

    List<Ticket> findByBooking(Booking booking);

    List<Ticket> findByStatus(Ticket.TicketStatus status);

    List<Ticket> findByShowAndStatus(Show show, Ticket.TicketStatus status);

    Optional<Ticket> findByShowAndSeat(Show show, Seat seat);

    @Query("SELECT t FROM Ticket t WHERE t.show.id = :showId AND t.status = 'AVAILABLE'")
    List<Ticket> findAvailableTicketsForShow(@Param("showId") Long showId);

    @Query("SELECT t FROM Ticket t WHERE t.show.id = :showId AND t.status = 'BOOKED'")
    List<Ticket> findBookedTicketsForShow(@Param("showId") Long showId);

    @Query("SELECT t FROM Ticket t WHERE t.show.id = :showId AND t.seat.type = :seatType AND t.status = 'AVAILABLE'")
    List<Ticket> findAvailableTicketsByShowAndSeatType(@Param("showId") Long showId, @Param("seatType") Seat.SeatType seatType);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.show.id = :showId AND t.status = :status")
    long countByShowIdAndStatus(@Param("showId") Long showId, @Param("status") Ticket.TicketStatus status);

    @Query("SELECT t FROM Ticket t WHERE t.booking.id = :bookingId")
    List<Ticket> findByBookingId(@Param("bookingId") Long bookingId);

//    @Query("SELECT t FROM Ticket t WHERE t.show.id = :showId AND t.seat.row = :row AND t.status = 'AVAILABLE' ORDER BY t.seat.number")
//    List<Ticket> findAvailableTicketsByShowAndRow(@Param("showId") Long showId, @Param("row") String row);

    boolean existsByShowAndSeat(Show show, Seat seat);
}