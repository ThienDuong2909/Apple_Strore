package com.applestore.applestore.Entities;


import jakarta.persistence.*;

@Entity
@Table(name = "Customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customer_id;

    @Column(unique = true, nullable = false)
    private int user_id;

    @Column(columnDefinition = "nvarchar(max)", nullable = false)
    private String address_line;

    @Column(columnDefinition = "nvarchar(255)", nullable = false)
    private String country;

    @Column(columnDefinition = "nvarchar(255)", nullable = false)
    private String city;

    @Column(unique = true, nullable = false)
    private String phone;

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAddress_line() {
        return address_line;
    }

    public void setAddress_line(String address_line) {
        this.address_line = address_line;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
