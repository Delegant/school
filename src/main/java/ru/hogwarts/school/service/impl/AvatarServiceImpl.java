package ru.hogwarts.school.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import javax.transaction.Transactional;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

    @Value("$(students.avatar.dir.path)")
    private String avatarDir;

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(StudentService studentService, AvatarRepository avatarRepository){
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

}
