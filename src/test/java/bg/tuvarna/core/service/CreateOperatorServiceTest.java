package bg.tuvarna.core.service;

import bg.tuvarna.api.UserAuthority;
import bg.tuvarna.api.exception.IncorrectInputException;
import bg.tuvarna.api.exception.UserExistsException;
import bg.tuvarna.api.operations.administrator.createoperator.CreateOperatorInput;
import bg.tuvarna.api.operations.administrator.createoperator.CreateOperatorResult;
import bg.tuvarna.persistence.entity.User;
import bg.tuvarna.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateOperatorServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateOperatorService createOperatorService;

    private CreateOperatorInput validInput;

    @BeforeEach
    void setUp() {
        validInput = new CreateOperatorInput();
        validInput.setEmail("test@example.com");
        validInput.setPassword("password123");
        validInput.setConfirmPassword("password123");
    }

    @Test
    void process_WhenUserExists_ShouldThrowUserExistsException() {
        when(userRepository.findByEmail(validInput.getEmail())).thenReturn(Optional.of(User.builder()
                .id(UUID.randomUUID())
                .email("test@example.com")
                .password("password123")
                .userAuthority(UserAuthority.OPERATOR)
                .build()));

        UserExistsException exception = assertThrows(UserExistsException.class, () -> {
            createOperatorService.process(validInput);
        });

        assertEquals("User with this email exists.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void process_WhenPasswordsDoNotMatch_ShouldThrowIncorrectInputException() {
        validInput.setConfirmPassword("differentPassword");

        IncorrectInputException exception = assertThrows(IncorrectInputException.class, () -> {
            createOperatorService.process(validInput);
        });

        assertEquals("Passwords don't match.", exception.getMessage());
        verify(userRepository, never()).findByEmail(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void process_WhenInputIsValid_ShouldSaveUser() {
        when(userRepository.findByEmail(validInput.getEmail())).thenReturn(Optional.empty());

        CreateOperatorResult result = createOperatorService.process(validInput);

        verify(userRepository, times(1)).save(any(User.class));
        assertNotNull(result);
    }
}


