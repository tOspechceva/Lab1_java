package tech.reliab.course.ospechceva.bank.entity;

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

    public int getId() {
        return id;
    }

    public Bank getBank() {
        return bank;
    }

    public User getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
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