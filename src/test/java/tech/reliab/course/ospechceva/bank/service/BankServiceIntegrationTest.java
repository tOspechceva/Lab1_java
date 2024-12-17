package tech.reliab.course.ospechceva.bank.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import tech.reliab.course.ospechceva.bank.OspechcevaLabApplication;
import tech.reliab.course.ospechceva.bank.container.TestContainerConfig;
import tech.reliab.course.ospechceva.bank.entity.Bank;

@SpringBootTest(classes = OspechcevaLabApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {TestContainerConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BankServiceIntegrationTest {

    @Autowired
    private BankService bankService;

    @Test
    void testCreateBank() {
        Bank createdBank = bankService.createBank("Test Bank");
        Assertions.assertNotNull(createdBank.getId(), "ID банка должен быть не null после сохранения");
        Assertions.assertEquals("Test Bank", createdBank.getName(), "Имя банка должно совпадать с заданным");
    }

    @Test
    void testUpdateBank() {
        Bank createdBank = bankService.createBank("Old Name");
        Bank updatedBank = bankService.updateBank(createdBank.getId(), "New Name");
        Assertions.assertEquals("New Name", updatedBank.getName(), "Название банка должно быть обновлено");
    }

    @Test
    void testGetBankById() {
        Bank createdBank = bankService.createBank("Unique Bank");
        Bank foundBank = bankService.getBankDtoById(createdBank.getId());
        Assertions.assertEquals("Unique Bank", foundBank.getName(), "Полученный по ID банк должен совпадать");
    }

    @Test
    void testGetAllBanks() {
        bankService.createBank("Bank A");
        bankService.createBank("Bank B");
        List<Bank> banks = bankService.getAllBanks();
        Assertions.assertEquals(2, banks.size(), "Должно вернуться 2 банка");
    }

    @Test
    void testDeleteBank() {
        Bank createdBank = bankService.createBank("Bank to Delete");
        bankService.deleteBank(createdBank.getId());
        Assertions.assertThrows(
                RuntimeException.class,
                () -> bankService.getBankDtoById(createdBank.getId()),
                "После удаления при попытке получить банк по ID должно быть исключение"
        );
    }
}