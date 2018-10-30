package session;

import naming.INamingService;
import rental.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public class ManagerSession implements IManagerSession {

    private INamingService namingService;

    private INamingService getNamingService() {
        return this.namingService;
    }

    public void registerCompany(String companyName, ICarRentalCompany company) throws RemoteException {
        this.getNamingService().registerCompany(companyName, company);
    }

    public void unregisterCompany(String companyName) throws RemoteException {
        this.getNamingService().unregisterCompany(companyName);
    }

    public Map<String, ICarRentalCompany> getAllRegisteredCompanies() throws RemoteException {
        return this.getNamingService().getAllCompanies();
    }

    public List<CarType> getCarTypes(String rentalCompany) throws RemoteException {
        ICarRentalCompany company = this.getNamingService().getCompany(rentalCompany);
        return new ArrayList<CarType>(company.getAllCarTypes());
    }

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

    public int getNumberReservationsBy(String clientName) throws RemoteException {
        int counter = 0;
        Map<String, ICarRentalCompany> rentals = this.getNamingService().getAllCompanies();
        for (ICarRentalCompany crc: rentals.values()) {
            counter += crc.getReservationsBy(clientName).size();
        }
        return counter;
    }

}
