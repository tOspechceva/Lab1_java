package tech.reliab.course.ospechceva.bank.service.impl;

import tech.reliab.course.ospechceva.bank.entity.Bank;
import tech.reliab.course.ospechceva.bank.entity.BankOffice;
import tech.reliab.course.ospechceva.bank.enums.BankOfficeStatus;
import tech.reliab.course.ospechceva.bank.service.BankOfficeService;
import tech.reliab.course.ospechceva.bank.service.BankService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

public class BankOfficeServiceImpl implements BankOfficeService {

    private static int bankOfficesCount = 0;

    private List<BankOffice> bankOffices = new ArrayList<>();

    private final BankService bankService;

    public BankOfficeServiceImpl(BankService bankService) {
        this.bankService = bankService;
    }

    /**
     * Создание нового офиса банка.
     *
     * @param name           Название офиса.
     * @param address        Адрес офиса.
     * @param canPlaceAtm   Возможность размещения банкомата в офисе.
     * @param canIssueLoan Возможность выдачи кредитов в офисе.
     * @param cashWithdrawal Возможность снятия наличных в офисе.
     * @param cashDeposit    Возможность пополнения счета в офисе.
     * @param rentCost       Стоимость аренды офиса.
     * @param bank           Банк, которому принадлежит офис.
     * @return Созданный офис банка.
     */
    public BankOffice createBankOffice(String name, String address, boolean canPlaceAtm,
                                       boolean canIssueLoan, boolean cashWithdrawal, boolean cashDeposit,
                                       double rentCost, Bank bank) {
        BankOffice bankOffice = new BankOffice(name, address, canPlaceAtm, canIssueLoan,
                cashWithdrawal, cashDeposit, rentCost, bank);
        bankOffice.setId(bankOfficesCount++);
        bankOffice.setStatus(generateStatus());
        bankOffice.setOfficeMoney(generateOfficeMoney(bank));
        bankOffices.add(bankOffice);
        bankService.addOffice(bank);
        return bankOffice;
    }

    /**
     * Генерация случайного статуса офиса банка.
     *
     * @return Случайный статус офиса банка.
     */
    private BankOfficeStatus generateStatus() {
        return BankOfficeStatus.randomStatus();
    }

    /**
     * Генерация случайного количества денег в офисе банка.
     *
     * @param bank Банк, которому принадлежит офис.
     * @return Случайное количество денег в офисе банка.
     */
    private double generateOfficeMoney(Bank bank) {
        return new Random().nextDouble(bank.getTotalMoney());
    }

    /**
     * Поиск офиса банка по его идентификатору.
     *
     * @param id Идентификатор офиса банка.
     * @return Офис банка, если он найден, иначе - пустой Optional.
     */
    public Optional<BankOffice> getBankOfficeById(int id) {
        return bankOffices.stream()
                .filter(bankOffice -> bankOffice.getId() == id)
                .findFirst();
    }

    /**
     * Чтение всех офисов банка.
     *
     * @return Список всех офисов банка.
     */
    public List<BankOffice> getAllBankOffices() {
        return new ArrayList<>(bankOffices);
    }

    /**
     * Обновление информации об офисе банка по его идентификатору.
     *
     * @param id   Идентификатор офиса банка.
     * @param name Новое название офиса банка.
     */
    public void updateBankOffice(int id, String name) {
        BankOffice bankOffice = getBankOfficeIfExists(id);
        bankOffice.setName(name);
    }

    /**
     * Удаление офиса банка по его идентификатору и идентификатору банка.
     *
     * @param officeId Идентификатор офиса банка.
     * @param bankId   Идентификатор банка, которому принадлежит офис.
     */
    public void deleteBankAtm(int officeId, int bankId) {
        BankOffice bankOffice = getBankOfficeIfExists(officeId);
        bankOffices.remove(bankOffice);
        Bank bank = bankService.getBankIfExists(bankId);
        bankService.removeOffice(bank);
    }

    /**
     * Получение офиса банка по его идентификатору, если он существует.
     *
     * @param id Идентификатор офиса банка.
     * @return Офис банка, если он найден.
     * @throws NoSuchElementException Если офис банка не найден.
     */
    private BankOffice getBankOfficeIfExists(int id) {
        return getBankOfficeById(id).orElseThrow(() -> new NoSuchElementException("BankOffice was not found"));
    }
}