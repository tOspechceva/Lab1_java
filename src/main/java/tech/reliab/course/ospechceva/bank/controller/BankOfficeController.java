package tech.reliab.course.ospechceva.bank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.reliab.course.ospechceva.bank.entity.BankOffice;
import tech.reliab.course.ospechceva.bank.model.BankOfficeRequest;
import tech.reliab.course.ospechceva.bank.service.BankOfficeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank-offices")
public class BankOfficeController  {

    private final BankOfficeService bankOfficeService;

    @PostMapping
    public ResponseEntity<BankOffice> createBankOffice(BankOfficeRequest bankOfficeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bankOfficeService.createBankOffice(bankOfficeRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankOffice(int id) {
        bankOfficeService.deleteBankAtm(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BankOffice> updateBankOffice(int id, String name) {
        return ResponseEntity.ok(bankOfficeService.updateBankOffice(id, name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankOffice> getBankOfficeById(int id) {
        return ResponseEntity.ok(bankOfficeService.getBankDtoOfficeById(id));
    }

    @GetMapping
    public ResponseEntity<List<BankOffice>> getAllBankOffices() {
        return ResponseEntity.ok(bankOfficeService.getAllBankOffices());
    }
}
