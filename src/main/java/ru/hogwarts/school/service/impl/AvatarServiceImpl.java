package ru.hogwarts.school.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

    @Value("${students.avatar.dir.path}")
    private String avatarDir;

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public void uploadAvatar(long studentId, MultipartFile pic) throws IOException {
        Student student = studentService.findStudent(studentId);

        Path picPath = Path.of(avatarDir, studentId + "." + getExtension(pic.getOriginalFilename()));
        Files.createDirectories(picPath.getParent());
        Files.deleteIfExists(picPath);

        try (InputStream is = pic.getInputStream();
             OutputStream os = Files.newOutputStream(picPath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(picPath.toString());
        avatar.setFileSize(pic.getSize());
        avatar.setMediaType(pic.getContentType());
        avatar.setPreview(generateImagePreview(picPath));

        avatarRepository.save(avatar);

    }

    @Override
    public Avatar findAvatar(long studentId) {
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    private byte[] generateImagePreview(Path picPath) throws IOException {
        try (InputStream is = Files.newInputStream(picPath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(picPath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }

    @Override
    public void getAvatar(long id, HttpServletResponse response) throws IOException {
        Avatar avatar = findAvatar(id);
        Path avatarPath = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(avatarPath);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLengthLong(avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @Override
    public void deleteAvatarFile(long id) throws IOException {
        Avatar avatar = avatarRepository.findByStudentId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student must be created."));
        Path avatarFilePath = Path.of(avatar.getFilePath());
        Files.deleteIfExists(avatarFilePath);
    }

    @Override
    public List<Avatar> getPageWithAvatars(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }

    @Override
    public int testInteger() {
        return Stream
                .iterate(1, a -> a + 1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, Integer::sum);
    }

    private String getExtension(String nameFile) {
        return nameFile.substring(nameFile.lastIndexOf('.') + 1);
    }
}
