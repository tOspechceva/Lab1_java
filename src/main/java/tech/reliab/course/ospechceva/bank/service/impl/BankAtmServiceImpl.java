package tech.reliab.course.ospechceva.bank.service.impl;

import tech.reliab.course.ospechceva.bank.entity.Bank;
import tech.reliab.course.ospechceva.bank.entity.BankAtm;
import tech.reliab.course.ospechceva.bank.enums.BankAtmStatus;
import tech.reliab.course.ospechceva.bank.entity.BankOffice;
import tech.reliab.course.ospechceva.bank.entity.Employee;
import tech.reliab.course.ospechceva.bank.service.BankAtmService;
import tech.reliab.course.ospechceva.bank.service.BankService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class BankAtmServiceImpl implements BankAtmService {

    private static int bankAtmsCount = 0;

    private List<BankAtm> bankAtms = new ArrayList<>();

    private final BankService bankService;

    public BankAtmServiceImpl(BankService bankService) {
        this.bankService = bankService;
    }

    /**
     * Создание нового банкомата.
     *
     * @param name           Название банкомата.
     * @param address        Адрес банкомата.
     * @param bank           Банк, которому принадлежит банкомат.
     * @param location       Офис банка, где расположен банкомат.
     * @param employee       Сотрудник, ответственный за банкомат.
     * @param cashWithdrawal Возможность снятия наличных.
     * @param cashDeposit    Возможность пополнения счета.
     * @param maintenanceCost Стоимость обслуживания банкомата.
     * @return Созданный банкомат.
     */
    public BankAtm createBankAtm(String name, String address, Bank bank, BankOffice location, Employee employee,
                                 boolean cashWithdrawal, boolean cashDeposit, double maintenanceCost) {
        BankAtm bankAtm = new BankAtm(name, address, bank, location, employee,
                cashWithdrawal, cashDeposit, maintenanceCost);
        bankAtm.setId(bankAtmsCount++);
        bankAtm.setStatus(generateStatus());
        bankAtm.setAtmMoney(generateAtmMoney(bank));
        bankService.addAtm(bank);
        bankAtms.add(bankAtm);
        return bankAtm;
    }

    /**
     * Генерация случайного статуса банкомата.
     *
     * @return Случайный статус банкомата.
     */
    private BankAtmStatus generateStatus() {
        return BankAtmStatus.randomStatus();
    }

    /**
     * Генерация случайного количества денег в банкомате.
     *
     * @param bank Банк, которому принадлежит банкомат.
     * @return Случайное количество денег в банкомате.
     */
    private double generateAtmMoney(Bank bank) {
        return new Random().nextDouble(bank.getTotalMoney());
    }

    /**
     * Чтение банкомата по его идентификатору.
     *
     * @param id Идентификатор банкомата.
     * @return Банкомат, если он найден, иначе - пустой Optional.
     */
    public Optional<BankAtm> getBankAtmById(int id) {
        return bankAtms.stream()
                .filter(bankAtm -> bankAtm.getId() == id)
                .findFirst();
    }

    /**
     * Чтение всех банкоматов.
     *
     * @return Список всех банкоматов.
     */
    public List<BankAtm> getAllBankAtms() {
        return new ArrayList<>(bankAtms);
    }

    /**
     * Чтение всех банкоматов определенного банка.
     *
     * @param bank Банк, для которого нужно получить банкоматы.
     * @return Список банкоматов, принадлежащих указанному банку.
     */
    public List<BankAtm> getAllBankAtmsByBank(Bank bank) {
        return bankAtms.stream()
                .filter(bankAtm -> bankAtm.getBank().getId() == bank.getId())
                .collect(Collectors.toList());
    }

    /**
     * Обновление информации о банкомате по его идентификатору.
     *
     * @param id   Идентификатор банкомата.
     * @param name Новое название банкомата.
     */
    public void updateBankAtm(int id, String name) {
        BankAtm bankAtm = getBankAtmIfExists(id);
        bankAtm.setName(name);
    }

    /**
     * Удаление банкомата по его идентификатору.
     *
     * @param id Идентификатор банкомата.
     */
    public void deleteBankAtm(int id) {
        BankAtm bankAtm = getBankAtmIfExists(id);
        bankAtms.remove(bankAtm);
        Bank bank = bankAtm.getBank();
        bankService.removeAtm(bank);
    }

    /**
     * Получение банкомата по идентификатору, если он существует.
     *
     * @param id Идентификатор банкомата.
     * @return Банкомат, если он найден.
     * @throws RuntimeException Если банкомат не найден.
     */
    private BankAtm getBankAtmIfExists(int id) {
        return getBankAtmById(id).orElseThrow(() -> new NoSuchElementException("BankAtm was not found"));
    }
}