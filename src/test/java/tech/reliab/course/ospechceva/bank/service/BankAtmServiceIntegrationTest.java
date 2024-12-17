package tech.reliab.course.ospechceva.bank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ContextConfiguration;
import tech.reliab.course.ospechceva.bank.entity.*;
import tech.reliab.course.ospechceva.bank.model.BankAtmRequest;
import tech.reliab.course.ospechceva.bank.repository.*;
import tech.reliab.course.ospechceva.bank.service.impl.BankAtmServiceImpl;
import tech.reliab.course.ospechceva.bank.OspechcevaLabApplication;
import tech.reliab.course.ospechceva.bank.container.TestContainerConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = OspechcevaLabApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {TestContainerConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankAtmServiceIntegrationTest {

    @Autowired
    private BankAtmServiceImpl bankAtmService;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BankAtmRepository bankAtmRepository;

    @Autowired
    private BankOfficeRepository bankOfficeRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        // Удаляем все данные из таблиц перед каждым тестом
        bankAtmRepository.deleteAll();
        bankOfficeRepository.deleteAll();
        employeeRepository.deleteAll();
        userRepository.deleteAll();
        bankRepository.deleteAll();
    }

    @Test
    @Order(1)
    void testRequestBankInfo() {
        // Создаём банки для теста
        Bank bank = new Bank();
        bank.setName("Test Bank");
        bankRepository.save(bank);

        // Проверяем, что банк сохранён
        List<Bank> banks = bankRepository.findAll();
        assertEquals(1, banks.size(), "Ожидалось 1 банк в базе");

        Bank testBank = banks.get(0);

        // Проверяем, что для банка пока нет банкоматов и офисов
        assertTrue(bankAtmRepository.findAllByBankId(testBank.getId()).isEmpty(), "Банкоматов не должно быть");
        assertTrue(bankOfficeRepository.findAllByBankId(testBank.getId()).isEmpty(), "Офисов не должно быть");
    }

    @Test
    @Order(2)
    void testInitializeBankInfo() {
        // Создаём новый банк через сервис
        String bankName = "Тестовый банк";
        bankAtmService.createBankAtm(new BankAtmRequest(bankName, "bimbim", 1, 1, 1, true, true, 1));

        // Проверяем, что банк создан
        Bank bank = bankRepository.findByName(bankName)
                .orElseThrow(() -> new AssertionError("Банк не найден"));
        assertEquals(bankName, bank.getName(), "Название банка не совпадает");

        // Проверяем созданные сущности
        List<BankAtm> bankAtms = bankAtmRepository.findAllByBankId(bank.getId());
        List<BankOffice> bankOffices = bankOfficeRepository.findAllByBankId(bank.getId());
        List<Employee> employees = employeeRepository.findAllByBankId(bank.getId());
        List<User> users = userRepository.findAllByBanksId(bank.getId());

        assertEquals(1, bankAtms.size(), "Ожидалось 1 банкомат");
        assertEquals(1, bankOffices.size(), "Ожидался 1 офис");
        assertEquals(1, employees.size(), "Ожидался 1 сотрудник");
        assertTrue(users.size() >= 1, "Ожидалось как минимум 1 пользователь");
    }
}
