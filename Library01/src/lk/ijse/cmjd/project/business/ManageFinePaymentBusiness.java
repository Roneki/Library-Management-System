package lk.ijse.cmjd.project.business;

import lk.ijse.cmjd.project.dao.DAOFactory;
import lk.ijse.cmjd.project.dao.custom.FinePaymentDAO;
import lk.ijse.cmjd.project.dto.FinePaymentDTO;
import lk.ijse.cmjd.project.entity.FinePayment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageFinePaymentBusiness {
    private static FinePaymentDAO finePaymentDAO= (FinePaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.FINEPAYMENT);

    public static boolean addFinePayment(FinePaymentDTO finePaymentDTO) throws SQLException {
        FinePayment finePayment =new FinePayment(finePaymentDTO.getFineNo(),finePaymentDTO.getMemberId(),finePaymentDTO.getBookId(),finePaymentDTO.getNoOfDaysPassed(),finePaymentDTO.getAmount());
        return finePaymentDAO.save(finePayment);
    }
    public static List<FinePaymentDTO> getFinePaymentDTOList() throws SQLException {
        List<FinePayment>allFines=finePaymentDAO.findAll();
        List<FinePaymentDTO>tmpDTOs=new ArrayList<>();
        for (FinePayment finePayment:allFines){
            FinePaymentDTO dto=new FinePaymentDTO(finePayment.getFineNo(),finePayment.getBookId(),finePayment.getMemberId(),finePayment.getNoOfDaysPassed(),finePayment.getAmount());
            tmpDTOs.add(dto);
        }
        return  tmpDTOs;
    }
    public static boolean deleteFinePayment(String fineNo) throws SQLException {
        return finePaymentDAO.delete(fineNo);
    }

}
