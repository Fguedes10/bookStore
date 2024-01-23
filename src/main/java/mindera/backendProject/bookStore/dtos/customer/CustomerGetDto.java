package mindera.backendProject.bookStore.dtos.customer;

public record CustomerGetDto(

        String firstName,

        String lastName,
        String email,
        String favoriteGenre
) {


}
