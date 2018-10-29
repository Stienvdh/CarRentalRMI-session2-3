package naming;

import rental.ICarRentalCompany;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class NamingService implements INamingService {

    private Map<String, ICarRentalCompany> companies = new HashMap<String, ICarRentalCompany>();

    public Map<String, ICarRentalCompany> getAllCompanies() {
        return this.companies;
    }

    public ICarRentalCompany getCompany(String companyName) throws RemoteException{
        return this.getAllCompanies().getOrDefault(companyName, null);
    }

    public void registerCompany(String companyName, ICarRentalCompany company) throws RemoteException {
        this.getAllCompanies().put(companyName, company);
    }

    public void unregisterCompany(String companyName) throws RemoteException, IllegalArgumentException {
        if (! this.getAllCompanies().containsKey(companyName)) {
            throw new IllegalArgumentException("Company" + companyName + "is not registered.");
        }
        this.getAllCompanies().remove(companyName);
    }

}
