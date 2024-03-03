package anubislab.tech.insoapp.services;

import anubislab.tech.insoapp.domains.User;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    Page<User> getAllUsers(int page, int size);
    User getUser(Long id);
    User createUser(User user);
    void deleteUser(Long id);
    String uploadImage(Long id, MultipartFile file);
}
