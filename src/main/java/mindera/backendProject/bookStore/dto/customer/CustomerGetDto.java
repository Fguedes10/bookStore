package mindera.backendProject.bookStore.dto.customer;

public record CustomerGetDto(

        String firstName,

        String lastName,
        String email,
        String favoriteGenre
) {


}
