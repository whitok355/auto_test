package org.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="debit", schema = "main", catalog = "")
public class Debit {
    private int debit_id;
    private String type_name;
    private double balance;
    private String open_date;
    private int percent;
    private String number;
    private String status;
    private int employee_id;
    private int client_id;


    @Id
    @Column(name ="credit_id")
    public int getCredit_id() {
        return debit_id;
    }
    public void setCredit_id(int credit_id) {
        this.debit_id = credit_id;
    }
    @Column(name = "balance")
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    @Column(name = "open_date")
    public String getOpen_date() {
        return open_date;
    }
    public void setOpen_date(String open_date) {
        this.open_date = open_date;
    }
    @Column(name = "percent")
    public int getClose_date() {
        return percent;
    }
    public void setClose_date(int percent) {
        this.percent = percent;
    }
    @Column(name = "number")
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    @Column(name = "status")
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Column(name = "employee_id")
    public int getEmployee_id() {
        return employee_id;
    }
    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
    @Column(name = "client_id")
    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }
}
