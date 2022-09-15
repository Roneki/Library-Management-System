package lk.ijse.cmjd.project.dao.custom.impl;

import lk.ijse.cmjd.project.dao.custom.LostPaymentDAO;
import lk.ijse.cmjd.project.db.DBConnection;
import lk.ijse.cmjd.project.entity.LostPayment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LostPaymentDAOImpl  implements LostPaymentDAO {
    @Override
    public LostPayment find(String key) throws SQLException {
        return null;
    }

    @Override
    public List<LostPayment> findAll() throws SQLException {
        ArrayList<LostPayment> allLostPayments = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM lostbookpayment");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()) {
            int number=rst.getInt(1);
            String bookId = rst.getString(2);
            String memberId = rst.getString(3);
            double bookPrice=rst.getDouble(4);
            double payment=rst.getDouble(5);
        LostPayment lostPayment=new LostPayment(number,bookId,memberId,bookPrice,payment);
            allLostPayments.add(lostPayment);
        }
        return allLostPayments;
    }

    @Override
    public boolean save(LostPayment entity) throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("Insert into lostbookpayment values (?,?,?,?,?)");
        pstm.setObject(1,entity.getNumber());
        pstm.setObject(2,entity.getBookId());
        pstm.setObject(3,entity.getMemberId());
        pstm.setObject(4,entity.getBookPrice());
        pstm.setObject(5,entity.getPayment());
        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean update(LostPayment entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String key) throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("delete from lostbookpayment where number=?");
        pstm.setObject(1,key);
        return  pstm.executeUpdate()>0;
    }

    @Override
    public List<String> getAll() throws SQLException {
        return null;
    }
}
