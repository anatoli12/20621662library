package bg.tuvarna.core.service;

import bg.tuvarna.api.administrator.registerbook.RegisterBook;
import bg.tuvarna.api.administrator.registerbook.RegisterBookInput;
import bg.tuvarna.api.administrator.registerbook.RegisterBookResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterBookService implements RegisterBook {

    @Override
    public RegisterBookResult process(RegisterBookInput input) {
        return null;
    }
}
