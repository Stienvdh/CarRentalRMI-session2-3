package client;

import rental.*;
import session.*;
import sessionMaster.ISessionMaster;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Client extends AbstractTestAgency<IReservationSession, IManagerSession> {

    ISessionMaster sessionMaster;

    public Client(String scriptFile, ISessionMaster sessionMaster) {
        super(scriptFile);
        this.sessionMaster = sessionMaster;
    }


    @Override
    protected IReservationSession getNewReservationSession(String name) throws Exception {
        return sessionMaster.getReservationSession("reservation_" + name, name);
    }

    @Override
    protected IManagerSession getNewManagerSession(String name, String carRentalName) throws Exception {
        return sessionMaster.getManagerSession("manager_" + name);
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
        ReservationConstraints constraints = new ReservationConstraints(start, end, carType, region);
        iReservationSession.createQuote(constraints, name);
    }

    @Override
    protected List<Reservation> confirmQuotes(IReservationSession iReservationSession, String name) throws Exception {
        return iReservationSession.confirmQuotes();
    }

    @Override
    protected int getNumberOfReservationsBy(IManagerSession ms, String clientName) throws Exception {
        return ms.getNumberReservationsBy(clientName);
    }

    @Override
    protected int getNumberOfReservationsForCarType(IManagerSession ms, String carRentalName, String carType) throws Exception {
        Map<String, Integer> carTypes = ms.getNbReservationCarType(carRentalName);
        return carTypes.get(carType);
    }
}
