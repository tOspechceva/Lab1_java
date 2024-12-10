package tech.reliab.course.ospechceva.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.reliab.course.ospechceva.bank.entity.PaymentAccount;

import java.util.Optional;

public interface PaymentAccountRepository extends JpaRepository<PaymentAccount, Integer> {

    void deleteById(int id);
}
