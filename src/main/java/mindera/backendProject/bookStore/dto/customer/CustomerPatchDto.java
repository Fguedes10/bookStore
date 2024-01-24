package mindera.backendProject.bookStore.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerPatchDto(


        @NotBlank(message = "Please input First Name")
        @Size(max = 15)
        String firstName,
        @NotBlank(message = "Please input Last Name")
        @Size(max = 15)
        String lastName,

        @NotBlank(message = "Please input an Email")
        @Email
        @Size(max = 100)
        String email
) {
}
