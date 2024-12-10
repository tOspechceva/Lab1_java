package tech.reliab.course.ospechceva.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.reliab.course.ospechceva.bank.entity.BankAtm;

import java.util.List;
import java.util.Optional;

public interface BankAtmRepository extends JpaRepository<BankAtm, Integer> {

    List<BankAtm> findAllByBankId(int id);

    void deleteById(int id);
}
