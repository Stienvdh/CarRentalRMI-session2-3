package client;

import rental.*;
import session.*;
import sessionMaster.ISessionMaster;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Client extends AbstractTestAgency<IReservationSession, IManagerSession> {

    ISessionMaster sessionMaster;

    public Client(String scriptFile, ISessionMaster sessionMaster) {
        super(scriptFile);
        this.sessionMaster = sessionMaster;
    }


    @Override
    protected IReservationSession getNewReservationSession(String name) throws Exception {
        return null;
    }

    @Override
    protected IManagerSession getNewManagerSession(String name, String carRentalName) throws Exception {
        return null;
    }

    @Override
    protected void checkForAvailableCarTypes(IReservationSession iReservationSession, Date start, Date end) throws Exception {
        Set<CarType> result = iReservationSession.getAvailableCarTypes(start,end);
        for (CarType carType : result) {
            System.out.println(carType.toString());
        }
    }

    @Override
    protected void addQuoteToSession(IReservationSession iReservationSession, String name, Date start, Date end, String carType, String region) throws Exception {

    }

    @Override
    protected List<Reservation> confirmQuotes(IReservationSession iReservationSession, String name) throws Exception {
        return null;
    }

    @Override
    protected int getNumberOfReservationsBy(IManagerSession ms, String clientName) throws Exception {
        return 0;
    }

    @Override
    protected int getNumberOfReservationsForCarType(IManagerSession ms, String carRentalName, String carType) throws Exception {
        return 0;
    }
}
