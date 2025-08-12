package org.testapp.moviebookingapp.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "seats", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"theater_id", "row_label", "number"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"theater", "tickets"})
public class Seat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @NotBlank
    @Column(name = "row_label", nullable = false)
    private String rowLabel;

    @NotNull
    @Column(nullable = false)
    private Integer number;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private SeatType type = SeatType.REGULAR;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Ticket> tickets;

    public enum SeatType {
        REGULAR, VIP, PREMIUM
    }
}
