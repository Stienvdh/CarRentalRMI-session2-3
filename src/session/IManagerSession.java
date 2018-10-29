package session;

import rental.CarType;
import rental.ICarRentalCompany;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IManagerSession extends Remote {

    void registerCompany(String companyName, ICarRentalCompany company) throws RemoteException;
    void unregisterCompany(String companyName) throws RemoteException;
    public Map<String, ICarRentalCompany> getAllRegisteredCompanies() throws RemoteException;
    List<CarType> getCarTypes(String rentalCompany) throws RemoteException;
    Map<String,Integer> getNbReservationCarType(String carRentalCompany) throws RemoteException;
    public int getNumberReservationsBy(String clientName) throws RemoteException;

}
