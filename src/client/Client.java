package client;

import naming.INamingService;
import naming.NamingServer;
import rental.*;
import session.*;
import sessionMaster.ISessionMaster;
import sessionMaster.SessionServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class Client extends AbstractTestManagement<IReservationSession, IManagerSession> {

    private ISessionMaster sessionMaster;

    public static void main(String[] args) throws Exception {
        System.setSecurityManager(null);

        NamingServer.main(args);
        SessionServer.main(args);

        Registry registry = LocateRegistry.getRegistry("localhost");
        ISessionMaster sessionMaster = (ISessionMaster) registry.lookup("master");

        Client client = new Client("trips", sessionMaster);

        INamingService naming = (INamingService) registry.lookup("naming");
        ICarRentalCompany crc1 = (ICarRentalCompany) registry.lookup("Hertz");
        ICarRentalCompany crc2 = (ICarRentalCompany) registry.lookup("Dockx");
        naming.registerCompany("Hertz", crc1);
        naming.registerCompany("Dockx", crc2);
        System.out.println("Companies have been registered.");

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
        return sessionMaster.getManagerSession("MANAGER_" + name + "_" + carRentalName, carRentalName);
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
        return session.getNbReservationCarType(carRentalName, carType);
    }


    @Override
    protected Set<String> getBestClients(IManagerSession ms) throws Exception {
        return ms.getBestRenters();
    }

    @Override
    protected String getCheapestCarType(IReservationSession iReservationSession, Date start, Date end, String region) throws Exception {
        return iReservationSession.getCheapestCarType(start,end).toString();
    }

    @Override
    protected CarType getMostPopularCarTypeIn(IManagerSession ms, String carRentalCompanyName, int year) throws Exception {
        return ms.getMostPopularCarType(carRentalCompanyName,year);
    }
}
