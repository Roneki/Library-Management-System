package lk.ijse.cmjd.project.dao.custom.impl;

import lk.ijse.cmjd.project.dao.custom.BorrowDAO;
import lk.ijse.cmjd.project.db.DBConnection;
import lk.ijse.cmjd.project.entity.Borrow;
import lk.ijse.cmjd.project.entity.BorrowPK;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAOImpl implements BorrowDAO {
    @Override
    public Borrow find(BorrowPK key) throws SQLException {
        return null;
    }

    @Override
    public List<Borrow> findAll() throws SQLException {
        ArrayList<Borrow> allBorrows = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM burrow");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()) {
            String memberId = rst.getString(1);
            String bookId = rst.getString(2);
            Date issueDate = rst.getDate(3);
            Date dueDate = rst.getDate(4);

            Borrow borrow=new Borrow(memberId,bookId,issueDate,dueDate);
            allBorrows.add(borrow);
        }
        return allBorrows;
    }

    @Override
    public boolean save(Borrow entity) throws SQLException {
        Connection connection= DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("Insert into burrow values (?,?,?,?)");
        pstm.setObject(1,entity.getMemberId());
        pstm.setObject(2,entity.getBookId());
        pstm.setObject(3,entity.getIssueDate());
        pstm.setObject(4,entity.getDueDate());
        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean update(Borrow entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(BorrowPK borrowPK) throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("delete from burrow where memberId=? and bookId=? and issueDate=?");
        pstm.setObject(1,borrowPK.getMemberId());
        pstm.setObject(2,borrowPK.getBookId());
        pstm.setObject(3,borrowPK.getIssueDate());
        return  pstm.executeUpdate()>0;
    }

    @Override
    public List<String> getAll() throws SQLException {
        return null;
    }
}
