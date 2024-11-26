package tech.reliab.course.ospechceva.bank.enums;

import java.util.Random;

public enum BankOfficeStatus {

    WORKING,
    NOT_WORKING;

    private static final Random RANDOM = new Random();

    public static BankOfficeStatus randomStatus()  {
        BankOfficeStatus[] statuses = values();
        return statuses[RANDOM.nextInt(statuses.length)];
    }
}
