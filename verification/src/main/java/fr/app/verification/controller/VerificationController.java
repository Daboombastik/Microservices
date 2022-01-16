package fr.app.verification.controller;

import fr.app.verification.entity.VerificationHistoryDTO;
import fr.app.verification.service.VerificationHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/verification")
@Slf4j
public record VerificationController(VerificationHistoryService service) {

    @GetMapping(path = "{customerId}")
    public ResponseEntity<VerificationHistoryDTO> doVerification(@PathVariable Long customerId) {
        boolean isFraudster = service.isFraudulentCustomer(customerId);
        VerificationHistoryDTO response = new VerificationHistoryDTO(isFraudster);
        log.info("Verification for customer:{}", customerId);
        return ResponseEntity.<VerificationHistoryDTO>ok(response);

    }
}
