package com.example.backendspringboot.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString
@Table(name="employees")
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false, length = 50) private String userId;

    @Setter @Column(nullable = false)  private String password;

    protected Employee(){}

    public Employee(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public static Employee of(String userId, String userPassword){
        return new Employee(userId, userPassword);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return id.equals(employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
