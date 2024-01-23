package mindera.backendProject.bookStore.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerCreateDto(

        @NotBlank(message = "Please input First Name")
        @Size(max = 15, message = "You exceed the max number of characters")
        String firstName,
        @NotBlank(message = "Please input Last Name")
        @Size(max = 15, message = "You exceed the max number of characters")
        @Pattern(regexp = "[a-zA-Z]",message = "Please insert a valid name")
        String lastName,
        @NotBlank(message = "Please input an email")
        @Email
        @Size(max = 100, message = "You exceed the max number of characters")
        String email,
        @NotBlank(message = "Please input a nif")
        @Size(min = 100000000, max = 999999999, message = "Please input a valid number")
        Long nif,
        @NotBlank(message = "Please input a favorite genre")
        String favoriteGenre
) {
}
