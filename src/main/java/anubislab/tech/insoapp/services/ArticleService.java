package anubislab.tech.insoapp.services;

import anubislab.tech.insoapp.domains.Article;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService {
    Page<Article> getAllArticles(int page, int size);
    Article getArticle(String id);
    Article createArticle(Article article);
    void deleteArticle(String id);
    String uploadImage(String id, MultipartFile file);
}
