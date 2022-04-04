package s26555.sri02.helpers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import s26555.sri02.model.Book;
import s26555.sri02.repositories.BookRepository;

import java.util.List;
import java.util.UUID;

@Configuration
public class BookGenerator {

    @Bean
    CommandLineRunner commandLineRunner(BookRepository bookRepository) {
        return args -> {
            Book book1 =
                    new Book(UUID.randomUUID(), "Pride and Prejudice", "Jane Austen", "novel", 1813, 484);
            Book book2 =
                    new Book(UUID.randomUUID(), "Anne of Green Gables", "Montgomery Lucy Maud", "novel", 1908, 440);
            Book book3 =
                    new Book(UUID.randomUUID(), "November 9 (Nine)", "Hoover Colleen", "romance", 2015, 310);
            Book book4 =
                    new Book(UUID.randomUUID(), "The picture of Dorian Gray", "Oscar Wilde", "thriller", 2018, 240);
            Book book5 =
                    new Book(UUID.randomUUID(), "Blood of Elves: Witcher 1", "Andrzej Sapkowski", "fantasy", 2020, 320);
            Book book6 =
                    new Book(UUID.randomUUID(), "Pillow Thoughts", "Peppernell Courtney", "poetry", 2017, 272);

            bookRepository.saveAll(List.of(book1, book2, book3, book4, book5, book6));
        };
    }
}
