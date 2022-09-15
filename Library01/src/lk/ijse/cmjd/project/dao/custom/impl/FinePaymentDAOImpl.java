package lk.ijse.cmjd.project.dao.custom.impl;

import lk.ijse.cmjd.project.dao.custom.FinePaymentDAO;
import lk.ijse.cmjd.project.db.DBConnection;
import lk.ijse.cmjd.project.entity.FinePayment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FinePaymentDAOImpl implements FinePaymentDAO {
    @Override
    public FinePayment find(String key) throws SQLException {
        return null;
    }

    @Override
    public List<FinePayment> findAll() throws SQLException {
        ArrayList<FinePayment> allFine= new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM finepayment");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()) {
            int fineNo=rst.getInt(1);
            String bookId = rst.getString(2);
            String memberId = rst.getString(3);
           int days=rst.getInt(4);
            double amount=rst.getDouble(5);

FinePayment finePayment=new FinePayment(fineNo,bookId,memberId,days,amount);
            allFine.add(finePayment);
        }
        return allFine;
    }

    @Override
    public boolean save(FinePayment entity) throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("Insert into finepayment values (?,?,?,?,?)");
        pstm.setObject(1,entity.getFineNo());
        pstm.setObject(2,entity.getBookId());
        pstm.setObject(3,entity.getMemberId());
        pstm.setObject(4,entity.getNoOfDaysPassed());
        pstm.setObject(5,entity.getAmount());
        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean update(FinePayment entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String key) throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("delete from finepayment where fineNO=?");
        pstm.setObject(1,key);
        return  pstm.executeUpdate()>0;
    }

    @Override
    public List<String> getAll() throws SQLException {
        return null;
    }
}
