package bg.tuvarna.core.service;

import bg.tuvarna.api.operations.operator.createreader.CreateReader;
import bg.tuvarna.api.operations.operator.createreader.CreateReaderInput;
import bg.tuvarna.api.operations.operator.createreader.CreateReaderResult;
import bg.tuvarna.persistence.entity.Reader;
import bg.tuvarna.persistence.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateReaderService implements CreateReader {
    private final ReaderRepository readerRepository;

    @Override
    public CreateReaderResult process(CreateReaderInput input) {
        log.info("Processing CreateReaderInput: {}", input);

        Reader newReader = Reader.builder()
                .email(input.getEmail())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .email(input.getEmail())
                .phoneNumber(input.getPhoneNumber())
                .readerRating(3)
                .bookItemList(new ArrayList<>())
                .build();

        log.info("Saving new Reader: {}", newReader);
        readerRepository.save(newReader);
        log.info("New Reader saved successfully.");

        return CreateReaderResult.builder()
                .email(newReader.getEmail())
                .firstName(newReader.getFirstName())
                .lastName(newReader.getLastName())
                .email(newReader.getEmail())
                .phoneNumber(newReader.getPhoneNumber())
                .readerRating(newReader.getReaderRating())
                .build();
    }
}
