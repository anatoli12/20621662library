package bg.tuvarna.core.service;

import bg.tuvarna.api.exception.UserNotFoundException;
import bg.tuvarna.api.operations.operator.removereader.RemoveReader;
import bg.tuvarna.api.operations.operator.removereader.RemoveReaderInput;
import bg.tuvarna.api.operations.operator.removereader.RemoveReaderResult;
import bg.tuvarna.persistence.entity.Reader;
import bg.tuvarna.persistence.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RemoveReaderService implements RemoveReader {

    private final ReaderRepository readerRepository;

    @Override
    public RemoveReaderResult process(RemoveReaderInput input) {
        log.info("Processing RemoveReaderInput: {}", input);

        Optional<Reader> readerOptional = readerRepository.findByEmail(input.getEmail());

        if (readerOptional.isEmpty()) {
            log.warn("Reader with email {} not found.", input.getEmail());
            throw new UserNotFoundException("User with email not found");
        }

        Reader reader = readerOptional.get();

        log.info("Deleting Reader: {}", reader);
        readerRepository.delete(reader);
        log.info("Reader deleted successfully.");

        return RemoveReaderResult.builder()
                .name(reader.getFirstName().concat(" ").concat(reader.getLastName()))
                .build();
    }
}
