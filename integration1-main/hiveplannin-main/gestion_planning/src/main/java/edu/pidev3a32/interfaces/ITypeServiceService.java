package edu.pidev3a32.interfaces;



import edu.pidev3a32.entities.TypeService;

import java.sql.SQLException;
import java.util.List;

public interface ITypeServiceService {
    void addTypeService(TypeService typeService) throws SQLException;
    List<TypeService> getAllTypeServices() throws SQLException;
}