package mindera.backendProject.bookStore.dtos.customer;

import jakarta.validation.constraints.Max;
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
