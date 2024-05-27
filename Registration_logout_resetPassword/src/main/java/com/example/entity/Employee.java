package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "emp_details")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name ="emp_name")
	private String name;
	
	@Column(name = "emp_email")
	@Email
	private String email;
	
	@Column(name = "emp_age")
	private int age;
	
	@Column(name = "emp_address")
	private String address;

	public Employee() {
		
	}

	public Employee(Long id, String name, @Email String email, int age, String address) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.age = age;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", age=" + age + ", address=" + address
				+ "]";
	}
	
	
	
	

}
