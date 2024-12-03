package tech.reliab.course.ospechceva.bank.entity;
import tech.reliab.course.ospechceva.bank.enums.BankOfficeStatus;

import lombok.*;

@Getter
@Setter
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

    public BankOffice(String name, String address, boolean canPlaceAtm, boolean canIssueLoan, boolean cashWithdrawal,
            boolean cashDeposit, double rentCost, Bank bank) {
        this.name = name;
        this.address = address;
        this.canPlaceAtm = canPlaceAtm;
        this.canIssueLoan = canIssueLoan;
        this.cashWithdrawal = cashWithdrawal;
        this.cashDeposit = cashDeposit;
        this.rentCost = rentCost;
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