package naming;

import rental.ICarRentalCompany;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

public interface INamingService extends Remote {

    List<ICarRentalCompany> getAllCompanies() throws RemoteException;
    ICarRentalCompany getCompany(String companyName) throws RemoteException, IllegalArgumentException;
    void registerCompany(String companyName, ICarRentalCompany company) throws RemoteException;
    void unregisterCompany(String companyName) throws RemoteException, IllegalArgumentException;
    List<String> getAllCompanyNames() throws RemoteException;
}
