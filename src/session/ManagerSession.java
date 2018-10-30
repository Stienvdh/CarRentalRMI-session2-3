package session;

import naming.INamingService;
import naming.NamingServer;
import rental.*;

import javax.print.DocFlavor;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ManagerSession implements IManagerSession {

    private INamingService namingService;
    private String sessionid;
    private String carRentalName;

    public ManagerSession(INamingService namingService, String sessionid, String carRentalName) {
        this.sessionid = sessionid;
        this.namingService = namingService;
        this.carRentalName = carRentalName;
    }

    private INamingService getNamingService() {
        return this.namingService;
    }
    private String getCarRentalName() {return this.carRentalName;}
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
    public Map<String, ICarRentalCompany> getAllRegisteredCompanies() throws RemoteException {
        return this.getNamingService().getAllCompanies();
    }

    @Override
    public List<CarType> getCarTypes(String rentalCompany) throws RemoteException {
        ICarRentalCompany company = this.getNamingService().getCompany(rentalCompany);
        return new ArrayList<CarType>(company.getAllCarTypes());
    }

    @Override
    public String getBestRenter(String rentalCompany) throws RemoteException {
        Map<String, Integer> result = new HashMap<String, Integer>();
        ICarRentalCompany company = this.getNamingService().getCompany(rentalCompany);
        for (Car car: company.getAllCars()) {
            for (Reservation res: car.getReservations()) {
                result.put(res.getCarRenter(),company.getReservationsBy(res.getCarRenter()).size());
            }
        }
        Integer max = Collections.max(result.values());
        for (String renter: result.keySet()) {
            if (result.get(renter).equals(max)) {
                return renter;
            }
        }
        return null;
    }

    @Override
    public Map<String,Integer> getNbReservationCarType(String carRentalCompany) throws RemoteException {
        Map<String, Integer> result = new HashMap<String,Integer>();
        ICarRentalCompany company = this.getNamingService().getCompany(carRentalCompany);
        for (CarType carType : getCarTypes(carRentalCompany)) {
            result.put(carType.getName(), 0);
        }
        for (Car car : company.getAllCars()) {
            for (Reservation res : car.getReservations()){
                int value = result.get(car.getType().getName());
                result.put(car.getType().getName(), value+1);
            }
        }
        return result;
    }

    @Override
    public int getNumberReservationsBy(String clientName) throws RemoteException {
        int counter = 0;
        Map<String, ICarRentalCompany> rentals = this.getNamingService().getAllCompanies();
        for (ICarRentalCompany crc: rentals.values()) {
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
