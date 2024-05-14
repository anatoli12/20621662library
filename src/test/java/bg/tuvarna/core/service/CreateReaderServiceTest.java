package bg.tuvarna.core.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import bg.tuvarna.api.operations.operator.createreader.CreateReaderInput;
import bg.tuvarna.api.operations.operator.createreader.CreateReaderResult;
import bg.tuvarna.persistence.entity.Reader;
import bg.tuvarna.persistence.repository.ReaderRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class CreateReaderServiceTest {

    @Mock
    private ReaderRepository readerRepository;

    @InjectMocks
    private CreateReaderService createReaderService;

    @Mock
    private Validator validator;

    private CreateReaderInput validInput;
    private CreateReaderInput invalidEmailInput;
    private CreateReaderInput shortFirstNameInput;

    @BeforeEach
    void setUp() {
        validInput = CreateReaderInput.builder()
                .firstName("Valeri")
                .lastName("Raikov")
                .phoneNumber("+1234567890")
                .email("valeri.raikov@example.com")
                .build();

        invalidEmailInput = CreateReaderInput.builder()
                .firstName("Anatoli")
                .lastName("Raikov")
                .phoneNumber("+1234567890")
                .email("anatoli.raikovexample.com") // Invalid email format
                .build();

        shortFirstNameInput = CreateReaderInput.builder()
                .firstName("V") // Invalid, too short
                .lastName("Dimov")
                .phoneNumber("+1234567890")
                .email("v.dimov@example.com")
                .build();
    }

    @Test
    void process_WhenInputIsValid_ShouldSaveReader() {
        // Arrange
        Reader reader = Reader.builder()
                .email(validInput.getEmail())
                .firstName(validInput.getFirstName())
                .lastName(validInput.getLastName())
                .phoneNumber(validInput.getPhoneNumber())
                .readerRating(3)
                .bookItemList(new ArrayList<>())
                .build();

        when(validator.validate(validInput)).thenReturn(Collections.emptySet());
        when(readerRepository.save(any(Reader.class))).thenReturn(reader);

        // Act
        CreateReaderResult result = createReaderService.process(validInput);

        // Assert
        verify(validator, times(1)).validate(validInput);
        verify(readerRepository, times(1)).save(any(Reader.class));
        assertNotNull(result);
        assertEquals(validInput.getEmail(), result.getEmail());
        assertEquals(validInput.getFirstName(), result.getFirstName());
        assertEquals(validInput.getLastName(), result.getLastName());
        assertEquals(validInput.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(3, result.getReaderRating());
    }

    @Test
    void process_WhenEmailIsInvalid_ShouldThrowConstraintViolationException() {
        Set<ConstraintViolation<CreateReaderInput>> violations = new HashSet<>();
        violations.add(mock(ConstraintViolation.class));
        when(validator.validate(invalidEmailInput)).thenReturn(violations);

        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> {
            createReaderService.process(invalidEmailInput);
        });

        verify(readerRepository, never()).save(any(Reader.class));
    }

    @Test
    void process_WhenFirstNameIsTooShort_ShouldThrowConstraintViolationException() {
        Set<ConstraintViolation<CreateReaderInput>> violations = new HashSet<>();
        violations.add(mock(ConstraintViolation.class));
        when(validator.validate(shortFirstNameInput)).thenReturn(violations);

        // Act & Assert
        assertThrows(ConstraintViolationException.class, () -> {
            createReaderService.process(shortFirstNameInput);
        });

        verify(readerRepository, never()).save(any(Reader.class));
    }

    @Test
    void process_WhenRepositoryThrowsException_ShouldPropagateException() {
        // Arrange
        when(readerRepository.save(any(Reader.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            createReaderService.process(validInput);
        });

        assertEquals("Database error", exception.getMessage());
        verify(readerRepository, times(1)).save(any(Reader.class));
    }
}