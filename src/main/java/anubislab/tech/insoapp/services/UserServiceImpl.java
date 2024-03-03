package anubislab.tech.insoapp.services;

import anubislab.tech.insoapp.domains.User;
import anubislab.tech.insoapp.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static anubislab.tech.insoapp.constant.Constant.PHOTO_DIRECTORY;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<User> getAllUsers(int page, int size){
        return userRepository.findAll(PageRequest.of(page, size, Sort.by("username")));
    }

    @Override
    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @Override
    public User createUser(User user){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        user.setCreatedAt(sdf.format(new Date()));
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id){
        // A completer
    }

    @Override
    public String uploadImage(Long id, MultipartFile file){
        log.info("Téléchargement de l'image ID: {}", id);
        User user = getUser(id);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        user.setUpdatedAt(sdf.format(new Date()));
        String imageUrl = photoFunction.apply(id, file);
        user.setImageUrl(imageUrl);
        userRepository.save(user);

        return imageUrl;
    }

    private final Function<String, String> fileExtension = filename -> Optional.of(filename).filter(name->name.contains("."))
            .map(name->"." +name.substring(filename.lastIndexOf(".")+1)).orElse(".png");


    private final BiFunction<Long, MultipartFile, String> photoFunction = (id, image) -> {
        String fileName = id + fileExtension.apply(image.getOriginalFilename());
        try {
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();
            if (!Files.exists(fileStorageLocation)){Files.createDirectories(fileStorageLocation);}
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(fileName), REPLACE_EXISTING);
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/users/image/" + fileName).toUriString();
        }catch (Exception e){
            throw new RuntimeException("Impossible d'enregistrer l'image");
        }
    };
}
