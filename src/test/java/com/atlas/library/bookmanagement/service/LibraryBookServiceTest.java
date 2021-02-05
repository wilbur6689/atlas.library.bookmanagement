package com.atlas.library.bookmanagement.service;

import com.atlas.library.bookmanagement.model.Book;
import com.atlas.library.bookmanagement.model.BookQuantity;
import com.atlas.library.bookmanagement.model.web.Requests;
import com.atlas.library.bookmanagement.repository.BookQuantityRepository;
import com.atlas.library.bookmanagement.repository.LibraryBookRepository;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LibraryBookServiceTest {

    private LibraryBookRepository libraryBookRepository;
    private BookQuantityRepository bookQuantityRepository;
    private LibraryBookService libraryBookService;

    @BeforeEach
    public void init() {

        libraryBookRepository = mock(LibraryBookRepository.class);
        bookQuantityRepository = mock(BookQuantityRepository.class);
        libraryBookService = new LibraryBookService(libraryBookRepository, bookQuantityRepository);
    }

    /*
     *  Unit Tests for getLibraryUser method
     */
    @Test
    public void testGetLibraryBookForSuccess() {
        // given:
        doReturn(getNewOptionalBookObject()).when(libraryBookRepository).findById(anyString());

        // when:
        val response = libraryBookService.getLibraryBook("userId");

        // then:
        verify(libraryBookRepository, times(1)).findById("userId");
        assertNotNull(response);
        assertEquals(getNewOptionalBookObject(), response);
    }

    /*
     *  Unit Tests for createLibraryUser method
     */
    @Test
    public void testCreateLibraryBookForSuccess() {
        // given:
        doReturn(getNewOptionalBookQuantityObject()).when(bookQuantityRepository).findByIsbn(anyString());
        doReturn(getNewBookObject()).when(libraryBookRepository).save(any());

        // when:
        val response = libraryBookService.createNewLibraryBook(getNewCreateBookModel());

        // then:
        verify(bookQuantityRepository, times(1)).findByIsbn("ISBN-002-TEST");
        assertNotNull(response);
        assertEquals(getNewBookObject(), response);
    }

    /*
     *  Unit Tests for updateLibraryUserAccBal method
     */
    @Test
    public void testUpdateLibraryBookCostForSuccess() {
        // given:
        doReturn(getNewOptionalBookObject()).when(libraryBookRepository).findById(anyString());
        doReturn(getNewUpdatedBookObject()).when(libraryBookRepository).save(any());

        // when:
        val response = libraryBookService.updateLibraryBook("userId", 1.00);

        // then:
        verify(libraryBookRepository, times(1)).findById("userId");
        verify(libraryBookRepository, times(1)).save(any());
        assertNotNull(response);
        assertEquals(1.00, response.get().getCost());
    }

    /*
     *  Unit Tests for deleteLibraryUser method
     */
    @Test
    public void testDeleteLibraryBookForSuccess() {
        // given:
        doReturn(getNewOptionalBookObject()).when(libraryBookRepository).findById(anyString());
        doReturn(getNewOptionalBookQuantityObject()).when(bookQuantityRepository).findByIsbn(anyString());

        // when:
        libraryBookService.deleteLibraryBook("bookId");

        // then:
        verify(libraryBookRepository, times(1)).findById("bookId");
        verify(bookQuantityRepository, times(1)).findByIsbn(anyString());
        verify(bookQuantityRepository, times(1)).delete(any());
        verify(libraryBookRepository, times(1)).deleteById("bookId");
    }

    /*
     *   Helper methods
     */

    private Optional<Book> getNewOptionalBookObject() {
        return Optional.of(Book.builder()
                .bookId("bookId-001-TEST")
                .ISBN("ISBN-001-TEST")
                .title("BookTitle-001-TEST")
                .author("BookAuthor-001-TEST")
                .genre("BookGenre-001-TEST")
                .cost(10.00)
                .publisherName("BookPublisher-001-TEST")
                .publishDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .modificationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .creationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .build());
    }

    private Book getNewUpdatedBookObject() {
        return Book.builder()
                .bookId("bookId-001-TEST")
                .ISBN("ISBN-001-TEST")
                .title("BookTitle-001-TEST")
                .author("BookAuthor-001-TEST")
                .genre("BookGenre-001-TEST")
                .cost(1.00)
                .publisherName("BookPublisher-001-TEST")
                .publishDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .modificationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .creationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .build();
    }

    private Book getNewBookObject() {
        return Book.builder()
                .bookId("bookId-002-TEST")
                .ISBN("ISBN-002-TEST")
                .title("BookTitle-002-TEST")
                .author("BookAuthor-002-TEST")
                .genre("BookGenre-002-TEST")
                .cost(5.00)
                .publisherName("BookPublisher-002-TEST")
                .publishDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .modificationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .creationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .build();
    }

    private Requests.CreateBookModel getNewCreateBookModel() {
        return Requests.CreateBookModel.builder()
                .isbn("ISBN-002-TEST")
                .title("BookTitle-002-TEST")
                .author("BookAuthor-002-TEST")
                .genre("BookGenre-002-TEST")
                .cost(5.00)
                .publisherName("BookPublisher-002-TEST")
                .publishDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .build();
    }

    private Optional<BookQuantity> getNewOptionalBookQuantityObject() {
        return Optional.of(BookQuantity.builder()
                .bookQuantityId("bookQuantityId-003-TEST")
                .isbn("ISBN-002-TEST")
                .totalQuantity(5)
                .currentQuantity(5)
                .modificationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .creationDate(LocalDateTime.parse("2019-02-03T10:08:02"))
                .build());
    }
}
