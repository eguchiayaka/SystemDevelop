package com.example.demo.form;

	import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

	@Data
	public class LoginEditForm implements Serializable {
		 
		private Long id;
		
		@NotNull
		private Long store_id;
		
		@NotBlank
		private String first_name;
		
		@NotBlank
		private String last_name;
		
		@NotBlank
		@Email
		private String email;
		
		@NotNull
		private Long position_id;
		
		
		@NotNull
		private Long authority_id;
		
		@NotBlank
		@Size(min = 10, max = 11)
		private String phone;
		
		
		@NotBlank
		private String password;
		
		private LocalDateTime createdAt;
		private LocalDateTime updatedAt;

	}


