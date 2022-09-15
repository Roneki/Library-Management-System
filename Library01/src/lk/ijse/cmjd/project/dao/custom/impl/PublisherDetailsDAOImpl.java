package lk.ijse.cmjd.project.dao.custom.impl;

import lk.ijse.cmjd.project.dao.custom.PublisherDetailsDAO;
import lk.ijse.cmjd.project.db.DBConnection;
import lk.ijse.cmjd.project.entity.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublisherDetailsDAOImpl implements PublisherDetailsDAO {
    @Override
    public Publisher find(String key) throws SQLException {
        return null;
    }

    @Override
    public List<Publisher> findAll() throws SQLException {
        ArrayList<Publisher> allPublishers= new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM publisher");
        ResultSet rst = pstm.executeQuery();
        while (rst.next()) {
            String publisherId = rst.getString(1);
            String name = rst.getString(2);
            String address = rst.getString(3);
           int tele=rst.getInt(4);
            Publisher publisher=new Publisher(publisherId,name,address,tele);
            allPublishers.add(publisher);
        }
        return allPublishers;
    }

    @Override
    public boolean save(Publisher entity) throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("Insert into publisher values (?,?,?,?)");
        pstm.setObject(1,entity.getName());
        pstm.setObject(2,entity.getId());
        pstm.setObject(3,entity.getAddress());
        pstm.setObject(4,entity.getTeleNo());

        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean update(Publisher entity) throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("Update publisher set name=?,address=?,teleNo=? where Id=?");
        pstm.setObject(1,entity.getName());
        pstm.setObject(2,entity.getAddress());
        pstm.setObject(3,entity.getTeleNo());
        pstm.setObject(4,entity.getId());


        return pstm.executeUpdate()>0;
    }

    @Override
    public boolean delete(String key) throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("delete from publisher where Id=?");
        pstm.setObject(1,key);
        return  pstm.executeUpdate()>0;
    }

    @Override
    public List<String> getAll() throws SQLException {
        Connection connection=DBConnection.getConnection();
        PreparedStatement pstm=connection.prepareStatement("SELECT Id from publisher");
        ResultSet rst=pstm.executeQuery();
        List <String> ids=new ArrayList<>();
        String id;
        if (rst.next()){
            id=rst.getString("Id");
            System.out.println(id);
            ids.add(id);
        }
        return ids;
    }
    }

