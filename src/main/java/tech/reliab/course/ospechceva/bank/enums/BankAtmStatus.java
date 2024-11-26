package tech.reliab.course.ospechceva.bank.enums;

import java.util.Random;

public enum BankAtmStatus {

    WORKING,
    NOT_WORKING,
    NO_MONEY;

    private static final Random RANDOM = new Random();

    public static BankAtmStatus randomStatus()  {
        BankAtmStatus[] statuses = values();
        return statuses[RANDOM.nextInt(statuses.length)];
    }
}