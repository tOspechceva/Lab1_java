package tech.reliab.course.ospechceva.bank.entity;

import lombok.*;

@Getter
@Setter
public class PaymentAccount {

    private int id;
    private User user;
    private Bank bank;
    private double balance = 0;

    public PaymentAccount(User user, Bank bank) {
        this.user = user;
        this.bank = bank;
        this.balance = 0;
    }

    @Override
    public String toString() {
        return "PaymentAccount{" +
                "id=" + id +
                ", user=" + user.getFullName() +
                ", bank=" + bank.getName() +
                ", balance=" + balance +
                '}';
    }
}