package s26555.sri02.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import s26555.sri02.dto.BookDto;
import s26555.sri02.model.Book;
import s26555.sri02.repositories.BookRepository;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/books")
public class BookController {
    private final BookRepository _bookRepository;
    private final ModelMapper _modelMapper;

    public BookController(BookRepository bookRepository, ModelMapper modelMapper) {
        this._bookRepository = bookRepository;
        this._modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<Collection<BookDto>> getBooks() {
        List<Book> books = _bookRepository.findAll();
        List<BookDto> result = books.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getNumberOfBooks() {
        List<Book> books = _bookRepository.findAll();
        return new ResponseEntity<>(books.size(), HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable UUID bookId) {
        Optional<Book> book = _bookRepository.findById(bookId);
        if (book.isPresent()) {
            BookDto bookDto = convertToDto(book.get());
            return new ResponseEntity<>(bookDto,
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity createNewBook(@RequestBody BookDto bookDto) {
        Book book = convertToEntity(bookDto);
        _bookRepository.save(book);
        HttpHeaders headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(book.getUuid())
                .toUri();
        headers.add("Location", location.toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity updateBook(@PathVariable UUID bookId, @RequestBody BookDto bookDto) {
        Optional<Book> currentBook = _bookRepository.findById(bookId);
        if (currentBook.isPresent()) {
            bookDto.setUuid(bookId);
            Book entity = convertToEntity(bookDto);
            _bookRepository.save(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity deleteBook(@PathVariable UUID bookId) {
        _bookRepository.deleteById(bookId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    private BookDto convertToDto(Book b) {
        return _modelMapper.map(b, BookDto.class);
    }

    private Book convertToEntity(BookDto dto) {
        return _modelMapper.map(dto, Book.class);
    }

}
