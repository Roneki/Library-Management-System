package lk.ijse.cmjd.project.dao.custom.impl;

import lk.ijse.cmjd.project.dao.custom.RenewDAO;
import lk.ijse.cmjd.project.db.DBConnection;
import lk.ijse.cmjd.project.entity.Renew;
import lk.ijse.cmjd.project.entity.RenewPK;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RenewDAOImpl implements RenewDAO {

    @Override
    public Renew find(RenewPK key) throws SQLException {
        return null;
    }

    @Override
    public List<Renew> findAll() throws SQLException {
        ArrayList<Renew> allRenews = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM renew");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()) {
            String memberId = rst.getString(1);
            String bookId = rst.getString(2);
            Date date=rst.getDate(3);
            Date dueDate=rst.getDate(4);
         Renew renew=new Renew(memberId,bookId,date,dueDate);
            allRenews.add(renew);
        }
        return allRenews;
    }

    @Override
    public boolean save(Renew entity) throws SQLException {
        Connection connection= DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("Insert into renew values (?,?,?,?)");
        pstm.setObject(1,entity.getMemberId());
        pstm.setObject(2,entity.getBookId());
        pstm.setObject(3,entity.getDate());
        pstm.setObject(4,entity.getDueDate());
        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean update(Renew entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(RenewPK key) throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("delete from renew where memberId=? and bookId=?and Date=?");
        pstm.setObject(1,key.getMemberId());
        pstm.setObject(2,key.getBookId());
        pstm.setObject(3,key.getDate());
        return  pstm.executeUpdate()>0;
    }

    @Override
    public List<String> getAll() throws SQLException {
        return null;
    }
}
