package lk.ijse.cmjd.project.business;

import lk.ijse.cmjd.project.dao.DAOFactory;
import lk.ijse.cmjd.project.dao.custom.BookDAO;
import lk.ijse.cmjd.project.dao.custom.MemberDAO;
import lk.ijse.cmjd.project.dto.BookDTO;
import lk.ijse.cmjd.project.dto.MemberDTO;
import lk.ijse.cmjd.project.entity.Book;
import lk.ijse.cmjd.project.entity.Member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageBookBusiness {
    private static BookDAO bookDAO= (BookDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.BOOK);

    public static boolean addBook(BookDTO bookDTO) throws SQLException {
        Book book=new Book(bookDTO.getBookId(),bookDTO.getTitle(),bookDTO.getSubject(),bookDTO.getAuthor(),bookDTO.getPrice(),bookDTO.getFullQty(),bookDTO.getQtyOnHand(),bookDTO.getPublisherId());
        return bookDAO.save(book);
    }
    public static List<BookDTO> getBooks() throws SQLException {
        List<Book>allBooks=bookDAO.findAll();
        List<BookDTO>tmpDTOs=new ArrayList<>();
        for (Book book:allBooks){
            BookDTO dto=new BookDTO(book.getBookId(),book.getTitle(),book.getSubject(),book.getAuthor(),book.getPrice(),book.getFullQty(),book.getQtyOnHand(),book.getPublisherId());

            tmpDTOs.add(dto);
        }
        return  tmpDTOs;
    }
    public static boolean deleteBook(String bookID) throws SQLException {
        return bookDAO.delete(bookID);
    }
    public static boolean   updateBook(BookDTO dto) throws SQLException {
        Book book=new Book(dto.getBookId(),dto.getTitle(),dto.getSubject(),dto.getAuthor(),dto.getPrice(),dto.getFullQty(),dto.getQtyOnHand(),dto.getPublisherId());
        return bookDAO.update(book);
    }
    public static BookDTO searchBook(String bookId) throws SQLException {
        Book book = bookDAO.find(bookId);
        if (book != null) {
            return new BookDTO(book.getBookId(),book.getTitle(),book.getSubject(),book.getAuthor(),book.getPrice(),book.getFullQty(),book.getQtyOnHand(),book.getPublisherId());
        }

        return null;
    }
    public static List<String> bookIdList() throws SQLException{
        List <String> ids=bookDAO.getAll();
        return  ids;
    }
}
