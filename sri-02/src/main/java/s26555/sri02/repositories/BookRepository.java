package s26555.sri02.repositories;

import org.springframework.data.repository.CrudRepository;
import s26555.sri02.model.Book;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends CrudRepository<Book, UUID> {
    List<Book> findAll();
}
