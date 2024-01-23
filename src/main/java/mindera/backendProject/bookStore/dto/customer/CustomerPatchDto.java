package mindera.backendProject.bookStore.dto.customer;

import jakarta.validation.constraints.Size;

public record CustomerPatchDto(


        @Size(max = 15)
        String firstName,
        @Size(max = 15)
        String lastName,

        @Size(max = 100)
        String email
) {
}
