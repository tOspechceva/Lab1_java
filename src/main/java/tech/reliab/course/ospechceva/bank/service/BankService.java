package tech.reliab.course.ospechceva.bank.service;

import tech.reliab.course.ospechceva.bank.entity.Bank;

import java.util.List;
import java.util.Optional;

public interface BankService {

    Bank createBank(String bankName);

    Optional<Bank> getBankById(int id);

    List<Bank> getAllBanks();

    void updateBank(int id, String name);

    void deleteBank(int id);

    void addOffice(Bank bank);

    void addAtm(Bank bank);

    void addEmployee(Bank bank);

    void addClient(Bank bank);

    void removeOffice(Bank bank);

    void removeAtm(Bank bank);

    void removeEmployee(Bank bank);

    void removeClient(Bank bank);

    Bank getBankIfExists(int id);
}