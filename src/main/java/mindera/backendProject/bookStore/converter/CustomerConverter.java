package mindera.backendProject.bookStore.converter;

import mindera.backendProject.bookStore.dto.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerGetDto;
import mindera.backendProject.bookStore.dto.customer.CustomerPatchDto;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.model.Genre;

import java.util.List;

public class CustomerConverter {


    public static Customer fromCustomerCreateDtoToEntity(CustomerCreateDto customerCreateDto, List<Genre> genreList){
        return Customer.builder()
                .firstName(customerCreateDto.firstName())
                .lastName(customerCreateDto.lastName())
                .email(customerCreateDto.email())
                .nif(customerCreateDto.nif())
                .favoriteGenres(genreList)
                .build();
    }

    public static CustomerGetDto fromEntityToCustomerGetDto(Customer customer){
        return new CustomerGetDto(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getFavoriteGenres().stream().map(GenreConverter::fromModelToGenreCreateDto).toList()
        );
    }

    public static CustomerPatchDto fromEntityToCustomerPatchDto(Customer customer){
        return new CustomerPatchDto(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail()
        );
    }


}
