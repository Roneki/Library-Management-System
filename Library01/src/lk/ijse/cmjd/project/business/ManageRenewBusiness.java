package lk.ijse.cmjd.project.business;

import lk.ijse.cmjd.project.dao.DAOFactory;
import lk.ijse.cmjd.project.dao.custom.RenewDAO;
import lk.ijse.cmjd.project.dto.RenewDTO;
import lk.ijse.cmjd.project.entity.BorrowPK;
import lk.ijse.cmjd.project.entity.Renew;
import lk.ijse.cmjd.project.entity.RenewPK;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageRenewBusiness {
    private static RenewDAO renewDAO= (RenewDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.RENEW);

    public static boolean addRenew(RenewDTO renewDTO) throws SQLException {
        Renew renew=new Renew(renewDTO.getMemberId(),renewDTO.getBookId(),java.sql.Date.valueOf(renewDTO.getDate()),java.sql.Date.valueOf(renewDTO.getDueDate()));
        return renewDAO.save(renew);
    }
    public static List<RenewDTO> getRenews() throws SQLException {
        List<Renew>allRenews=renewDAO.findAll();
        List<RenewDTO>tmpDTOs=new ArrayList<>();
        for (Renew renew:allRenews){
            RenewDTO dto=new RenewDTO(renew.getMemberId(),renew.getBookId(),renew.getDate().toLocalDate(),renew.getDueDate().toLocalDate());
            tmpDTOs.add(dto);
        }
        return  tmpDTOs;
    }
    public static boolean deleteRenew(RenewPK memberID) throws SQLException {
        return renewDAO.delete(memberID);
    }

}
