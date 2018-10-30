package naming;

import rental.ICarRentalCompany;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NamingService implements INamingService {

    private Map<String, ICarRentalCompany> companies = new HashMap<String, ICarRentalCompany>();

    public List<ICarRentalCompany> getAllCompanies() {
        List<ICarRentalCompany> result = new ArrayList<ICarRentalCompany>();
        for (ICarRentalCompany crc : this.companies.values()) {
            result.add(crc);
        }
        return result;
    }

    public List<String> getAllCompanyNames() {
        List<String> result = new ArrayList<String>();
        for (String crc : this.companies.keySet()) {
            result.add(crc);
        }
        return result;
    }

    public ICarRentalCompany getCompany(String companyName) throws RemoteException, IllegalArgumentException {
        if (! this.getAllCompanyNames().contains(companyName)) {
            throw new IllegalArgumentException("Company " + companyName + " is not registered.");
        }
        return this.companies.get(companyName);
    }

    public void registerCompany(String companyName, ICarRentalCompany company) throws RemoteException {
        this.companies.put(companyName, company);
    }

    public void unregisterCompany(String companyName) throws RemoteException, IllegalArgumentException {
        if (! this.getAllCompanyNames().contains(companyName)) {
            throw new IllegalArgumentException("Company" + companyName + "is not registered.");
        }
        this.companies.remove(companyName);
    }

}
