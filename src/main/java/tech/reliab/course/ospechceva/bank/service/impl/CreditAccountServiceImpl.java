package tech.reliab.course.ospechceva.bank.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.reliab.course.ospechceva.bank.entity.Bank;
import tech.reliab.course.ospechceva.bank.entity.CreditAccount;
import tech.reliab.course.ospechceva.bank.model.CreditAccountRequest;
import tech.reliab.course.ospechceva.bank.repository.CreditAccountRepository;
import tech.reliab.course.ospechceva.bank.service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CreditAccountServiceImpl implements CreditAccountService {

    private final CreditAccountRepository creditAccountRepository;
    private final BankService bankService;
    private final UserService userService;
    private final EmployeeService employeeService;
    private final PaymentAccountService paymentAccountService;

    /**
     * Создание нового кредитного аккаунта.
     *
     * @param creditAccountRequest данные аккаунта
     * @return Созданный кредитный аккаунт.
     */
    public CreditAccount createCreditAccount(CreditAccountRequest creditAccountRequest) {
        CreditAccount creditAccount = new CreditAccount(userService.getUserById(creditAccountRequest.getUserId()),
                                                        bankService.getBankById(creditAccountRequest.getBankId()), creditAccountRequest.getStartDate(),
                                                        creditAccountRequest.getLoanTermMonths(), creditAccountRequest.getInterestRate(),
                                                        employeeService.getEmployeeById(creditAccountRequest.getEmployeeId()),
                                                        paymentAccountService.getPaymentAccountById(creditAccountRequest.getPaymentAccountId()));
        creditAccount.setEndDate(calculateEndDate(creditAccountRequest.getStartDate(), creditAccountRequest.getLoanTermMonths()));
        creditAccount.setLoanAmount(calculateLoanAmount(creditAccountRequest.getLoanAmount(), creditAccountRequest.getBankId()));
        creditAccount.setMonthlyPayment(calculateMonthlyPayment(creditAccountRequest.getInterestRate(),
                                                                creditAccountRequest.getLoanAmount(), creditAccountRequest.getLoanTermMonths()));
        creditAccount.setInterestRate(calculateInterestRate(creditAccountRequest.getInterestRate(), creditAccountRequest.getBankId()));
        return creditAccountRepository.save(creditAccount);
    }

    /**
     * Вычисление даты окончания кредита.
     *
     * @param startDate         Дата начала кредита.
     * @param loanTermMonths    Срок кредита в месяцах.
     * @return Дата окончания кредита.
     */
    private LocalDate calculateEndDate(LocalDate startDate, int loanTermMonths) {
        return startDate.plusMonths(loanTermMonths);
    }

    /**
     * Расчет аннуитетного платежа по кредиту.
     *
     * @param interestRate      Процентная ставка по кредиту.
     * @param loanAmount        Сумма кредита.
     * @param loanTermMonths    Срок кредита в месяцах.
     * @return Размер аннуитетного платежа.
     */
    private double calculateMonthlyPayment(double interestRate, double loanAmount, int loanTermMonths) {
        double monthlyRate = interestRate / 12 / 100;
        return loanAmount * (monthlyRate / (1 - Math.pow(1 + monthlyRate, -loanTermMonths)));
    }

    /**
     * Расчет суммы кредита, не превышающей доступных средств банка.
     *
     * @param loanAmount Сумма кредита, запрошенная пользователем.
     * @param bankId      Банк, который предоставляет кредит.
     * @return Сумма кредита, не превышающая доступные средства банка.
     */
    private double calculateLoanAmount(double loanAmount, int bankId) {
        Bank bank = bankService.getBankById(bankId);
        if (loanAmount > bank.getTotalMoney()) {
            loanAmount = bank.getTotalMoney();
        }
        return loanAmount;
    }

    /**
     * Расчет процентной ставки по кредиту, не превышающей процентную ставку банка.
     *
     * @param interestRate Процентная ставка по кредиту, запрошенная пользователем.
     * @param bankId        Банк, который предоставляет кредит.
     * @return Процентная ставка по кредиту, не превышающая процентную ставку банка.
     */
    private double calculateInterestRate(double interestRate, int bankId) {
        Bank bank = bankService.getBankById(bankId);
        if (interestRate > bank.getInterestRate()) {
            System.out.println("Заданная процентная ставка превышает процентную ставку банка. Ставка будет скорректирована.");
            interestRate = bank.getInterestRate();
        }
        return interestRate;
    }

    /**
     * Чтение кредитного аккаунта по его идентификатору.
     *
     * @param id Идентификатор кредитного аккаунта.
     * @return Кредитный аккаунт, если он найден
     * @throws NoSuchElementException Если кредитный аккаунт не найден.
     */
    public CreditAccount getCreditAccountById(int id) {
        return creditAccountRepository.findById(id).orElseThrow(() -> new NoSuchElementException("CreditAccount was not found"));
    }

    /**
     * Чтение кредитного аккаунта по его идентификатору.
     *
     * @param id Идентификатор кредитного аккаунта.
     * @return Кредитный аккаунт, если он найден
     * @throws NoSuchElementException Если кредитный аккаунт не найден.
     */
    public CreditAccount getCreditAccountDtoById(int id) {
        return getCreditAccountById(id);
    }

    /**
     * Чтение всех кредитных аккаунтов.
     *
     * @return Список всех кредитных аккаунтов.
     */
    public List<CreditAccount> getAllCreditAccounts() {
        return creditAccountRepository.findAll();
    }

    /**
     * Обновление информации о кредитном аккаунте по его идентификатору.
     *
     * @param id   Идентификатор кредитного аккаунта.
     * @param bankId Банк, который предоставляет кредит.
     */
    public CreditAccount updateCreditAccount(int id, int bankId) {
        CreditAccount creditAccount = getCreditAccountById(id);
        creditAccount.setBank(bankService.getBankById(bankId));
        return creditAccountRepository.save(creditAccount);
    }

    /**
     * Удаление кредитного аккаунта по его идентификатору
     *
     * @param id Идентификатор кредитного аккаунта.
     */
    public void deleteCreditAccount(int id) {
        creditAccountRepository.deleteById(id);
    }
}
