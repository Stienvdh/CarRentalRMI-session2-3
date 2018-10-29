package naming;

import rental.ICarRentalCompany;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface INamingService extends Remote {

    Map<String, ICarRentalCompany> getAllCompanies() throws RemoteException;
    ICarRentalCompany getCompany(String companyName) throws RemoteException;
    void registerCompany(String companyName, ICarRentalCompany company) throws RemoteException;
    void unregisterCompany(String companyName) throws RemoteException, IllegalArgumentException;

}
