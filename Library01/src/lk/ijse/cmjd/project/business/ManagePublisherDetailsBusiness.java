package lk.ijse.cmjd.project.business;

import lk.ijse.cmjd.project.dao.DAOFactory;
import lk.ijse.cmjd.project.dao.custom.PublisherDetailsDAO;
import lk.ijse.cmjd.project.dto.PublisherDetailsDTO;
import lk.ijse.cmjd.project.entity.Publisher;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagePublisherDetailsBusiness {
    private static PublisherDetailsDAO publisherDAO= (PublisherDetailsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PUBLISHER);

    public static boolean addPublisher(PublisherDetailsDTO publisherDetailsDTO) throws SQLException {
        Publisher publisher=new Publisher(publisherDetailsDTO.getId(),publisherDetailsDTO.getName(),publisherDetailsDTO.getAddress(),publisherDetailsDTO.getTeleNo());
        return publisherDAO.save(publisher);
    }
    public static List<PublisherDetailsDTO> getPublishers() throws SQLException {
        List<Publisher>allPublishers=publisherDAO.findAll();
        List<PublisherDetailsDTO>tmpDTOs=new ArrayList<>();
        for (Publisher publisher:allPublishers){
            PublisherDetailsDTO dto=new PublisherDetailsDTO(publisher.getId(),publisher.getName(),publisher.getAddress(),publisher.getTeleNo());

            tmpDTOs.add(dto);
        }
        return  tmpDTOs;
    }
    public static boolean deletePublisher(String publisherID) throws SQLException {
        return publisherDAO.delete(publisherID);
    }
    public static boolean   updatePublisher(PublisherDetailsDTO dto) throws SQLException {
        Publisher publisher=new Publisher(dto.getId(),dto.getName(),dto.getAddress(),dto.getTeleNo());
        return publisherDAO.update(publisher);
    }
    public static PublisherDetailsDTO searchPublisher(String publisherId) throws SQLException {
        Publisher publisher = publisherDAO.find(publisherId);
        if (publisher != null) {
            return new PublisherDetailsDTO(publisher.getId(),publisher.getName(),publisher.getAddress(),publisher.getTeleNo());
        }

        return null;
    }
    public static List<String> publisherIdList() throws SQLException{
        List <String> ids=publisherDAO.getAll();
        return  ids;
    }
}
