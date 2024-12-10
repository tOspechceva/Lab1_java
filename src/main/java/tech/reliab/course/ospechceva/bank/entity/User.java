package tech.reliab.course.ospechceva.bank.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String job;

    @Column(nullable = false)
    private double monthlyIncome;

    @Column(nullable = false)
    private int creditRating;

    @ManyToMany
    @JoinTable(name = "user_banks", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "bank_id"))
    private List<Bank> banks;

    @OneToMany(mappedBy = "user")
    private List<CreditAccount> creditAccounts;

    @OneToMany(mappedBy = "user")
    private List<PaymentAccount> paymentAccounts;

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