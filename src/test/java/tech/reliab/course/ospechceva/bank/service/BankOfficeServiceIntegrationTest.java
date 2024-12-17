package tech.reliab.course.ospechceva.bank.service;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import tech.reliab.course.ospechceva.bank.OspechcevaLabApplication;
import tech.reliab.course.ospechceva.bank.entity.Bank;
import tech.reliab.course.ospechceva.bank.entity.BankOffice;
import tech.reliab.course.ospechceva.bank.model.BankOfficeRequest;
import tech.reliab.course.ospechceva.bank.repository.BankRepository;
import tech.reliab.course.ospechceva.bank.service.BankOfficeService;
import tech.reliab.course.ospechceva.bank.container.TestContainerConfig;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = OspechcevaLabApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {TestContainerConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BankOfficeServiceIntegrationTest {

    @Autowired
    private BankOfficeService bankOfficeService;

    @Autowired
    private BankRepository bankRepository;

    private Bank testBank;

    @BeforeEach
    void setUp() {
        bankRepository.deleteAll();
        testBank = bankRepository.save(new Bank("Test Bank", "Test Bank Address", 50000, 50, 20, 5));
    }

    @Test
    void shouldCreateBankOffice() {
        BankOfficeRequest request = new BankOfficeRequest();
        request.setName("Main Office");
        request.setAddress("Main Street 1");
        request.setCanPlaceAtm(true);
        request.setCanIssueLoan(true);
        request.setCashWithdrawal(true);
        request.setCashDeposit(false);
        request.setRentCost(BigDecimal.valueOf(1000));
        request.setBankId(testBank.getId());

        BankOffice createdOffice = bankOfficeService.createBankOffice(request);

        assertAll(
            "Проверка параметров созданного офиса",
            () -> assertNotNull(createdOffice.getId(), "ID офиса должен быть не null"),
            () -> assertEquals("Main Office", createdOffice.getName(), "Имя офиса должно совпадать"),
            () -> assertEquals("Main Street 1", createdOffice.getAddress(), "Адрес офиса должен совпадать"),
            () -> assertTrue(createdOffice.isCanPlaceAtm(), "Офис должен поддерживать размещение банкоматов"),
            () -> assertEquals(testBank.getId(), createdOffice.getBank().getId(), "Банк офиса должен совпадать")
        );
    }

    @Test
    void shouldUpdateBankOfficeName() {
        BankOfficeRequest request = new BankOfficeRequest();
        request.setName("Branch Office");
        request.setAddress("Branch Street 5");
        request.setCanPlaceAtm(false);
        request.setCanIssueLoan(false);
        request.setCashWithdrawal(false);
        request.setCashDeposit(true);
        request.setRentCost(BigDecimal.valueOf(500));
        request.setBankId(testBank.getId());

        BankOffice office = bankOfficeService.createBankOffice(request);

        BankOffice updatedOffice = bankOfficeService.updateBankOffice(office.getId(), "New Branch Office");

        assertEquals("New Branch Office", updatedOffice.getName(), "Имя офиса должно быть обновлено");
    }

    @Test
    void shouldReturnBankOfficeById() {
        BankOfficeRequest request = new BankOfficeRequest();
        request.setName("Info Office");
        request.setAddress("Info Street 10");
        request.setCanPlaceAtm(true);
        request.setCanIssueLoan(false);
        request.setCashWithdrawal(true);
        request.setCashDeposit(true);
        request.setRentCost(BigDecimal.valueOf(2000));
        request.setBankId(testBank.getId());

        BankOffice office = bankOfficeService.createBankOffice(request);
        BankOffice found = bankOfficeService.getBankDtoOfficeById(office.getId());

        assertNotNull(found, "Офис должен быть найден");
        assertEquals("Info Office", found.getName(), "Имя офиса должно совпадать");
    }

    @Test
    void shouldReturnAllBankOffices() {
        BankOfficeRequest request1 = new BankOfficeRequest();
        request1.setName("Office A");
        request1.setAddress("Address A");
        request1.setCanPlaceAtm(true);
        request1.setCanIssueLoan(true);
        request1.setCashWithdrawal(true);
        request1.setCashDeposit(true);
        request1.setRentCost(BigDecimal.valueOf(1500));
        request1.setBankId(testBank.getId());
        bankOfficeService.createBankOffice(request1);

        BankOfficeRequest request2 = new BankOfficeRequest();
        request2.setName("Office B");
        request2.setAddress("Address B");
        request2.setCanPlaceAtm(false);
        request2.setCanIssueLoan(false);
        request2.setCashWithdrawal(false);
        request2.setCashDeposit(false);
        request2.setRentCost(BigDecimal.valueOf(3000));
        request2.setBankId(testBank.getId());
        bankOfficeService.createBankOffice(request2);

        List<BankOffice> offices = bankOfficeService.getAllBankOffices();

        assertEquals(2, offices.size(), "Должно вернуться 2 офиса");
    }

    @Test
    void shouldDeleteBankOffice() {
        BankOfficeRequest request = new BankOfficeRequest();
        request.setName("Office to Delete");
        request.setAddress("Delete Street 1");
        request.setCanPlaceAtm(false);
        request.setCanIssueLoan(false);
        request.setCashWithdrawal(false);
        request.setCashDeposit(false);
        request.setRentCost(BigDecimal.valueOf(700));
        request.setBankId(testBank.getId());

        BankOffice office = bankOfficeService.createBankOffice(request);

        bankOfficeService.deleteBankOffice(office.getId());

        Assertions.assertThrows(
            RuntimeException.class,
            () -> bankOfficeService.getBankDtoOfficeById(office.getId()),
            "После удаления офиса должно выбрасываться исключение при попытке найти его"
        );
    }
}
