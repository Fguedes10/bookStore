package mindera.backendProject.bookStore;

import mindera.backendProject.bookStore.model.Book;
import mindera.backendProject.bookStore.repository.bookRepository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepositoryTest;

    @AfterEach
    public void init(){
        bookRepositoryTest.deleteAll();
    }

    @Test
    public void test_getBooksFromRepository_Should_Work(){

        //Given

       // List<Book> books = getMockedBooks();
        // bookRepositoryTest.save(books.get(0));
        //bookRepositoryTest.save(books.get(1));

    }


    @Test
    public void test_getBooksByName_Should_Work(){

    }


    @Test void test_getBooksByIsdn_Should_Work(){

    }


}
