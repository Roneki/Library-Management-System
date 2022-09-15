package lk.ijse.cmjd.project.dao.custom.impl;

import lk.ijse.cmjd.project.dao.custom.MemberDAO;
import lk.ijse.cmjd.project.db.DBConnection;
import lk.ijse.cmjd.project.entity.Member;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberDAOImpl implements MemberDAO {
    @Override
    public Member find(String memberId) throws SQLException {
        Connection connection= DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("SELECT * from  member where memberId=?");
        pstm.setObject(1,memberId);
        ResultSet rst=pstm.executeQuery();
        if (rst.next()){
        return new Member(rst.getString("memberId"),rst.getString("name"),rst.getString("address"),rst.getString("memberType"),rst.getDate("membershipDate"));

        }
        return null;
    }

    @Override
    public List<Member> findAll() throws SQLException {
        ArrayList<Member> allMembers = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM member");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()) {
            String memberId = rst.getString(1);
            String name = rst.getString(2);
            String address = rst.getString(3);
            String memberType=rst.getString(5);
           Date membershipDate=rst.getDate(4);
            Member member = new Member(memberId,name,address,memberType,membershipDate);
            allMembers.add(member);
        }
        return allMembers;
    }

    @Override
    public boolean save(Member entity) throws SQLException {
       Connection connection=DBConnection.getConnection();
       PreparedStatement pstm=connection.prepareStatement("Insert into member values (?,?,?,?,?)");
       pstm.setObject(1,entity.getMemberId());
       pstm.setObject(2,entity.getName());
       pstm.setObject(3,entity.getAddress());
       pstm.setObject(4,entity.getMembershipDate());
       pstm.setObject(5,entity.getMemberType());
       return pstm.executeUpdate()>0;
    }

    @Override
    public boolean update(Member entity) throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("Update member set name=?,address=?,membershipDate=?,memberType=? where memberId=?");
        pstm.setObject(1,entity.getName());
        pstm.setObject(2,entity.getAddress());
        pstm.setObject(3,entity.getMembershipDate());
        pstm.setObject(4,entity.getMemberType());
        pstm.setObject(5,entity.getMemberId());

        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean delete(String memberId) throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("delete from member where memberId=?");
        pstm.setObject(1,memberId);
        return  pstm.executeUpdate()>0;
    }

    @Override
    public List<String> getAll() throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("SELECT memberId from member");
        ResultSet rst=pstm.executeQuery();
        List <String> memberIds=new ArrayList<>();
        String memberId;
        if (rst.next()){
            memberId=rst.getString("memberId");
            memberIds.add(memberId);
        }
       return memberIds;
    }
}
