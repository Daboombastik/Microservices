package fr.app.verification.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VerificationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "verification_id_sequence")
    @SequenceGenerator(name = "verification_id_sequence", sequenceName = "verification_id_sequence")
    private Long id;

    private Long customerId;
    private Boolean isFraudster;
    private LocalDateTime createdAt;
}
