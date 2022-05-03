package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AvatarService {
    void uploadAvatar(long id, MultipartFile avatar) throws IOException;

    Avatar findAvatar(long studentId);

    void getAvatar(long id, HttpServletResponse response)throws IOException;

    void deleteAvatarFile(long id) throws IOException;
}
