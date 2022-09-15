package lk.ijse.cmjd.project.business;

import lk.ijse.cmjd.project.dao.DAOFactory;
import lk.ijse.cmjd.project.dao.custom.BorrowDAO;
import lk.ijse.cmjd.project.dto.BorrowDTO;
import lk.ijse.cmjd.project.entity.Borrow;
import lk.ijse.cmjd.project.entity.BorrowPK;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageBorrowBusiness {
    private static BorrowDAO borrowDAO= (BorrowDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.BORROW);

    public static boolean addBorrow(BorrowDTO borrowDTO) throws SQLException {
        Borrow borrow=new Borrow(borrowDTO.getMemberId(),borrowDTO.getBookId(),java.sql.Date.valueOf(borrowDTO.getIssueDate()),java.sql.Date.valueOf(borrowDTO.getDueDate()));
        return borrowDAO.save(borrow);
    }
    public static List<BorrowDTO> getBorrows() throws SQLException {
        List<Borrow>allBorrows=borrowDAO.findAll();
        List<BorrowDTO>tmpDTOs=new ArrayList<>();
        for (Borrow borrow:allBorrows){
            BorrowDTO dto=new BorrowDTO(borrow.getMemberId(),borrow.getBookId(),borrow.getIssueDate().toLocalDate(),borrow.getDueDate().toLocalDate());
            tmpDTOs.add(dto);
        }
        return  tmpDTOs;
    }
    public static boolean deleteBorrow(BorrowPK borrowPK) throws SQLException {
        return borrowDAO.delete(borrowPK);
    }

}
