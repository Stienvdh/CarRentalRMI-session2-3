package session;

import rental.CarType;
import rental.ICarRentalCompany;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

public interface IManagerSession extends Remote {

    String getCarRentalName() throws RemoteException;
    void registerCompany(String companyName, ICarRentalCompany company) throws RemoteException;
    void unregisterCompany(String companyName) throws RemoteException;
    public List<ICarRentalCompany> getAllRegisteredCompanies() throws RemoteException;
    List<CarType> getCarTypes(String rentalCompany) throws RemoteException;
    Set<String> getBestRenters() throws RemoteException;
    int getNbReservationCarType(String carRentalCompany, String carType) throws RemoteException;
    public int getNumberReservationsBy(String clientName) throws RemoteException;
    CarType getMostPopularCarType(String carRentalCompany, int year) throws RemoteException;
}
