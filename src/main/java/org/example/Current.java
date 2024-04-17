package org.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "current", schema = "main", catalog = "")
public class Current {
    private int current_id;
    private double balance;
    private String open_date;
    private int number;
    private int employee_id;
    private int client_id;
    @Id
    @Column(name = "current_id")
    public int getCurrent_id() {
        return current_id;
    }
    public void setCurrent_id(int current_id) {
        this.current_id = current_id;
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
    @Column(name = "number")
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
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
