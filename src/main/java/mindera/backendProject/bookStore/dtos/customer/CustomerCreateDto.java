package mindera.backendProject.bookStore.dtos.customer;

public record CustomerCreateDto(

        String firstName,

        String lastName,

        String email,

        Long nif,

        String favoriteGenre
) {
}
