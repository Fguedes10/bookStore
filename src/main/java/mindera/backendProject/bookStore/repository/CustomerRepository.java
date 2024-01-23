package mindera.backendProject.bookStore.repository;

import mindera.backendProject.bookStore.dtos.customer.CustomerCreateDto;
import mindera.backendProject.bookStore.dtos.customer.CustomerPatchDto;
import mindera.backendProject.bookStore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(CustomerCreateDto customerCreateDto);
    Optional<Customer> patchFindByEmail(String email);

    Optional<Customer> findByNif(CustomerCreateDto customerCreateDto);

    Optional<Customer> findByUsername(CustomerCreateDto customerCreateDto);
}
