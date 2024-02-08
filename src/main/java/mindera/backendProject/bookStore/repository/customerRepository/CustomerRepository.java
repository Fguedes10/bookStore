package mindera.backendProject.bookStore.repository.customerRepository;

import jakarta.transaction.Transactional;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByNif(Long nif);
    Optional<Customer> findByUsername(String username);

    Page<Customer> findAll(Pageable pageable);



    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE customer AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

    @Query("SELECT c FROM Customer c JOIN c.favoriteBooks b WHERE b.id = :bookId")
    List<Customer> findCustomersByFavoriteBook(@Param("bookId") Long bookId);

}
