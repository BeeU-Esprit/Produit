/*package edu.emmapi.entities;

public class Service {
    private int idService;
    private String nomService;
    private String typeService;
    private String description;
    private boolean estDisponible;

    public Service() {}

    public Service(int idService, String nomService, String typeService, String description, boolean estDisponible) {
        this.idService = idService;
        this.nomService = nomService;
        this.typeService = typeService;
        this.description = description;
        this.estDisponible = estDisponible;
    }

    // Getters and Setters
    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getNomService() {
        return nomService;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
    }

    public String getTypeService() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEstDisponible() {
        return estDisponible;
    }

    public void setEstDisponible(boolean estDisponible) {
        this.estDisponible = estDisponible;
    }

    @Override
    public String toString() {
        return "Service{" +
                "idService=" + idService +
                ", nomService='" + nomService + '\'' +
                ", typeService='" + typeService + '\'' +
                ", description='" + description + '\'' +
                ", estDisponible=" + estDisponible +
                '}';
    }
}*/
package edu.pidev3a32.entities;

public class Service {
    private int id;
    private String nomService;
    private String description;
    private String typeService;

    // Constructeur
    public Service(int id, String nomService, String description, String typeService) {
        this.id = id;
        this.nomService = nomService;
        this.description = description;
        this.typeService = typeService;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomService() {
        return nomService;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeService() {
        return typeService;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", nomService='" + nomService + '\'' +
                ", description='" + description + '\'' +
                ", typeService='" + typeService + '\'' +
                '}';
    }
}
