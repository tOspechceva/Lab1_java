package tech.reliab.course.ospechceva.bank.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.reliab.course.ospechceva.bank.entity.PaymentAccount;
import tech.reliab.course.ospechceva.bank.model.PaymentAccountRequest;
import tech.reliab.course.ospechceva.bank.repository.PaymentAccountRepository;
import tech.reliab.course.ospechceva.bank.service.BankService;
import tech.reliab.course.ospechceva.bank.service.PaymentAccountService;
import tech.reliab.course.ospechceva.bank.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PaymentAccountServiceImpl implements PaymentAccountService {

    private final PaymentAccountRepository paymentAccountRepository;
    private final UserService userService;
    private final BankService bankService;

    /**
     * Создание нового платежного аккаунта.
     *
     * @param paymentAccountRequest содержит информацию о userId и bankId
     * @return Созданный платежный аккаунт.
     */
    public PaymentAccount createPaymentAccount(PaymentAccountRequest paymentAccountRequest) {
        PaymentAccount paymentAccount = new PaymentAccount(userService.getUserById(paymentAccountRequest.getUserId()),
                                                           bankService.getBankById(paymentAccountRequest.getBankId()));
        return paymentAccountRepository.save(paymentAccount);
    }

    /**
     * Чтение платежного аккаунта по его идентификатору.
     *
     * @param id Идентификатор платежного аккаунта.
     * @return Платежный аккаунт
     * @throws NoSuchElementException Если платежный аккаунт не найден.
     */
    public PaymentAccount getPaymentAccountById(int id) {
        return paymentAccountRepository.findById(id).orElseThrow(() -> new NoSuchElementException("PaymentAccount was not found"));
    }

    public PaymentAccount getPaymentAccountDtoById(int id) {
        return getPaymentAccountById(id);
    }
    /**
     * Чтение всех платежных аккаунтов.
     *
     * @return Список всех платежных аккаунтов.
     */
    public List<PaymentAccount> getAllPaymentAccounts() {
        return paymentAccountRepository.findAll();
    }

    /**
     * Обновление информации о платежном аккаунте по его идентификатору.
     *
     * @param id   Идентификатор платежного аккаунта.
     * @param bankId id банка, в котором открыт аккаунт.
     */
    public PaymentAccount updatePaymentAccount(int id, int bankId) {
        PaymentAccount paymentAccount = getPaymentAccountById(id);
        paymentAccount.setBank(bankService.getBankById(bankId));
        return paymentAccountRepository.save(paymentAccount);
    }

    /**
     * Удаление платежного аккаунта по его идентификатору.
     *
     * @param id Идентификатор платежного аккаунта.
     */
    public void deletePaymentAccount(int id) {
        paymentAccountRepository.deleteById(id);
    }
}
