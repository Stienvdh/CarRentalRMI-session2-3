package session;

import rental.CarType;
import rental.ICarRentalCompany;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IManagerSession extends Remote {

    String getCarRentalName();

    void registerCompany(String companyName, ICarRentalCompany company) throws RemoteException;
    void unregisterCompany(String companyName) throws RemoteException;
    public Map<String, ICarRentalCompany> getAllRegisteredCompanies() throws RemoteException;
    List<CarType> getCarTypes(String rentalCompany) throws RemoteException;
    Set<String> getBestRenter(String rentalCompany) throws RemoteException;
    Map<String,Integer> getNbReservationCarType(String carRentalCompany) throws RemoteException;
    public int getNumberReservationsBy(String clientName) throws RemoteException;
    CarType getMostPopularCarType(String carRentalCompany, int year) throws RemoteException;
}
