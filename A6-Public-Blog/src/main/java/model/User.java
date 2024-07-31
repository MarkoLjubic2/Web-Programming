package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    @NotNull
    @NotEmpty(message = "Username is required")
    private String username;
    @NotNull
    @NotEmpty(message = "Password is required")
    private String password;
}
