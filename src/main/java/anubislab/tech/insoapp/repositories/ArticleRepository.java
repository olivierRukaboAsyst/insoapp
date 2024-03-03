package anubislab.tech.insoapp.repositories;

import anubislab.tech.insoapp.domains.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, String> {
    Optional<Article> findById(String id);
}
