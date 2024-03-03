package anubislab.tech.insoapp.services;

import anubislab.tech.insoapp.domains.Article;
import anubislab.tech.insoapp.repositories.ArticleRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.BiFunction;

@Service
@Transactional(rollbackOn = Exception.class)
public class ArticleServiceImpl implements ArticleService{

    private ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Page<Article> getAllArticles(int page, int size){
        return articleRepository.findAll(PageRequest.of(page, size, Sort.by("title")));
    }

    @Override
    public Article getArticle(String id){
        return articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article non trouvé"));
    }

    @Override
    public Article createArticle(Article article){
        return articleRepository.save(article);
    }

    @Override
    public void deleteArticle(String id){
        // A completer
    }

    @Override
    public String uploadImage(String id, MultipartFile file){
        Article article = getArticle(id);
        String imageUrl = null;
        article.setImageUrl(imageUrl);
        articleRepository.save(article);

        return imageUrl;
    }

//    private final BiFunction<String, MultipartFile, String> imageFonction(id, image) -> {
//        try {
//
//        }catch (Exception e){
//
//        }
//    }
}
