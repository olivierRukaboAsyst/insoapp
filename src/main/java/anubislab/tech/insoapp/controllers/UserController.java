package anubislab.tech.insoapp.controllers;

import anubislab.tech.insoapp.domains.User;
import anubislab.tech.insoapp.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static anubislab.tech.insoapp.constant.Constant.PHOTO_DIRECTORY;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.created(URI.create("/users/userId")).body(userService.createUser(user));
    }

    @GetMapping
    public ResponseEntity<Page<User>> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok().body(userService.getAllUsers(page, size));
    }

    @GetMapping("/id")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @PutMapping("/image")
    public ResponseEntity<String> uploadImage(@RequestParam("id") Long id, @RequestParam("file")MultipartFile file){
        return ResponseEntity.ok().body(userService.uploadImage(id, file));
    }

    @GetMapping(path = "/image/{filename}", produces = {IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE})
    public byte[] getPhoto(@PathVariable("filename") String filename) throws IOException {
        return Files.readAllBytes(Paths.get(PHOTO_DIRECTORY+filename));
    }
}
