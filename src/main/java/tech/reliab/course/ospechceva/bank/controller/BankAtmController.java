package tech.reliab.course.ospechceva.bank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.reliab.course.ospechceva.bank.entity.BankAtm;
import tech.reliab.course.ospechceva.bank.model.BankAtmRequest;
import tech.reliab.course.ospechceva.bank.service.BankAtmService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank-atms")
public class BankAtmController {

    private final BankAtmService bankAtmService;

    @PostMapping
    public ResponseEntity<BankAtm> createBankAtm(BankAtmRequest bankAtmRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bankAtmService.createBankAtm(bankAtmRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankAtm(int id) {
        bankAtmService.deleteBankAtm(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BankAtm> updateBankAtm(int id, String name) {
        return ResponseEntity.ok(bankAtmService.updateBankAtm(id, name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAtm> getBankAtmById(int id) {
        return ResponseEntity.ok(bankAtmService.getBankAtmDtoById(id));
    }

    @GetMapping("/all-by-bank/{bankId}")
    public ResponseEntity<List<BankAtm>> getAllBankAtmByBankId(int bankId) {
        return ResponseEntity.ok(bankAtmService.getAllBankAtmsByBankId(bankId));
    }

    @GetMapping
    public ResponseEntity<List<BankAtm>> getAllBankAtms() {
        return ResponseEntity.ok(bankAtmService.getAllBankAtms());
    }
}
