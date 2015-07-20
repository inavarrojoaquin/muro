package service.instances;

import java.util.List;
import model.dao.CommentDAO;
import model.dto.CommentDTO;

public class CommentService {
private CommentDAO commentDAO;

    public CommentService() {
        this.commentDAO = new CommentDAO();
    }
    
    //getComments by publication
    public List<CommentDTO> getCommentsByPublication(int id_publication) {
        return commentDAO.selectAllComments(id_publication);
    }
    
    //getComments by publication
    public List<CommentDTO> getLastCommentsByPublication(int id_publication, String lastDate) {
        return commentDAO.selectLastComments(id_publication, lastDate);
    }
    
    //insert comment
    public boolean insertComment(String text,String id_user, int id_publication) {
        return commentDAO.insertComment(text, id_user, id_publication);
    }
    
    //delete comment
    public boolean deleteComment(int id_comment){
        return commentDAO.deleteComment(id_comment);
    }    
}
