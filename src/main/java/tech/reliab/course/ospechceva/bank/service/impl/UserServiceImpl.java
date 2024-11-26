package tech.reliab.course.ospechceva.bank.service.impl;

import tech.reliab.course.ospechceva.bank.entity.Bank;
import tech.reliab.course.ospechceva.bank.entity.CreditAccount;
import tech.reliab.course.ospechceva.bank.entity.PaymentAccount;
import tech.reliab.course.ospechceva.bank.entity.User;
import tech.reliab.course.ospechceva.bank.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

public class UserServiceImpl implements UserService {

    private static final int MONTHLY_INCOME_BOUND = 10001;
    private static final double DIVIDER = 1000.0;
    private static final int FACTOR = 100;
    private static int usersCount = 0;

    private List<User> users = new ArrayList<>();

    /**
     * Создание нового пользователя.
     *
     * @param fullName   Полное имя пользователя.
     * @param birthDate Дата рождения пользователя.
     * @param job        Профессия пользователя.
     * @return Созданный пользователь.
     */
    public User createUser(String fullName, LocalDate birthDate, String job) {
        User user = new User(fullName, birthDate, job);
        user.setId(usersCount++);
        user.setMonthlyIncome(generateMonthlyIncome());
        user.setCreditRating(generateCreditRating(user.getMonthlyIncome()));
        users.add(user);
        return user;
    }

    /**
     * Генерация случайного месячного дохода пользователя.
     *
     * @return Случайный месячный доход.
     */
    private int generateMonthlyIncome() {
        return new Random().nextInt(MONTHLY_INCOME_BOUND);
    }

    /**
     * Генерация кредитного рейтинга пользователя,
     * основанного на его месячном доходе.
     *
     * @param monthlyIncome Месячный доход пользователя.
     * @return Кредитный рейтинг пользователя.
     */
    private int generateCreditRating(double monthlyIncome) {
        return (int) Math.ceil(monthlyIncome / DIVIDER) * FACTOR;
    }

    /**
     * Чтение пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Пользователь, если он найден, иначе - пустой Optional.
     */
    public Optional<User> getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    /**
     * Чтение всех пользователей.
     *
     * @return Список всех пользователей.
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    /**
     * Обновление информации о пользователе по его идентификатору.
     *
     * @param id   Идентификатор пользователя.
     * @param name Новое имя пользователя.
     */
    public void updateUser(int id, String name) {
        User user = getUserIfExists(id);
        user.setFullName(name);
    }

    /**
     * Удаление пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     */
    public void deleteUser(int id) {
        users.remove(getUserIfExists(id));
    }

    /**
     * Получение пользователя по его идентификатору, если он существует.
     *
     * @param id Идентификатор пользователя.
     * @return Пользователь, если он найден.
     * @throws NoSuchElementException Если пользователь не найден.
     */
    public User getUserIfExists(int id) {
        return getUserById(id).orElseThrow(() -> new NoSuchElementException("User was not found"));
    }

    /**
     * Добавление кредитного аккаунта пользователю.
     *
     * @param creditAccount Кредитный аккаунт.
     * @param user         Пользователь, которому принадлежит аккаунт.
     */
    public void addCreditAccount(CreditAccount creditAccount, User user) {
        List<CreditAccount> creditAccounts = user.getCreditAccounts();
        creditAccounts.add(creditAccount);
        user.setCreditAccounts(creditAccounts);
    }

    /**
     * Добавление платежного аккаунта пользователю.
     *
     * @param paymentAccount Платежный аккаунт.
     * @param user           Пользователь, которому принадлежит аккаунт.
     */
    public void addPaymentAccount(PaymentAccount paymentAccount, User user) {
        List<PaymentAccount> paymentAccounts = user.getPaymentAccounts();
        paymentAccounts.add(paymentAccount);
        user.setPaymentAccounts(paymentAccounts);
    }

    /**
     * Добавление банка к списку банков пользователя.
     *
     * @param bank Банк.
     * @param user Пользователь, которому добавляется банк.
     */
    public void addBank(Bank bank, User user) {
        List<Bank> banks = user.getBanks();
        banks.add(bank);
        user.setBanks(banks);
    }

    /**
     * Удаление кредитного аккаунта у пользователя.
     *
     * @param creditAccount Кредитный аккаунт.
     * @param user         Пользователь, у которого удаляется аккаунт.
     */
    public void deleteCreditAccount(CreditAccount creditAccount, User user) {
        List<CreditAccount> creditAccounts = user.getCreditAccounts();
        creditAccounts.remove(creditAccount);
        user.setCreditAccounts(creditAccounts);
    }

    /**
     * Удаление платежного аккаунта у пользователя.
     *
     * @param paymentAccount Платежный аккаунт.
     * @param user           Пользователь, у которого удаляется аккаунт.
     */
    public void deletePaymentAccount(PaymentAccount paymentAccount, User user) {
        List<PaymentAccount> paymentAccounts = user.getPaymentAccounts();
        paymentAccounts.remove(paymentAccount);
        user.setPaymentAccounts(paymentAccounts);
    }

    /**
     * Удаление банка из списка банков пользователей.
     *
     * @param bank Банк, который нужно удалить.
     */
    public void deleteBank(Bank bank) {
        for(User curUser: users) {
            List<Bank> banks = curUser.getBanks();
            banks.remove(bank);
            curUser.setBanks(banks);
        }
    }
}