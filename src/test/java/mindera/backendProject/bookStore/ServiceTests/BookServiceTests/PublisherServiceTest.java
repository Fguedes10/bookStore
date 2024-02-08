package mindera.backendProject.bookStore.ServiceTests.BookServiceTests;

import mindera.backendProject.bookStore.converter.book.PublisherConverter;
import mindera.backendProject.bookStore.dto.book.PublisherCreateDto;
import mindera.backendProject.bookStore.exception.book.PublisherAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.PublisherNotFoundException;
import mindera.backendProject.bookStore.model.Customer;
import mindera.backendProject.bookStore.model.Publisher;
import mindera.backendProject.bookStore.repository.bookRepository.PublisherRepository;
import mindera.backendProject.bookStore.service.bookService.PublisherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PublisherServiceTest {

    static MockedStatic<PublisherConverter> mockedPublisherConverter = Mockito.mockStatic(PublisherConverter.class);

    @MockBean
    private PublisherRepository publisherRepositoryMock;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    @BeforeEach
    public void setUp() {
        publisherService = new PublisherServiceImpl(publisherRepositoryMock);
    }


    @Test
    @DisplayName("Get all publishers and check repository")
    void testGetAll() {

        // GIVEN
        int page = 0;
        int size = 10;
        String searchTerm = "name";

        Publisher publisher1 = Publisher.builder().id(1L).name("Porto Editora").build();
        Publisher publisher2 = Publisher.builder().id(1L).name("Bertrand Editora").build();
        List<Publisher> publisherList = List.of(publisher1, publisher2);
        Page<Publisher> mockedPage = new PageImpl<>(publisherList);

        // WHEN
        when(publisherRepositoryMock.findAll(any(PageRequest.class))).thenReturn(mockedPage);

        mockedPublisherConverter.when(() -> PublisherConverter.fromModelToPublisherCreateDto(publisher1))
                .thenReturn(new PublisherCreateDto(publisher1.getName()));
        mockedPublisherConverter.when(() -> PublisherConverter.fromModelToPublisherCreateDto(publisher2))
                .thenReturn(new PublisherCreateDto(publisher2.getName()));

        List<PublisherCreateDto> result = publisherService.getAll(page, size, searchTerm);




        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(publisherRepositoryMock).findAll(PageRequest.of(page, size, Sort.Direction.DESC, searchTerm));

        mockedPublisherConverter.verify(() -> PublisherConverter.fromModelToPublisherCreateDto(publisher1));
        mockedPublisherConverter.verify(() -> PublisherConverter.fromModelToPublisherCreateDto(publisher2));
    }


    @Test
    @DisplayName("Get publisher by id and check repository")
    void testGetPublisher() throws PublisherNotFoundException {

        // GIVEN
        Long publisherId = 1L;
        Publisher publisher = new Publisher();
        publisher.setId(publisherId);
        publisher.setName("Porto Editora");

        // WHEN
        when(publisherRepositoryMock.findById(publisherId)).thenReturn(Optional.of(publisher));

        PublisherCreateDto expectedDto = new PublisherCreateDto("Porto Editora");
        when(PublisherConverter.fromModelToPublisherCreateDto(publisher)).thenReturn(new PublisherCreateDto("Porto Eitora"));
        PublisherCreateDto resultDto = publisherService.getPublisher(publisherId);

        // THEN
        assertNotNull(resultDto);
        verify(publisherRepositoryMock).findById(publisherId);

        mockedPublisherConverter.verify(() -> PublisherConverter.fromModelToPublisherCreateDto(publisher));
    }

    @Test
    @DisplayName("Add a Publisher and check repository and Exception")
    void testAddPublisherSuccessfully() {

        // GIVEN
        PublisherCreateDto publisherExistingDto = new PublisherCreateDto("Existing Publisher");

        when(publisherRepositoryMock.findByName(publisherExistingDto.name())).thenReturn(Optional.of(new Publisher()));

        // WHEN / THEN
        assertThrows(PublisherAlreadyExistsException.class, () -> {
            publisherService.add(publisherExistingDto);
        });
        verify(publisherRepositoryMock, never()).save(any(Publisher.class));

    }

    @Test
    @DisplayName("Get publisher when the requested publisher does not exist in the DB")
    void testGetPublisherThrowsException() {

        // GIVEN
        Long publisherId = 1L;

        // WHEN
        when(publisherRepositoryMock.findById(publisherId)).thenReturn(Optional.empty());

        // THEN
        assertThrows(PublisherNotFoundException.class, () -> publisherService.getPublisher(publisherId));

        verify(publisherRepositoryMock, times(1)).findById(publisherId);
    }


    @Test
    @DisplayName("Add publisher with duplicate name and check exception")
    void testAddPublisherAlreadyExistsException() {
        // GIVEN
        PublisherCreateDto existingPublisherDto = new PublisherCreateDto("Publisher exists already");

        // WHEN
        when(publisherRepositoryMock.findByName("Publisher exists already")).thenReturn(Optional.of(new Publisher()));

        // THEN
        assertThrows(PublisherAlreadyExistsException.class, () -> publisherService.add(existingPublisherDto));

        verify(publisherRepositoryMock, times(1)).findByName("Publisher exists already");

        verify(publisherRepositoryMock, never()).save(any(Publisher.class));
    }


    @Test
    @DisplayName("Get publisher by name and check exception")
    void testGetPublisherByName() throws PublisherNotFoundException {

        // GIVEN
        String publisherName = "Porto Editora";
        Publisher publisher = Publisher.builder().id(1L).name(publisherName).build();

        when(publisherRepositoryMock.findByName(publisherName)).thenReturn(Optional.of(publisher));
        mockedPublisherConverter.when(() -> PublisherConverter.fromModelToPublisherCreateDto(publisher))
                .thenReturn(new PublisherCreateDto(publisherName));

        // WHEN
        PublisherCreateDto result = publisherService.getPublisherByName(publisherName);

        // THEN
        assertNotNull(result);
        verify(publisherRepositoryMock).findByName(publisherName);
        mockedPublisherConverter.verify(() -> PublisherConverter.fromModelToPublisherCreateDto(publisher));
    }


    @Test
    @DisplayName("Test findById - Existing Genre publishers")
    void testGetPublisherById() throws PublisherNotFoundException {

        // GIVEN
        Long publisherId = 1L;
        Publisher publisher = Publisher.builder().id(publisherId).name("Porto Editora").build();

        when(publisherRepositoryMock.findById(publisherId)).thenReturn(Optional.of(publisher));

        // WHEN
        Publisher result = publisherService.getPublisherById(publisherId);

        // THEN
        assertNotNull(result);
        assertEquals(publisherId, result.getId());
        verify(publisherRepositoryMock).findById(publisherId);

        mockedPublisherConverter.verifyNoInteractions();
    }


    @Test
    @DisplayName("Test get publisher by name when the requested publisher does not exist in the DB")
    void testGetPublisherNotFound() {

        // GIVEN
        String publisherName = "Non Existent Publisher";

        // WHEN
        when(publisherRepositoryMock.findByName(publisherName)).thenReturn(Optional.empty());

        // THEN
        assertThrows(PublisherNotFoundException.class, () -> publisherService.getPublisherByName(publisherName));
        verify(publisherRepositoryMock).findByName(publisherName);
        mockedPublisherConverter.verifyNoInteractions();
    }

}




