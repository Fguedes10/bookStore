package mindera.backendProject.bookStore.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static mindera.backendProject.bookStore.util.Messages.INVALID_FIRSTNAME;
import static mindera.backendProject.bookStore.util.Messages.INVALID_LASTNAME;

public record CustomerPatchDto(


        @Pattern(regexp = "[a-zA-Z]+", message = INVALID_FIRSTNAME)
        @Size(max = 15)
        String firstName,
        @Pattern(regexp = "[a-zA-Z]+", message = INVALID_LASTNAME)
        @Size(max = 15)
        String lastName,

        @Email
        @Size(max = 100)
        String email
) {
}
