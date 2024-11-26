package tech.reliab.course.ospechceva.bank.service.impl;

import tech.reliab.course.ospechceva.bank.entity.Bank;
import tech.reliab.course.ospechceva.bank.service.BankService;
import tech.reliab.course.ospechceva.bank.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

public class BankServiceImpl implements BankService {

    private static final int RATING_BOUND = 101;
    private static final int TOTAL_MONEY_BOUND = 1000001;
    private static final int MAX_RATE = 20;
    private static final double DIVIDER = 10.0;
    private static int banksCount = 0;

    private final UserService userService;

    private List<Bank> banks = new ArrayList<>();

    public BankServiceImpl(UserService userService) {
        this.userService = userService;
    }

    /**
     * Создание нового банка.
     *
     * @param bankName Название банка.
     * @return Созданный банк.
     */
    public Bank createBank(String bankName) {
        Bank bank = new Bank(bankName);
        bank.setId(banksCount++);
        bank.setRating(generateRating());
        bank.setTotalMoney(generateTotalMoney());
        bank.setInterestRate(calculateInterestRate(bank.getRating()));
        banks.add(bank);
        return bank;
    }

    /**
     * Генерация случайного рейтинга банка.
     *
     * @return Случайный рейтинг банка.
     */
    private int generateRating() {
        return new Random().nextInt(RATING_BOUND);
    }

    /**
     * Генерация случайного количества денег в банке.
     *
     * @return Случайное количество денег в банке.
     */
    private double generateTotalMoney() {
        return new Random().nextInt(TOTAL_MONEY_BOUND);
    }

    /**
     * Вычисление процентной ставки по кредитам.
     *
     * @param rating Рейтинг банка.
     * @return Процентная ставка.
     */
    private double calculateInterestRate(int rating) {
        return MAX_RATE - (rating / DIVIDER);
    }

    /**
     * Чтение банка по его идентификатору.
     *
     * @param id Идентификатор банка.
     * @return Банк, если он найден, иначе - пустой Optional.
     */
    public Optional<Bank> getBankById(int id) {
        return banks.stream()
                .filter(bank -> bank.getId() == id)
                .findFirst();
    }

    /**
     * Чтение всех банков.
     *
     * @return Список всех банков.
     */
    public List<Bank> getAllBanks() {
        return new ArrayList<>(banks);
    }

    /**
     * Обновление информации о банке по его идентификатору.
     *
     * @param id   Идентификатор банка.
     * @param name Новое название банка.
     */
    public void updateBank(int id, String name) {
        Bank bank = getBankIfExists(id);
        bank.setName(name);
    }

    /**
     * Удаление банка по его идентификатору.
     *
     * @param id Идентификатор банка.
     */
    public void deleteBank(int id) {
        Bank bank = getBankIfExists(id);
        banks.remove(bank);
        userService.deleteBank(bank);
    }

    /**
     * Получение банка по идентификатору, если он существует.
     *
     * @param id Идентификатор банка.
     * @return Банк, если он найден.
     * @throws NoSuchElementException Если банк не найден.
     */
    public Bank getBankIfExists(int id) {
        return getBankById(id).orElseThrow(() -> new NoSuchElementException("Bank was not found"));
    }

    /**
     * Увеличение количества офисов в банке.
     *
     * @param bank Банк, для которого нужно увеличить количество офисов.
     */
    public void addOffice(Bank bank) {
        bank.setOfficeCount(bank.getOfficeCount() + 1);
    }

    /**
     * Увеличение количества банкоматов в банке.
     *
     * @param bank Банк, для которого нужно увеличить количество банкоматов.
     */
    public void addAtm(Bank bank) {
        bank.setAtmCount(bank.getAtmCount() + 1);
    }

    /**
     * Увеличение количества сотрудников в банке.
     *
     * @param bank Банк, для которого нужно увеличить количество сотрудников.
     */
    public void addEmployee(Bank bank) {
        bank.setEmployeeCount(bank.getEmployeeCount() + 1);
    }

    /**
     * Увеличение количества клиентов в банке.
     *
     * @param bank Банк, для которого нужно увеличить количество клиентов.
     */
    public void addClient(Bank bank) {
        bank.setClientCount(bank.getClientCount() + 1);
    }

    /**
     * Уменьшение количества офисов в банке.
     *
     * @param bank Банк, для которого нужно уменьшить количество офисов.
     */
    public void removeOffice(Bank bank) {
        bank.setOfficeCount(bank.getOfficeCount() - 1);
    }

    /**
     * Уменьшение количества банкоматов в банке.
     *
     * @param bank Банк, для которого нужно уменьшить количество банкоматов.
     */
    public void removeAtm(Bank bank) {
        bank.setAtmCount(bank.getAtmCount() - 1);
    }

    /**
     * Уменьшение количества сотрудников в банке.
     *
     * @param bank Банк, для которого нужно уменьшить количество сотрудников.
     */
    public void removeEmployee(Bank bank) {
        bank.setEmployeeCount(bank.getEmployeeCount() - 1);
    }

    /**
     * Уменьшение количества клиентов в банке.
     *
     * @param bank Банк, для которого нужно уменьшить количество клиентов.
     */
    public void removeClient(Bank bank) {
        bank.setClientCount(bank.getClientCount() - 1);
    }
}