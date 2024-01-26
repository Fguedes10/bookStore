package mindera.backendProject.bookStore.dto.customer;

import jakarta.validation.constraints.*;

import static mindera.backendProject.bookStore.util.Messages.*;

public record CustomerCreateDto(

        @NotBlank(message = INVALID_USERNAME)
        @Size(max = 20, message = MAX_CHAR_SIZE)
        @Pattern(regexp = "[a-zA-Z_0-9!?#$]+",message = INVALID_USERNAME)
        String username,

        @NotBlank(message = INVALID_FIRSTNAME)
        @Size(max = 15, message = MAX_CHAR_SIZE)
        @Pattern(regexp = "[a-zA-Z]+",message = INVALID_FIRSTNAME)
        String firstName,
        @NotBlank(message = INVALID_LASTNAME)
        @Size(max = 15, message = MAX_CHAR_SIZE)
        @Pattern(regexp = "[a-zA-Z]+",message = INVALID_LASTNAME)
        String lastName,
        @NotBlank(message = INVALID_EMAIL)
        @Email
        @Size(max = 100, message = MAX_CHAR_SIZE)
        String email,
        @NotNull(message = INVALID_NIF)
       // @Size(min = 100000000, max = 999999999, message = INVALID_NIF)
        Long nif

) {
}
