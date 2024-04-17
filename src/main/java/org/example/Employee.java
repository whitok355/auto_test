package org.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee", schema = "main", catalog = "")
public class Employee {
    private int employee_id;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String portion;

    @Id
    @Column(name = "employee_id")
    public int getEmployee_id() {
        return employee_id;
    }
    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
    @Column(name = "first_name")
    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    @Column(name = "last_name")
    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    @Column(name = "phone_number")
    public String getPhone_number() {
        return phone_number;
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    @Column(name = "portion")
    public String getPortion() {
        return portion;
    }
    public void setPortion(String portion) {
        this.portion = portion;
    }
}
