package bg.tuvarna.core.service;

import bg.tuvarna.api.operations.operator.getreaders.GetReaders;
import bg.tuvarna.api.operations.operator.getreaders.GetReadersInput;
import bg.tuvarna.api.operations.operator.getreaders.GetReadersResult;
import bg.tuvarna.api.operations.util.ReaderDTO;
import bg.tuvarna.persistence.entity.Reader;
import bg.tuvarna.persistence.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetReadersService implements GetReaders {

    private final ReaderRepository readerRepository;

    @Override
    public GetReadersResult process(GetReadersInput input) {
        log.info("Fetching all readers from the database.");

        List<Reader> readers = readerRepository.findAll();
        List<ReaderDTO> readerDTOs = readers.stream()
                .map(reader -> ReaderDTO.builder()
                        .firstName(reader.getFirstName())
                        .lastName(reader.getLastName())
                        .phoneNumber(reader.getPhoneNumber())
                        .email(reader.getEmail())
                        .rating(reader.getReaderRating())
                        .build())
                .toList();

        log.info("Fetched {} readers from the database.", readerDTOs.size());

        return GetReadersResult.builder()
                .readerDTOList(readerDTOs)
                .build();
    }
}