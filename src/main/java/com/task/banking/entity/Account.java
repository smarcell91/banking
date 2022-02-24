package com.task.banking.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double balance = 0;

    public double deposit(double amount) {
        this.balance += amount;
        return this.balance;
    }

    public double withdraw(double amount) {
        this.balance -= amount;
        return this.balance;
    }
}
