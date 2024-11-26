package tech.reliab.course.ospechceva.bank.service.impl;

import tech.reliab.course.ospechceva.bank.entity.Bank;
import tech.reliab.course.ospechceva.bank.entity.PaymentAccount;
import tech.reliab.course.ospechceva.bank.entity.User;
import tech.reliab.course.ospechceva.bank.service.BankService;
import tech.reliab.course.ospechceva.bank.service.PaymentAccountService;
import tech.reliab.course.ospechceva.bank.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class PaymentAccountServiceImpl implements PaymentAccountService {

    private static int paymentAccountCount = 0;

    private final UserService userService;
    private final BankService bankService;

    private List<PaymentAccount> paymentAccounts = new ArrayList<>();

    public PaymentAccountServiceImpl (UserService userService, BankService bankService) {
        this.userService = userService;
        this.bankService = bankService;
    }

    /**
     * Создание нового платежного аккаунта.
     *
     * @param user Пользователь, которому принадлежит аккаунт.
     * @param bank Банк, в котором открыт аккаунт.
     * @return Созданный платежный аккаунт.
     */
    public PaymentAccount createPaymentAccount(User user, Bank bank) {
        PaymentAccount paymentAccount = new PaymentAccount(user, bank);
        paymentAccount.setId(paymentAccountCount++);
        paymentAccounts.add(paymentAccount);
        userService.addPaymentAccount(paymentAccount, user);
        userService.addBank(bank, user);
        bankService.addClient(bank);
        return paymentAccount;
    }

    /**
     * Чтение платежного аккаунта по его идентификатору.
     *
     * @param id Идентификатор платежного аккаунта.
     * @return Платежный аккаунт, если он найден, иначе - пустой Optional.
     */
    public Optional<PaymentAccount> getPaymentAccountById(int id) {
        return paymentAccounts.stream()
                .filter(paymentAccount -> paymentAccount.getId() == id)
                .findFirst();
    }

    /**
     * Чтение всех платежных аккаунтов.
     *
     * @return Список всех платежных аккаунтов.
     */
    public List<PaymentAccount> getAllPaymentAccounts() {
        return new ArrayList<>(paymentAccounts);
    }

    /**
     * Обновление информации о платежном аккаунте по его идентификатору.
     *
     * @param id   Идентификатор платежного аккаунта.
     * @param bank Банк, в котором открыт аккаунт.
     */
    public void updatePaymentAccount(int id, Bank bank) {
        PaymentAccount paymentAccount = getPaymentAccountIfExists(id);
        paymentAccount.setBank(bank);
    }

    /**
     * Удаление платежного аккаунта по его идентификатору.
     *
     * @param id Идентификатор платежного аккаунта.
     */
    public void deletePaymentAccount(int id) {
        PaymentAccount paymentAccount = getPaymentAccountIfExists(id);
        paymentAccounts.remove(paymentAccount);
        userService.deletePaymentAccount(paymentAccount, paymentAccount.getUser());
    }

    /**
     * Получение платежного аккаунта по его идентификатору, если он существует.
     *
     * @param id Идентификатор платежного аккаунта.
     * @return Платежный аккаунт, если он найден.
     * @throws NoSuchElementException Если платежный аккаунт не найден.
     */
    private PaymentAccount getPaymentAccountIfExists(int id) {
        return getPaymentAccountById(id).orElseThrow(() -> new NoSuchElementException("PaymentAccount was not found"));
    }
}
