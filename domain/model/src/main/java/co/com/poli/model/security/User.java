package co.com.poli.model.security;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String email;
    private String uuid;
    private String name;
    private String lastName;
    private String password;
    private Boolean status;
    private String roles;
    
}