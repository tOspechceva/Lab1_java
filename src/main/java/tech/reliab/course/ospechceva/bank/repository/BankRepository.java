package tech.reliab.course.ospechceva.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.reliab.course.ospechceva.bank.entity.Bank;

import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Integer> {

    void deleteById(int id);
}
