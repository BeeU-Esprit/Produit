package edu.pidev3a32.interfaces;



import edu.pidev3a32.entities.Service;

import java.sql.SQLException;
import java.util.List;

public interface IServiceService {
    void addService(Service service) throws SQLException;
    void deleteService(int idService) throws SQLException;
    void updateService(Service service) throws SQLException;
    List<Service> getAllServices() throws SQLException;
}