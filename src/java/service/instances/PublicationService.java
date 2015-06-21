package service.instances;

import java.util.List;
import model.dao.PublicationDAO;
import model.dto.PublicationDTO;

public class PublicationService{
    private PublicationDAO publicationDAO;

    public PublicationService() {
        this.publicationDAO = new PublicationDAO();
    }
    
    //getPublications
    public List<PublicationDTO> getPublications(short id_muro) {
        return publicationDAO.getPublications(id_muro);
    }
    
    //insert publication
    public boolean insertPublication(String texto, short id_muro, String id_user){
        return publicationDAO.insertPublication(texto, id_muro, id_user);
    }
    
    //delete publication
    public boolean deletePublication(int id_publication){
        return publicationDAO.deletePublication(id_publication);
    }
    
    //habilita-deshabilitar publicacion
    public boolean enableDisablePublication(int id_publication, boolean enable){
        return publicationDAO.enableDisablePublication(id_publication, enable);
    }
}
