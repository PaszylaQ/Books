package s26555.sri02.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    UUID uuid;
    String title;
    String author;
    String genre;
    LocalDate releaseDate;
    int numberOfPages;
}
