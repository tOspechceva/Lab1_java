package tech.reliab.course.ospechceva.bank.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter
@Setter
public class User {

    private int id;
    private String fullName;
    private LocalDate birthDate;
    private String job;
    private double monthlyIncome;
    private int creditRating;
    private List<Bank> banks = new ArrayList<>();
    private List<CreditAccount> creditAccounts = new ArrayList<>();
    private List<PaymentAccount> paymentAccounts = new ArrayList<>();

    public User(String fullName, LocalDate birthDate, String job) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.job = job;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", birthDate=" + birthDate +
                ", job='" + job + '\'' +
                ", monthlyIncome=" + monthlyIncome +
                ", creditRating=" + creditRating +
                ", banks=" + banks.stream().map(Bank::getName).toList() +
                ", creditAccounts=" + creditAccounts.size() +
                ", paymentAccounts=" + paymentAccounts.size() +
                '}';
    }
}