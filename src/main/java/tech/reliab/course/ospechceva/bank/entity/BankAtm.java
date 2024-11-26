package tech.reliab.course.ospechceva.bank.entity;
import tech.reliab.course.ospechceva.bank.enums.BankAtmStatus;
public class BankAtm {

    private int id;
    private String name;
    private String address;
    private BankAtmStatus status;
    private Bank bank;
    private BankOffice location;
    private Employee employee;
    private boolean cashWithdrawal;
    private boolean cashDeposit;
    private double atmMoney;
    private double maintenanceCost;

    public BankAtm(String name, String address, Bank bank, BankOffice location, Employee employee, boolean cashWithdrawal, boolean cashDeposit, double maintenanceCost) {
        this.name = name;
        this.address = address;
        this.bank = bank;
        this.location = location;
        this.employee = employee;
        this.cashWithdrawal = cashWithdrawal;
        this.cashDeposit = cashDeposit;
        this.maintenanceCost = maintenanceCost;
    }

    public int getId() {
        return id;
    }

    public Bank getBank() {
        return bank;
    }

    public void setStatus(BankAtmStatus status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAtmMoney(double atmMoney) {
        this.atmMoney = atmMoney;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BankAtm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", bank=" + bank.getName() +
                ", location=" + location.getName() +
                ", employee=" + (employee != null ? employee.getFullName() : "None") +
                ", cashWithdrawal=" + cashWithdrawal +
                ", cashDeposit=" + cashDeposit +
                ", atmMoney=" + atmMoney +
                ", maintenanceCost=" + maintenanceCost +
                '}';
    }
}