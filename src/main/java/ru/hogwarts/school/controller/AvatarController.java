package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.util.List;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    public final AvatarService avatarService;


    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping("/get_page")
    public List<Avatar> getPageWithAvatars(@RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        return avatarService.getPageWithAvatars(pageNumber, pageSize);
        }
}
