package tech.reliab.course.ospechceva.bank.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.reliab.course.ospechceva.bank.entity.Bank;
import tech.reliab.course.ospechceva.bank.entity.BankAtm;
import tech.reliab.course.ospechceva.bank.enums.BankAtmStatus;
import tech.reliab.course.ospechceva.bank.model.BankAtmRequest;
import tech.reliab.course.ospechceva.bank.repository.BankAtmRepository;
import tech.reliab.course.ospechceva.bank.service.BankAtmService;
import tech.reliab.course.ospechceva.bank.service.BankOfficeService;
import tech.reliab.course.ospechceva.bank.service.BankService;
import tech.reliab.course.ospechceva.bank.service.EmployeeService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BankAtmServiceImpl implements BankAtmService {

    private final BankAtmRepository bankAtmRepository;
    private final BankService bankService;
    private final BankOfficeService bankOfficeService;
    private final EmployeeService employeeService;


    /**
     * Создание нового банкомата.
     *
     * @param bankAtmRequest данные о банкомате
     * @return Созданный банкомат.
     */
    public BankAtm createBankAtm(BankAtmRequest bankAtmRequest) {
        Bank bank = bankService.getBankById(bankAtmRequest.getBankId());
        BankAtm bankAtm = new BankAtm(bankAtmRequest.getName(), bankAtmRequest.getAddress(), bank,
                                      bankOfficeService.getBankOfficeById(bankAtmRequest.getLocationId()),
                                      employeeService.getEmployeeById(bankAtmRequest.getEmployeeId()),
                                      bankAtmRequest.isCashWithdrawal(), bankAtmRequest.isCashDeposit(), bankAtmRequest.getMaintenanceCost());
        bankAtm.setStatus(BankAtmStatus.randomStatus());
        bankAtm.setAtmMoney(generateAtmMoney(bank));
        return bankAtmRepository.save(bankAtm);
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
     * @return Банкомат, если он найден
     * @throws NoSuchElementException Если банкомат не найден.
     */
    public BankAtm getBankAtmById(int id) {
        return bankAtmRepository.findById(id).orElseThrow(() -> new NoSuchElementException("BankAtm was not found"));
    }

    public BankAtm getBankAtmDtoById(int id) {
        return getBankAtmById(id);
    }

    /**
     * Чтение всех банкоматов.
     *
     * @return Список всех банкоматов.
     */
    public List<BankAtm> getAllBankAtms() {
        return bankAtmRepository.findAll();
    }

    /**
     * Чтение всех банкоматов определенного банка.
     *
     * @param bankId id банка, для которого нужно получить банкоматы.
     * @return Список банкоматов, принадлежащих указанному банку.
     */
    public List<BankAtm> getAllBankAtmsByBankId(int bankId) {
        return bankAtmRepository.findAllByBankId(bankId);
    }

    /**
     * Обновление информации о банкомате по его идентификатору.
     *
     * @param id   Идентификатор банкомата.
     * @param name Новое название банкомата.
     */
    public BankAtm updateBankAtm(int id, String name) {
        BankAtm bankAtm = getBankAtmById(id);
        bankAtm.setName(name);
        return bankAtmRepository.save(bankAtm);
    }

    /**
     * Удаление банкомата по его идентификатору.
     *
     * @param id Идентификатор банкомата.
     */
    public void deleteBankAtm(int id) {
        bankAtmRepository.deleteById(id);
    }
}
