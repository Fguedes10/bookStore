package mindera.backendProject.bookStore.converter;

import mindera.backendProject.bookStore.dto.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.dto.customer.CustomerGetDto;
import mindera.backendProject.bookStore.dto.customer.CustomerPatchDto;
import mindera.backendProject.bookStore.model.Customer;

public class CustomerConverter {


    public static CustomerCreateDto fromEntitytoCustomerCreateDto(Customer customer){
        return new CustomerCreateDto(
                customer.getUsername(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getNif(),
                customer.getFavoriteGenre()

        );
    }

    public static Customer fromCustomerCreateDtoToEntity(CustomerCreateDto customerCreateDto){
        return Customer.builder()
                .firstName(customerCreateDto.firstName())
                .lastName(customerCreateDto.lastName())
                .email(customerCreateDto.email())
                .nif(customerCreateDto.nif())
                .favoriteGenre(customerCreateDto.favoriteGenre()).build();
    }

    public static CustomerGetDto fromEntityToCustomerGetDto(Customer customer){
        return new CustomerGetDto(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getFavoriteGenre()
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
