package lk.ijse.cmjd.project.business;

import lk.ijse.cmjd.project.dao.DAOFactory;
import lk.ijse.cmjd.project.dao.custom.MembershipFeeDAO;
import lk.ijse.cmjd.project.dto.MembershipFeeDTO;
import lk.ijse.cmjd.project.entity.MembershipFee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageMembershipFeeBusiness {
    private static MembershipFeeDAO membershipFeeDAO= (MembershipFeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.MEMBERSHIPFEE);

    public static boolean addMembershipFee(MembershipFeeDTO membershipFeeDTO) throws SQLException {
        MembershipFee membershipFee=new MembershipFee(membershipFeeDTO.getMemberId(),membershipFeeDTO.getYearlyAmount(),java.sql.Date.valueOf(membershipFeeDTO.getDateOfPayment()));
        return membershipFeeDAO.save(membershipFee);
    }
    public static List<MembershipFeeDTO> getMembershipFees() throws SQLException {
        List<MembershipFee>allMembershipFees=membershipFeeDAO.findAll();
        List<MembershipFeeDTO>tmpDTOs=new ArrayList<>();
        for (MembershipFee membershipFee:allMembershipFees){
            MembershipFeeDTO dto=new MembershipFeeDTO(membershipFee.getMemberId(),membershipFee.getYearlyAmount(),membershipFee.getDateOfPayment().toLocalDate());
            tmpDTOs.add(dto);
        }
        return  tmpDTOs;
    }
    public static boolean deleteMembershipFee(String memberID) throws SQLException {
        return membershipFeeDAO.delete(memberID);
    }

}
