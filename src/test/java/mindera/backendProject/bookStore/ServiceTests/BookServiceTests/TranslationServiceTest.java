package mindera.backendProject.bookStore.ServiceTests.BookServiceTests;

import mindera.backendProject.bookStore.converter.book.TranslationConverter;
import mindera.backendProject.bookStore.dto.book.TranslationCreateDto;
import mindera.backendProject.bookStore.exception.book.TranslationAlreadyExistsException;
import mindera.backendProject.bookStore.exception.book.TranslationNotFoundException;
import mindera.backendProject.bookStore.model.Translation;
import mindera.backendProject.bookStore.repository.bookRepository.TranslationRepository;
import mindera.backendProject.bookStore.service.bookService.TranslationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Get all translations and check repository")
    void testGetAll() {

        //GIVEN
        Translation translation1 = Translation.builder().id(1L).name("English").build();
        Translation translation2 = Translation.builder().id(2L).name("Spanish").build();

        List<Translation> translationList = List.of(translation1, translation2);

        when(translationRepositoryMock.findAll()).thenReturn(translationList);
        mockedTranslationConverter.when(() -> TranslationConverter.fromModelToTranslationCreateDto(translation1))
                .thenReturn(new TranslationCreateDto(translation1.getName()));
        mockedTranslationConverter.when(() -> TranslationConverter.fromModelToTranslationCreateDto(translation2))
                .thenReturn(new TranslationCreateDto(translation2.getName()));

        // WHEN
        List<TranslationCreateDto> resultDtos = translationService.getAll();


        //THEN
        assertNotNull(resultDtos);
        assertEquals(2, resultDtos.size());

        verify(translationRepositoryMock).findAll();

        mockedTranslationConverter.verify(() -> TranslationConverter.fromModelToTranslationCreateDto(translation1));
        mockedTranslationConverter.verify(() -> TranslationConverter.fromModelToTranslationCreateDto(translation2));

    }

    @Test
    @DisplayName("Get translation by id and check repository")
    void testGetTranslation() throws TranslationNotFoundException {

        // GIVEN
        Long translationId = 1L;
        Translation translation = new Translation();
        translation.setId(translationId);
        translation.setName("English");

        when(translationRepositoryMock.findById(translationId)).thenReturn(Optional.of(translation));
        TranslationCreateDto expectedDto = new TranslationCreateDto("English");
        when(TranslationConverter.fromModelToTranslationCreateDto(translation)).thenReturn(new TranslationCreateDto("English"));

        // WHEN
        TranslationCreateDto resultDto = translationService.getTranslation(translationId);

        // THEN
        assertEquals(expectedDto, resultDto);
        verify(translationRepositoryMock, times(1)).findById(translationId);

        mockedTranslationConverter.verify(() -> TranslationConverter.fromModelToTranslationCreateDto(translation));
    }


    @Test
    @DisplayName("Add a Translation and check repository and Exception")
    void testAddTranslationSuccessfully() {

        // GIVEN
        TranslationCreateDto translationExistingDto = new TranslationCreateDto("Existing Translation");

        when(translationRepositoryMock.findByName(translationExistingDto.name())).thenReturn(Optional.of(new Translation()));

        // WHEN / THEN
        assertThrows(TranslationAlreadyExistsException.class, () -> {
            translationService.add(translationExistingDto);
        });
        verify(translationRepositoryMock, never()).save(any(Translation.class));

    }

    @Test
    @DisplayName("Get translation when the requested translation does not exist in the DB")
    void testGetTranslationThrowsException() {

        // GIVEN
        Long translationId = 1L;

        // WHEN
        when(translationRepositoryMock.findById(translationId)).thenReturn(Optional.empty());

        // THEN
        assertThrows(TranslationNotFoundException.class, () -> translationService.getTranslation(translationId));
        verify(translationRepositoryMock, times(1)).findById(translationId);
    }

    @Test
    @DisplayName("Add translation with duplicate name and check exception")
    void testAddTranslationAlreadyExistsException() {

        // GIVEN
        TranslationCreateDto existingTranslationDto = new TranslationCreateDto("Translation exists already");

        // WHEN
        when(translationRepositoryMock.findByName("Translation exists already")).thenReturn(Optional.of(new Translation()));

        //THEN
        assertThrows(TranslationAlreadyExistsException.class, () -> translationService.add(existingTranslationDto));
        verify(translationRepositoryMock, times(1)).findByName("Translation exists already");
        verify(translationRepositoryMock, never()).save(any(Translation.class));
    }


    @Test
    @DisplayName("Get translation by name and check exception")
    void testGetTranslationByName() throws TranslationNotFoundException {

        // GIVEN
        String translationName = "English";
        Translation testTranslation = new Translation();
        testTranslation.setName(translationName);

        when(translationRepositoryMock.findByName(translationName)).thenReturn(Optional.of(testTranslation));
        when(translationRepositoryMock.findByName("Non Existing Translation")).thenReturn(Optional.empty());
        mockedTranslationConverter.when(() -> TranslationConverter.fromModelToTranslationCreateDto(testTranslation))
                .thenReturn(new TranslationCreateDto(translationName));

        // WHEN
        TranslationCreateDto resultDto = translationService.getTranslationByName(translationName);
        assertNotNull(resultDto);

        // GIVEN
        verify(translationRepositoryMock).findByName(translationName);
        mockedTranslationConverter.verify(() -> TranslationConverter.fromModelToTranslationCreateDto(testTranslation));
        assertThrows(TranslationNotFoundException.class, () -> translationService.getTranslationByName("Non Existing Translation"));
    }

    @Test
    @DisplayName("Test findByIds - Existing Translations")
    void testFindByIds() throws TranslationNotFoundException {

        // GIVEN
        List<Long> translationIds = List.of(1L, 2L);
        Translation translation1 = Translation.builder().id(1L).name("English").build();
        Translation translation2 = Translation.builder().id(2L).name("Spanish").build();

        when(translationRepositoryMock.findAllByIdIn(translationIds)).thenReturn(List.of(translation1, translation2));

        // WHEN
        List<Translation> resultGenres = translationService.findByIds(translationIds);

        // THEN
        assertNotNull(resultGenres);
        assertEquals(2, resultGenres.size());
        verify(translationRepositoryMock).findAllByIdIn(translationIds);
        assertThrows(TranslationNotFoundException.class, () -> translationService.findByIds(List.of(1L, 4L, 5L)));
    }


}
