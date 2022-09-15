package lk.ijse.cmjd.project.dao.custom.impl;

import lk.ijse.cmjd.project.dao.custom.ReturnDAO;
import lk.ijse.cmjd.project.db.DBConnection;
import lk.ijse.cmjd.project.entity.Return;
import lk.ijse.cmjd.project.entity.ReturnPK;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

public class ReturnDAOImpl implements ReturnDAO {
   @Override
   public Return find(ReturnPK key) throws SQLException {
      return null;
   }

   @Override
   public List<Return> findAll() throws SQLException {
      ArrayList<Return> allReturns = new ArrayList<>();
      Connection connection = DBConnection.getConnection();
      PreparedStatement pstm = connection.prepareStatement("SELECT * FROM returns");
      ResultSet rst = pstm.executeQuery();
      while (rst.next()) {
         String memberId = rst.getString(1);
         String bookId = rst.getString(2);
         Date dueDate=rst.getDate(3);
         Date returnDate=rst.getDate(4);
         Boolean fineNeed=rst.getBoolean(5);
       Return aReturn=new Return(memberId,bookId,dueDate,returnDate,fineNeed);
         allReturns.add(aReturn);
      }
      return allReturns;
   }

   @Override
   public boolean save(Return entity) throws SQLException {
      Connection connection= DBConnection.getConnection();
      PreparedStatement pstm=connection.prepareStatement("Insert into returns values (?,?,?,?,?)");
      pstm.setObject(1,entity.getMemberId());
      pstm.setObject(2,entity.getBookId());
      pstm.setObject(3,entity.getDueDate());
      pstm.setObject(4,entity.getReturnDate());
      pstm.setObject(5,entity.isFineNeeded());
      return pstm.executeUpdate()>0;
   }

   @Override
   public boolean update(Return entity) throws SQLException {
      return false;
   }

   @Override
   public boolean delete(ReturnPK key) throws SQLException {
      Connection connection= DBConnection.getConnection();
      PreparedStatement pstm=connection.prepareStatement("delete from renew where memberId=? and bookId=?and dueDate=?");
      pstm.setObject(1,key.getMemberId());
      pstm.setObject(2,key.getBookId());
      pstm.setObject(3,key.getDueDate());
      return  pstm.executeUpdate()>0;
   }

   @Override
   public List<String> getAll() throws SQLException {
      return null;
   }
}
