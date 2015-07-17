package service.instances;

import model.dao.ShareDAO;

public class ShareService {
    private ShareDAO shareDAO;

    public ShareService() {
        this.shareDAO = new ShareDAO();
    }
    
    //insert share with facebook or twitter
    public boolean insertShare(int id_publication, String id_user_share, String text, String destination){
        return shareDAO.insertShare(id_publication, id_user_share, text, destination);
    }    
}
