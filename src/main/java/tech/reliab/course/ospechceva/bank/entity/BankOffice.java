package tech.reliab.course.ospechceva.bank.entity;
import tech.reliab.course.ospechceva.bank.enums.BankOfficeStatus;
public class BankOffice {

    private int id;
    private String name;
    private String address;
    private BankOfficeStatus status;
    private boolean canPlaceAtm;
    private int atmCount = 0;
    private boolean canIssueLoan;
    private boolean cashWithdrawal;
    private boolean cashDeposit;
    private double officeMoney;
    private double rentCost;

    public BankOffice(String name, String address, boolean canPlaceAtm, boolean canIssueLoan, boolean cashWithdrawal, boolean cashDeposit, double rentCost, Bank bank) {
        this.name = name;
        this.address = address;
        this.canPlaceAtm = canPlaceAtm;
        this.canIssueLoan = canIssueLoan;
        this.cashWithdrawal = cashWithdrawal;
        this.cashDeposit = cashDeposit;
        this.rentCost = rentCost;
    }

    public void setStatus(BankOfficeStatus status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOfficeMoney(double officeMoney) {
        this.officeMoney = officeMoney;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "BankOffice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", canPlaceAtm=" + canPlaceAtm +
                ", atmCount=" + atmCount +
                ", canIssueLoan=" + canIssueLoan +
                ", cashWithdrawal=" + cashWithdrawal +
                ", cashDeposit=" + cashDeposit +
                ", officeMoney=" + officeMoney +
                ", rentCost=" + rentCost +
                '}';
    }
}