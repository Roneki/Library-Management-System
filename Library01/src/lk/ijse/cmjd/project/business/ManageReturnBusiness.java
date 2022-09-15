package lk.ijse.cmjd.project.business;

import lk.ijse.cmjd.project.dao.DAOFactory;
import lk.ijse.cmjd.project.dao.custom.ReturnDAO;
import lk.ijse.cmjd.project.dto.ReturnDTO;
import lk.ijse.cmjd.project.entity.RenewPK;
import lk.ijse.cmjd.project.entity.Return;
import lk.ijse.cmjd.project.entity.ReturnPK;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageReturnBusiness {
    private static ReturnDAO returnDAO= (ReturnDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.RETURN);

    public static boolean addReturn(ReturnDTO returnDTO) throws SQLException {
        Return returns =new Return(returnDTO.getMemberId(),returnDTO.getBookId(),java.sql.Date.valueOf(returnDTO.getDueDate()),java.sql.Date.valueOf(returnDTO.getReturnDate()),returnDTO.isFineNeeded());
        return returnDAO.save(returns);
    }
    public static List<ReturnDTO> getReturns() throws SQLException {
        List<Return>allReturns=returnDAO.findAll();
        List<ReturnDTO>tmpDTOs=new ArrayList<>();
        for (Return aReturn:allReturns){
            ReturnDTO dto=new ReturnDTO(aReturn.getMemberId(),aReturn.getBookId(),aReturn.getDueDate().toLocalDate(),aReturn.getReturnDate().toLocalDate(),aReturn.isFineNeeded());
            tmpDTOs.add(dto);
        }
        return  tmpDTOs;
    }
    public static boolean deleteReturn(ReturnPK returnPK ) throws SQLException {
        return returnDAO.delete(returnPK);
    }

}
