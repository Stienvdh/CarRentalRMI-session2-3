package session;

import naming.INamingService;
import rental.*;

import java.rmi.RemoteException;
import java.util.*;

public class ReservationSession extends Session implements IReservationSession {

    private String clientName;
    public List<Quote> currentQuotes = new ArrayList<Quote>();

    public ReservationSession(INamingService namingService, String sessionId, String clientName) {
        super(namingService, sessionId);
        this.clientName = clientName;
    }

    @Override
    public String getClientName() throws RemoteException {
        return this.clientName;
    }

    @Override
    public Quote createQuote(ReservationConstraints constraints, String clientName) throws ReservationException, RemoteException {
        INamingService namingService = this.getNamingService();
        Collection<String> companies = namingService.getAllCompanyNames();
        for (String str: companies) {
            ICarRentalCompany company = namingService.getCompany(str);
            try {
                Quote quote = company.createQuote(constraints, this.getClientName());
                currentQuotes.add(quote);
                return quote;
            }
            catch (Exception ex) {
            }
        }
        rollback();
        return null;
    }

    private void rollback() throws RemoteException {
        getCurrentQuotes().clear();
        System.out.println("Reservation failed, all current quotes are undone.");
    }

    @Override
    public List<Quote> getCurrentQuotes() throws RemoteException {
        return this.currentQuotes;
    }

    @Override
    public List<Reservation> confirmQuotes() throws ReservationException, RemoteException {
        List<Reservation> confirmedReservations = new ArrayList<Reservation>();
        for (Quote quote: getCurrentQuotes()) {
            String companyName = quote.getRentalCompany();
            ICarRentalCompany company = this.getNamingService().getCompany(companyName);
            try {
                Reservation res = company.confirmQuote(quote);
                confirmedReservations.add(res);
            }
            catch (ReservationException ex) {
                for (Reservation res : confirmedReservations) {
                    company.cancelReservation(res);
                }
                this.currentQuotes = new ArrayList<Quote>();
                throw new ReservationException("Cannot confirm all reservations");
            }
        }
        this.currentQuotes = new ArrayList<Quote>();
        return confirmedReservations;
    }

    @Override
    public Set<CarType> getAvailableCarTypes(Date start, Date end) throws RemoteException {
        Set<CarType> available = new HashSet<CarType>();
        List<String> companies = getNamingService().getAllCompanyNames();
        for (String company : companies) {
            for (Car car : getNamingService().getCompany(company).getAllCars()) {
                if (car.isAvailable(start, end)) {
                    available.add(car.getType());
                }
            }
        }
        return available;
    }

    @Override
    public CarType getCheapestCarType(Date start, Date end, String region) throws RemoteException {
        List<CarType> types = new ArrayList<CarType>();
        List<ICarRentalCompany> companies = this.getNamingService().getAllCompanies();
        for (ICarRentalCompany comp : companies) {
            if (comp.getRegions().contains(region)) {
                types.addAll(comp.getAvailableCarTypes(start,end));
            }
        }

        CarType cheapestType = null;
        double cheapest = Double.POSITIVE_INFINITY;
        for (CarType carType : types) {
            double price = carType.getRentalPricePerDay();
            if (price < cheapest) {
                cheapest = price;
                cheapestType = carType;
            }
        }
        return cheapestType;
    }

}
