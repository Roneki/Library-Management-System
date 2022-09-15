package lk.ijse.cmjd.project.business;

import lk.ijse.cmjd.project.dao.DAOFactory;
import lk.ijse.cmjd.project.dao.custom.MemberDAO;
import lk.ijse.cmjd.project.dto.MemberDTO;
import lk.ijse.cmjd.project.entity.Member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class  ManageMemberBusiness {
    private static MemberDAO memberDAO= (MemberDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.MEMBER);

    public static boolean addMember(MemberDTO memberDTO) throws SQLException {
        Member member=new Member(memberDTO.getMemberId(),memberDTO.getName(),memberDTO.getAddress(),memberDTO.getMemberType(),java.sql.Date.valueOf(memberDTO.getMembershipDate()));
        return memberDAO.save(member);
    }
    public static List<MemberDTO> getMembers() throws SQLException {
        List<Member>allMembers=memberDAO.findAll();
        List<MemberDTO>tmpDTOs=new ArrayList<>();
        for (Member member:allMembers){
            MemberDTO dto=new MemberDTO(member.getMemberId(),member.getName(),member.getAddress(),member.getMemberType(),member.getMembershipDate().toLocalDate());
            tmpDTOs.add(dto);
        }
        return  tmpDTOs;
    }
    public static boolean deleteMember(String memberID) throws SQLException {
        return memberDAO.delete(memberID);
    }
    public static boolean   updateMember(MemberDTO dto) throws SQLException {
        Member member=new Member(dto.getMemberId(),dto.getName(),dto.getAddress(),dto.getMemberType(),java.sql.Date.valueOf(dto.getMembershipDate()));
return memberDAO.update(member);
    }
    public static MemberDTO searchMember(String memberId) throws SQLException {
        Member member = memberDAO.find(memberId);
        if (member != null) {
            return new MemberDTO(member.getMemberId(), member.getName(), member.getAddress(), member.getMemberType(), member.getMembershipDate().toLocalDate());
        }

return null;
    }
    public static List<String> memberIdList() throws SQLException{
        List <String> ids=memberDAO.getAll();
        return  ids;
    }
}
