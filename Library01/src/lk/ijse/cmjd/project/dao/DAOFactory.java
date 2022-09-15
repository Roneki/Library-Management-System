package lk.ijse.cmjd.project.dao;

import lk.ijse.cmjd.project.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    public enum DAOTypes{
        MEMBER,BOOK,PUBLISHER,BORROW,RETURN,RENEW,FINEPAYMENT,LOSTPAYMENT,MEMBERSHIPFEE;
    }

    public DAOFactory() {
    }
    public static DAOFactory getInstance(){
        if (daoFactory==null){
            daoFactory=new DAOFactory();
        }
        return daoFactory;
    }
    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case MEMBER:
                return new MemberDAOImpl();
            case BOOK:
                return new BookDAOImpl();
            case PUBLISHER:
                return new PublisherDetailsDAOImpl();
            case BORROW:
                return new BorrowDAOImpl();
            case RETURN:
                return new ReturnDAOImpl();
            case RENEW:
                return new RenewDAOImpl();
            case FINEPAYMENT:
                return new FinePaymentDAOImpl();
            case LOSTPAYMENT:
                return new LostPaymentDAOImpl();
            case MEMBERSHIPFEE:
                return new MembershipFeeDAOImpl();

                default:
                    return null;
        }
    }
}
