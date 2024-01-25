package mindera.backendProject.bookStore.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerPatchDto(


        @Pattern(regexp = "[a-zA-Z]+", message = "Please input a valid firstName")
        @Size(max = 15)
        String firstName,
        @Pattern(regexp = "[a-zA-Z]+", message = "Please input a valid lastName")
        @Size(max = 15)
        String lastName,

        @Email
        @Size(max = 100)
        String email
) {
}
