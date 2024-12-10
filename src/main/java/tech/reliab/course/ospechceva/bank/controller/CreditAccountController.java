package tech.reliab.course.ospechceva.bank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.reliab.course.ospechceva.bank.entity.CreditAccount;
import tech.reliab.course.ospechceva.bank.model.CreditAccountRequest;
import tech.reliab.course.ospechceva.bank.service.CreditAccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/credit-accounts")
public class CreditAccountController{

    private final CreditAccountService creditAccountService;

    @PostMapping
    public ResponseEntity<CreditAccount> createCreditAccount(CreditAccountRequest creditAccountRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(creditAccountService.createCreditAccount(creditAccountRequest));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCreditAccount(int id) {
        creditAccountService.deleteCreditAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CreditAccount> updateCreditAccount(int id, int bankId) {
        return ResponseEntity.ok(creditAccountService.updateCreditAccount(id, bankId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditAccount> getBankByCreditAccount(int id) {
        return ResponseEntity.ok(creditAccountService.getCreditAccountDtoById(id));
    }

    @GetMapping
    public ResponseEntity<List<CreditAccount>> getAllCreditAccounts() {
        return ResponseEntity.ok(creditAccountService.getAllCreditAccounts());
    }
}
