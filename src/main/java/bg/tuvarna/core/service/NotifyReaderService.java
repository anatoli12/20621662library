package bg.tuvarna.core.service;

import bg.tuvarna.api.operations.operator.notifyreader.NotifyReader;
import bg.tuvarna.api.operations.operator.notifyreader.NotifyReaderInput;
import bg.tuvarna.api.operations.operator.notifyreader.NotifyReaderResult;
import bg.tuvarna.persistence.entity.BookItem;
import bg.tuvarna.persistence.entity.Reader;
import bg.tuvarna.persistence.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotifyReaderService implements NotifyReader {
    private final ReaderRepository readerRepository;

    @Override
    public NotifyReaderResult process(NotifyReaderInput input) {
        Reader reader = readerRepository.findByEmail(input.getEmail()).get();

        String recipient = reader.getFirstName() + " " + reader.getLastName();
        String body = createEmailBody(reader);
        String filePath = saveEmailToFile(recipient, body);

        return NotifyReaderResult.builder()
                .recipient(recipient)
                .body(body)
                .filePath(filePath)
                .build();
    }

    private String createEmailBody(Reader reader) {
        StringBuilder body = new StringBuilder();
        body.append("<html>");
        body.append("<body>");
        body.append("<p>Dear ").append(reader.getFirstName()).append(" ").append(reader.getLastName()).append(",</p>");
        body.append("<p>This is a reminder that you have to return books to the library:</p>");
        body.append("<p>Please return them at your earliest convenience.</p>");
        body.append("<p>Thank you,</p>");
        body.append("<p>Your Library</p>");
        body.append("</body>");
        body.append("</html>");

        return body.toString();
    }

    private String saveEmailToFile(String recipient, String body) {
        String fileName = "email_to_" + recipient.replace(" ", "_") + ".txt";
        Path path = Paths.get(fileName);

        try {
            Files.write(path, body.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to write email to file", e);
        }

        return path.toAbsolutePath().toString();
    }
}
