package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "admins")
public class Admin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "store_id", nullable = false)
	private Long store_id;
	
	@Column(name = "first_name", nullable = false)
	private String first_name;

	@Column(name = "last_name", nullable = false)
	private String last_name;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "position_id", nullable = false)
	private Long position_id;

	@Column(name = "authority_id", nullable = false)
	private Long authority_id;


	@Column(name = "phone", nullable = false)
	private String phone;
	
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
}
