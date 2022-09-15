package lk.ijse.cmjd.project.dao.custom.impl;

import lk.ijse.cmjd.project.dao.custom.BookDAO;
import lk.ijse.cmjd.project.db.DBConnection;
import lk.ijse.cmjd.project.entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {
    @Override
    public Book find(String id) throws SQLException {
        Connection connection= DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("SELECT * from  book where bookId=?");
        pstm.setObject(1,id);
        ResultSet rst=pstm.executeQuery();
        if (rst.next()){
            return new Book(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getDouble(5),rst.getInt(6),rst.getInt(7),rst.getString(8));

        }
        return null;

    }

    @Override
    public List<Book> findAll() throws SQLException {
        ArrayList<Book> allBooks = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM book");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()) {
            String bookId = rst.getString(1);
            String title = rst.getString(2);
            String subject = rst.getString(3);
            String author=rst.getString(4);
            double price=rst.getDouble(5);
            int fullQty=rst.getInt(6);
            int qtyOnHand=rst.getInt(7);
            String publisherId=rst.getString(8);
            Book book=new Book(bookId,title,subject,author,price,fullQty,qtyOnHand,publisherId);
            allBooks.add(book);
        }
        return allBooks;
    }

    @Override
    public boolean save(Book entity) throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("Insert into book values (?,?,?,?,?,?,?,?)");
        pstm.setObject(1,entity.getBookId());
        pstm.setObject(2,entity.getTitle());
        pstm.setObject(3,entity.getSubject());
        pstm.setObject(4,entity.getAuthor());
        pstm.setObject(5,entity.getPrice());
        pstm.setObject(6,entity.getFullQty());
        pstm.setObject(7,entity.getQtyOnHand());
        pstm.setObject(8,entity.getPublisherId());
        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean update(Book entity) throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("Update book set title=?,subject=?,author=?,price=?,fullQty=?,qtyOnHand=?,publisherId=? where bookId=?");
        pstm.setObject(1,entity.getTitle());
        pstm.setObject(2,entity.getSubject());
        pstm.setObject(3,entity.getAuthor());
        pstm.setObject(4,entity.getPrice());
        pstm.setObject(5,entity.getFullQty());
        pstm.setObject(6,entity.getQtyOnHand());
        pstm.setObject(7,entity.getPublisherId());
        pstm.setObject(8,entity.getBookId());
        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean delete(String key) throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("delete from book where bookIdId=?");
        pstm.setObject(1,key);
        return  pstm.executeUpdate()>0;
    }

    @Override
    public List<String> getAll() throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("SELECT bookId from book");
        ResultSet rst=pstm.executeQuery();
        List <String> bookIds=new ArrayList<>();
        String bookId;
        if (rst.next()){
            bookId=rst.getString("bookId");
            bookIds.add(bookId);
        }
        return bookIds;
    }
}
