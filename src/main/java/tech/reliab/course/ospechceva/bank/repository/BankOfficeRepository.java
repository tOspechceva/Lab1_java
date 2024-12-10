package tech.reliab.course.ospechceva.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.reliab.course.ospechceva.bank.entity.BankOffice;

import java.util.Optional;

public interface BankOfficeRepository extends JpaRepository<BankOffice, Integer> {

    void deleteById(int id);
}
