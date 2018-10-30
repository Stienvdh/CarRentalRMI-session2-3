package client;

import naming.NamingServer;
import rental.*;
import session.*;
import sessionMaster.ISessionMaster;
import sessionMaster.SessionServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Client extends AbstractTestAgency<IReservationSession, IManagerSession> {

    private ISessionMaster sessionMaster;

    public static void main(String[] args) throws Exception {
        System.setSecurityManager(null);

        NamingServer.main(args);
        SessionServer.main(args);

        Registry registry = LocateRegistry.getRegistry("localhost");
        ISessionMaster sessionMaster = (ISessionMaster) registry.lookup("master");

        Client client = new Client("trips", sessionMaster);

        client.run();
    }

    public Client(String scriptFile, ISessionMaster sessionMaster) {
        super(scriptFile);
        this.sessionMaster = sessionMaster;
    }

    @Override
    protected IReservationSession getNewReservationSession(String name) throws Exception {
        return sessionMaster.getReservationSession("RESERVATION_" + name, name);
    }

    @Override
    protected IManagerSession getNewManagerSession(String name, String carRentalName) throws Exception {
        return sessionMaster.getManagerSession("MANAGER_" + name + "_" + carRentalName);
    }

    @Override
    protected void checkForAvailableCarTypes(IReservationSession session, Date start, Date end) throws Exception {
        Set<CarType> result = session.getAvailableCarTypes(start,end);
        for (CarType carType : result) {
            System.out.println(carType.toString());
        }
    }

    @Override
    protected void addQuoteToSession(IReservationSession session, String name, Date start, Date end, String carType, String region) throws Exception {
        ReservationConstraints constraints = new ReservationConstraints(start, end, carType, region);
        session.createQuote(constraints, name);
    }

    @Override
    protected List<Reservation> confirmQuotes(IReservationSession session, String name) throws Exception {
        return session.confirmQuotes();
    }

    @Override
    protected int getNumberOfReservationsBy(IManagerSession session, String clientName) throws Exception {
        return session.getNumberReservationsBy(clientName);
    }

    @Override
    protected int getNumberOfReservationsForCarType(IManagerSession session, String carRentalName, String carType) throws Exception {
        Map<String, Integer> carTypes = session.getNbReservationCarType(carRentalName);
        return carTypes.get(carType);
    }
}
