package mindera.backendProject.bookStore.ServiceTests.BookServiceTests;

import mindera.backendProject.bookStore.converter.book.TranslationConverter;
import mindera.backendProject.bookStore.dto.book.TranslationCreateDto;
import mindera.backendProject.bookStore.exception.book.TranslationAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.TranslationNotFoundException;
import mindera.backendProject.bookStore.model.Translation;
import mindera.backendProject.bookStore.repository.bookRepository.TranslationRepository;
import mindera.backendProject.bookStore.service.bookService.TranslationServiceImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TranslationServiceTest {

    static MockedStatic<TranslationConverter> mockedTranslationConverter = Mockito.mockStatic(TranslationConverter.class);
    @MockBean
    private TranslationRepository translationRepositoryMock;
    @InjectMocks
    private TranslationServiceImpl translationService;

    @BeforeEach
    public void setUp() {

        translationService = new TranslationServiceImpl(translationRepositoryMock);
    }


    @Test
    void testGetAll() {
        Translation translation1 = Translation.builder().id(1L).name("English").build();
        Translation translation2 = Translation.builder().id(2L).name("Spanish").build();

        List<Translation> translationList = List.of(translation1, translation2);

        when(translationRepositoryMock.findAll()).thenReturn(translationList);
        mockedTranslationConverter.when(() -> TranslationConverter.fromModelToTranslationCreateDto(translation1))
                .thenReturn(new TranslationCreateDto(translation1.getName()));
        mockedTranslationConverter.when(() -> TranslationConverter.fromModelToTranslationCreateDto(translation2))
                .thenReturn(new TranslationCreateDto(translation2.getName()));

        List<TranslationCreateDto> resultDtos = translationService.getAll();

        assertNotNull(resultDtos);
        assertEquals(2, resultDtos.size());

        verify(translationRepositoryMock).findAll();

        mockedTranslationConverter.verify(() -> TranslationConverter.fromModelToTranslationCreateDto(translation1));
        mockedTranslationConverter.verify(() -> TranslationConverter.fromModelToTranslationCreateDto(translation2));

    }

    @Test
    void testGetTranslation() throws TranslationNotFoundException {
        Long translationId = 1L;

        Translation translation = new Translation();
        translation.setId(translationId);
        translation.setName("English");

        when(translationRepositoryMock.findById(translationId)).thenReturn(Optional.of(translation));

        TranslationCreateDto expectedDto = new TranslationCreateDto("English");

        when(TranslationConverter.fromModelToTranslationCreateDto(translation)).thenReturn(new TranslationCreateDto("English"));

        TranslationCreateDto resultDto = translationService.getTranslation(translationId);

        assertEquals(expectedDto, resultDto);
        verify(translationRepositoryMock, times(1)).findById(translationId);

        mockedTranslationConverter.verify(() -> TranslationConverter.fromModelToTranslationCreateDto(translation));
    }

    @Test
    void testGetTranslationThrowsException() {

        Long translationId = 1L;

        when(translationRepositoryMock.findById(translationId)).thenReturn(Optional.empty());

        assertThrows(TranslationNotFoundException.class, () -> translationService.getTranslation(translationId));

        verify(translationRepositoryMock, times(1)).findById(translationId);
    }

    @Test
    void testAddTranslationAlreadyExists() {
        // Arrange
        TranslationCreateDto existingTranslationDto = new TranslationCreateDto("Translation exists already");

        when(translationRepositoryMock.findByName("Translation exists already")).thenReturn(Optional.of(new Translation()));

        assertThrows(TranslationAlreadyExistsException.class, () -> translationService.add(existingTranslationDto));

        verify(translationRepositoryMock, times(1)).findByName("Translation exists already");

        verify(translationRepositoryMock, never()).save(any(Translation.class));
    }


    @Test
    void testGetTranslationByName() throws TranslationNotFoundException {
        String translationName = "English";
        Translation testTranslation = new Translation();
        testTranslation.setName(translationName);

        when(translationRepositoryMock.findByName(translationName)).thenReturn(Optional.of(testTranslation));
        when(translationRepositoryMock.findByName("Non Existing Translation")).thenReturn(Optional.empty());

        mockedTranslationConverter.when(() -> TranslationConverter.fromModelToTranslationCreateDto(testTranslation))
                .thenReturn(new TranslationCreateDto(translationName));

        TranslationCreateDto resultDto = translationService.getTranslationByName(translationName);
        assertNotNull(resultDto);

        verify(translationRepositoryMock).findByName(translationName);
        mockedTranslationConverter.verify(() -> TranslationConverter.fromModelToTranslationCreateDto(testTranslation));

        assertThrows(TranslationNotFoundException.class, () -> translationService.getTranslationByName("Non Existing Translation"));
    }

    @Test
    void testFindByIds() throws TranslationNotFoundException {

        List<Long> translationIds = List.of(1L, 2L);
        Translation translation1 = Translation.builder().id(1L).name("English").build();
        Translation translation2 = Translation.builder().id(2L).name("Spanish").build();

        when(translationRepositoryMock.findAllByIdIn(translationIds)).thenReturn(List.of(translation1, translation2));

        List<Translation> resultGenres = translationService.findByIds(translationIds);

        assertNotNull(resultGenres);
        assertEquals(2, resultGenres.size());

        verify(translationRepositoryMock).findAllByIdIn(translationIds);


        assertThrows(TranslationNotFoundException.class, () -> translationService.findByIds(List.of(1L, 4L, 5L)));
    }


}
