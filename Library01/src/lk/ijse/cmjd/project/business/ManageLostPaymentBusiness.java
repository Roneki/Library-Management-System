package lk.ijse.cmjd.project.business;

import lk.ijse.cmjd.project.dao.DAOFactory;
import lk.ijse.cmjd.project.dao.custom.LostPaymentDAO;
import lk.ijse.cmjd.project.dto.LostPaymentDTO;
import lk.ijse.cmjd.project.entity.LostPayment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageLostPaymentBusiness {
    private static LostPaymentDAO lostPaymentDAO= (LostPaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.LOSTPAYMENT);

    public static boolean addLostPayment(LostPaymentDTO lostPaymentDTO) throws SQLException {
        LostPayment lostPayment=new LostPayment(lostPaymentDTO.getNumber(),lostPaymentDTO.getBookId(),lostPaymentDTO.getMemberId(),lostPaymentDTO.getBookPrice(),lostPaymentDTO.getPayment());
        return lostPaymentDAO.save(lostPayment);
    }
    public static List<LostPaymentDTO> getLostPaymentDTOList() throws SQLException {
        List<LostPayment>allLosts=lostPaymentDAO.findAll();
        List<LostPaymentDTO>tmpDTOs=new ArrayList<>();
        for (LostPayment lostPayment:allLosts){
            LostPaymentDTO dto=new LostPaymentDTO(lostPayment.getNumber(),lostPayment.getBookId(),lostPayment.getMemberId(),lostPayment.getBookPrice(),lostPayment.getPayment());
            tmpDTOs.add(dto);
        }
        return  tmpDTOs;
    }
    public static boolean deleteLostPayment(String number) throws SQLException {
        return lostPaymentDAO.delete(number);
    }

}
