package co.com.poli.model.security;

public record SignUpDTO(String name,
                        String lastName,
                        String email,
                        String password,
                        String role) {}
