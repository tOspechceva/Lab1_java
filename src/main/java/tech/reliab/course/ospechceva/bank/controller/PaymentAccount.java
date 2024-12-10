package tech.reliab.course.ospechceva.bank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.reliab.course.ospechceva.bank.model.PaymentAccountRequest;
import tech.reliab.course.ospechceva.bank.service.PaymentAccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment-accounts")
public class PaymentAccount{

    private final PaymentAccountService paymentAccountService;

    @PostMapping
    public ResponseEntity<tech.reliab.course.ospechceva.bank.entity.PaymentAccount> createPaymentAccount(PaymentAccountRequest paymentAccountRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentAccountService.createPaymentAccount(paymentAccountRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentAccount(int id) {
        paymentAccountService.deletePaymentAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<tech.reliab.course.ospechceva.bank.entity.PaymentAccount> updatePaymentAccount(int id, int bankId) {
        return ResponseEntity.ok(paymentAccountService.updatePaymentAccount(id, bankId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<tech.reliab.course.ospechceva.bank.entity.PaymentAccount> getBankByPaymentAccount(int id) {
        return ResponseEntity.ok(paymentAccountService.getPaymentAccountDtoById(id));
    }

    @GetMapping
    public ResponseEntity<List<tech.reliab.course.ospechceva.bank.entity.PaymentAccount>> getAllPaymentAccounts() {
        return ResponseEntity.ok(paymentAccountService.getAllPaymentAccounts());
    }
}
