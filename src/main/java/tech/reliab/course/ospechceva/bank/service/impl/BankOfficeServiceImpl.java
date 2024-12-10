package tech.reliab.course.ospechceva.bank.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.reliab.course.ospechceva.bank.entity.Bank;
import tech.reliab.course.ospechceva.bank.entity.BankOffice;
import tech.reliab.course.ospechceva.bank.enums.BankOfficeStatus;
import tech.reliab.course.ospechceva.bank.model.BankOfficeRequest;
import tech.reliab.course.ospechceva.bank.repository.BankOfficeRepository;
import tech.reliab.course.ospechceva.bank.service.BankOfficeService;
import tech.reliab.course.ospechceva.bank.service.BankService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BankOfficeServiceImpl implements BankOfficeService {

    private final BankOfficeRepository bankOfficeRepository;
    private final BankService bankService;

    /**
     * Создание нового офиса банка.
     *
     * @param bankOfficeRequest содержит данные про офис
     * @return Созданный офис банка.
     */
    public BankOffice createBankOffice(BankOfficeRequest bankOfficeRequest) {
        Bank bank = bankService.getBankById(bankOfficeRequest.getBankId());
        BankOffice bankOffice = new BankOffice(bankOfficeRequest.getName(), bankOfficeRequest.getAddress(),
                                               bankOfficeRequest.isCanPlaceAtm(), bankOfficeRequest.isCanIssueLoan(),
                                               bankOfficeRequest.isCashWithdrawal(), bankOfficeRequest.isCashDeposit(),
                                               bankOfficeRequest.getRentCost(), bank);
        bankOffice.setStatus(BankOfficeStatus.randomStatus());
        bankOffice.setOfficeMoney(generateOfficeMoney(bank));
        return bankOfficeRepository.save(bankOffice);
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
     * @return Офис банка, если он найден
     * @throws NoSuchElementException Если офис не найден.
     */
    public BankOffice getBankOfficeById(int id) {
        return bankOfficeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("BankOffice was not found"));
    }

    public BankOffice getBankDtoOfficeById(int id) {
        return getBankOfficeById(id);
    }

    /**
     * Чтение всех офисов банка.
     *
     * @return Список всех офисов банка.
     */
    public List<BankOffice> getAllBankOffices() {
        return bankOfficeRepository.findAll();
    }

    /**
     * Обновление информации об офисе банка по его идентификатору.
     *
     * @param id   Идентификатор офиса банка.
     * @param name Новое название офиса банка.
     */
    public BankOffice updateBankOffice(int id, String name) {
        BankOffice bankOffice = getBankOfficeById(id);
        bankOffice.setName(name);
        return bankOfficeRepository.save(bankOffice);
    }

    /**
     * Удаление офиса банка по его идентификатору
     *
     * @param id Идентификатор офиса банка.
     */
    public void deleteBankAtm(int id) {
        bankOfficeRepository.deleteById(id);
    }
}
