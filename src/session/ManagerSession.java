package session;

import naming.INamingService;
import rental.*;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ManagerSession implements IManagerSession {

    private INamingService namingService;
    private String sessionid;
    public String carRentalName;

    public ManagerSession(INamingService namingService, String sessionid, String carRentalName) {
        this.sessionid = sessionid;
        this.namingService = namingService;
        this.carRentalName = carRentalName;
    }

    private INamingService getNamingService() {
        return this.namingService;
    }

    @Override
    public String getCarRentalName() throws RemoteException {return this.carRentalName;}
    private String getSessionid() {return this.sessionid;}


    @Override
    public void registerCompany(String companyName, ICarRentalCompany company) throws RemoteException {
        this.getNamingService().registerCompany(companyName, company);
    }

    @Override
    public void unregisterCompany(String companyName) throws RemoteException {
        this.getNamingService().unregisterCompany(companyName);
    }

    @Override
    public List<ICarRentalCompany> getAllRegisteredCompanies() throws RemoteException {
        return this.getNamingService().getAllCompanies();
    }

    @Override
    public List<CarType> getCarTypes(String rentalCompany) throws RemoteException {
        ICarRentalCompany company = this.getNamingService().getCompany(rentalCompany);
        return new ArrayList<CarType>(company.getAllCarTypes());
    }

    @Override
    public Set<String> getBestRenters() throws RemoteException {
        Map<String, Integer> result = new HashMap<String, Integer>();

        for (ICarRentalCompany crc : getNamingService().getAllCompanies()) {
            for (Car car : crc.getAllCars()) {
                for (Reservation res : car.getReservations()) {
                    result.put(res.getCarRenter(),0);
                }
            }
        }

        for (String renter : result.keySet()) {
            for (ICarRentalCompany crc : getNamingService().getAllCompanies()) {
                int old = result.get(renter);
                result.put(renter, old + crc.getReservationsBy(renter).size());
            }
        }

        Set<String> bestRenters = new HashSet<String>();
        Integer max = Collections.max(result.values());
        for (String renter: result.keySet()) {
            if (result.get(renter).equals(max)) {
                bestRenters.add(renter);
            }
        }
        return bestRenters;
    }

    @Override
    public int getNbReservationCarType(String carRentalCompany, String carTypeName) throws RemoteException {
        Map<String, Integer> result = new HashMap<String,Integer>();
        ICarRentalCompany company = this.getNamingService().getCompany(carRentalCompany);
        int value = 0;
        for (Car car : company.getAllCars()) {
            for (Reservation res : car.getReservations()){
                if (car.getType().getName().equals(carTypeName)) {
                    value += 1;
                }
            }
        }
        return value;
    }

    @Override
    public int getNumberReservationsBy(String clientName) throws RemoteException {
        int counter = 0;
        List<ICarRentalCompany> rentals = this.getNamingService().getAllCompanies();
        for (ICarRentalCompany crc: rentals) {
            counter += crc.getReservationsBy(clientName).size();
        }
        return counter;
    }

    @Override
    public CarType getMostPopularCarType(String carRentalCompany, int year) throws RemoteException {
        ICarRentalCompany rentalCompany = namingService.getCompany(carRentalCompany);

        Map<CarType,Integer> nbReservations = new HashMap<CarType,Integer>();
        for (CarType carType : rentalCompany.getAllCarTypes()) {
            nbReservations.put(carType,0);
        }

        for (Car car : rentalCompany.getAllCars()) {
            for (Reservation reservation : car.getReservations()) {
                LocalDate localDate = reservation.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (localDate.getYear() == year) {
                    int oldValue = nbReservations.get(car.getType());
                    nbReservations.replace(car.getType(), oldValue, oldValue+1);
                }
            }
        }

        int max = 0;
        CarType maxType = null;
        for(CarType carType : nbReservations.keySet()) {
            if (nbReservations.get(carType) > max) {
                max = nbReservations.get(carType);
                maxType = carType;
            }
        }

        return maxType;
    }

}
