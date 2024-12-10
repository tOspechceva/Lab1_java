package tech.reliab.course.ospechceva.bank.service;

import tech.reliab.course.ospechceva.bank.entity.Bank;

import java.util.List;

public interface BankService {
    Bank createBank(String bankName);

    Bank getBankById(int id);

    Bank getBankDtoById(int id);

    List<Bank> getAllBanks();

    Bank updateBank(int id, String name);

    void deleteBank(int id);
}
