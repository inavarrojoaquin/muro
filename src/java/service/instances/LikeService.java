package service.instances;

import java.util.List;
import model.dao.LikeDAO;
import model.dto.LikeDTO;

public class LikeService {
private LikeDAO likeDAO;

    public LikeService() {
        this.likeDAO = new LikeDAO();
    }
    
    //return list of id_publication where the user clicked in 'Me gusta'
    public List<LikeDTO> getUserLike(String id_user){
        return likeDAO.getUserLike(id_user);
    }
    
    //return all users that exists in the publication
    public List<LikeDTO> getAllUsersByPublicationLike(int id_publication) {
        return likeDAO.getAllUsersByPublicationLike(id_publication);
    }
    
    //insert like
    public boolean insertLike(String id_user, int id_publication){
        return likeDAO.insertLike(id_user, id_publication);
    }    
}
