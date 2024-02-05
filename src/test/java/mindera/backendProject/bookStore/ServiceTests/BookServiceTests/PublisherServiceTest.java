package mindera.backendProject.bookStore.ServiceTests.BookServiceTests;

import mindera.backendProject.bookStore.converter.book.PublisherConverter;
import mindera.backendProject.bookStore.dto.book.PublisherCreateDto;
import mindera.backendProject.bookStore.exception.book.PublisherAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.PublisherNotFoundException;
import mindera.backendProject.bookStore.model.Publisher;
import mindera.backendProject.bookStore.repository.bookRepository.PublisherRepository;
import mindera.backendProject.bookStore.service.bookService.PublisherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    void testGetAll() {

        Publisher publisher1 = Publisher.builder().id(1L).name("Porto Editora").build();
        Publisher publisher2 = Publisher.builder().id(1L).name("Bertrand Editora").build();
        List<Publisher> publisherList = List.of(publisher1, publisher2);

        when(publisherRepositoryMock.findAll()).thenReturn(publisherList);

        mockedPublisherConverter.when(() -> PublisherConverter.fromModelToPublisherCreateDto(publisher1))
                .thenReturn(new PublisherCreateDto(publisher1.getName()));
        mockedPublisherConverter.when(() -> PublisherConverter.fromModelToPublisherCreateDto(publisher2))
                .thenReturn(new PublisherCreateDto(publisher2.getName()));


        List<PublisherCreateDto> result = publisherService.getAll();


        assertNotNull(result);
        assertEquals(2, result.size());


        verify(publisherRepositoryMock).findAll();

        mockedPublisherConverter.verify(() -> PublisherConverter.fromModelToPublisherCreateDto(publisher1));
        mockedPublisherConverter.verify(() -> PublisherConverter.fromModelToPublisherCreateDto(publisher2));
    }


    @Test
    void testGetPublisher() throws PublisherNotFoundException {
        Long publisherId = 1L;
        Publisher publisher = new Publisher();
        publisher.setId(publisherId);
        publisher.setName("Porto Editora");

        when(publisherRepositoryMock.findById(publisherId)).thenReturn(Optional.of(publisher));

        PublisherCreateDto expectedDto = new PublisherCreateDto("Porto Editora");
        when(PublisherConverter.fromModelToPublisherCreateDto(publisher)).thenReturn(new PublisherCreateDto("Porto Eitora"));
        PublisherCreateDto resultDto = publisherService.getPublisher(publisherId);

        assertNotNull(resultDto);

        verify(publisherRepositoryMock).findById(publisherId);

        mockedPublisherConverter.verify(() -> PublisherConverter.fromModelToPublisherCreateDto(publisher));
    }

    @Test
    void testGetPublisherThrowsException() {

        Long publisherId = 1L;

        when(publisherRepositoryMock.findById(publisherId)).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class, () -> publisherService.getPublisher(publisherId));

        verify(publisherRepositoryMock, times(1)).findById(publisherId);
    }


    @Test
    void testAddPublisherAlreadyExists() {
        // Arrange
        PublisherCreateDto existingPublisherDto = new PublisherCreateDto("Publisher exists already");

        when(publisherRepositoryMock.findByName("Publisher exists already")).thenReturn(Optional.of(new Publisher()));

        assertThrows(PublisherAlreadyExistsException.class, () -> publisherService.add(existingPublisherDto));

        verify(publisherRepositoryMock, times(1)).findByName("Publisher exists already");

        verify(publisherRepositoryMock, never()).save(any(Publisher.class));
    }


    @Test
    void testGetPublisherByName() throws PublisherNotFoundException {
        String publisherName = "Porto Editora";
        Publisher publisher = Publisher.builder().id(1L).name(publisherName).build();

        when(publisherRepositoryMock.findByName(publisherName)).thenReturn(Optional.of(publisher));

        mockedPublisherConverter.when(() -> PublisherConverter.fromModelToPublisherCreateDto(publisher))
                .thenReturn(new PublisherCreateDto(publisherName));


        PublisherCreateDto result = publisherService.getPublisherByName(publisherName);

        assertNotNull(result);

        verify(publisherRepositoryMock).findByName(publisherName);

        mockedPublisherConverter.verify(() -> PublisherConverter.fromModelToPublisherCreateDto(publisher));
    }


    @Test
    void testGetPublisherById() throws PublisherNotFoundException {
        Long publisherId = 1L;
        Publisher publisher = Publisher.builder().id(publisherId).name("Porto Editora").build();

        when(publisherRepositoryMock.findById(publisherId)).thenReturn(Optional.of(publisher));

        Publisher result = publisherService.getPublisherById(publisherId);

        assertNotNull(result);
        assertEquals(publisherId, result.getId());

        verify(publisherRepositoryMock).findById(publisherId);

        mockedPublisherConverter.verifyNoInteractions();
    }


    @Test
    void testGetPublisherNotFound() {

        String publisherName = "Non Existent Publisher";

        when(publisherRepositoryMock.findByName(publisherName)).thenReturn(Optional.empty());

        assertThrows(PublisherNotFoundException.class, () -> publisherService.getPublisherByName(publisherName));

        verify(publisherRepositoryMock).findByName(publisherName);

        mockedPublisherConverter.verifyNoInteractions();
    }

}




