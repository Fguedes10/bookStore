package mindera.backendProject.bookStore.converter;

import mindera.backendProject.bookStore.dtos.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.model.Customer;

public class CustomerConverter {


    public static CustomerCreateDto fromEntitytoCustomerCreateDto(Customer customer){
        return new CustomerCreateDto(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getNif(),
                customer.getFavoriteGenre()

        );
    }

}
