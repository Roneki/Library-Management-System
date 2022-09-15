package lk.ijse.cmjd.project.dao.custom.impl;

import lk.ijse.cmjd.project.dao.custom.MembershipFeeDAO;
import lk.ijse.cmjd.project.db.DBConnection;
import lk.ijse.cmjd.project.entity.MembershipFee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembershipFeeDAOImpl implements MembershipFeeDAO {
    @Override
    public MembershipFee find(String key) throws SQLException {
        return null;
    }

    @Override
    public List<MembershipFee> findAll() throws SQLException {
        ArrayList<MembershipFee> allMemberFees = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM membershipfee");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()) {
            String memberId = rst.getString(1);
           double amount=rst.getDouble(2);
            Date membershipFeeDate=rst.getDate(3);
          MembershipFee membershipFee=new MembershipFee(memberId,amount,membershipFeeDate);
            allMemberFees.add(membershipFee);
        }
        return allMemberFees;
    }

    @Override
    public boolean save(MembershipFee entity) throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("Insert into membershipfee values (?,?,?)");
        pstm.setObject(1,entity.getMemberId());
        pstm.setObject(2,entity.getYearlyAmount());
        pstm.setObject(3,entity.getDateOfPayment());
        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean update(MembershipFee entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String key) throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("delete from membershipfee where memberId=?");
        pstm.setObject(1,key);
        return  pstm.executeUpdate()>0;
    }

    @Override
    public List<String> getAll() throws SQLException {
        return null;
    }
}
