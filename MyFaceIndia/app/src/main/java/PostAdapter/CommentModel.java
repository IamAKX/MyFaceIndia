package PostAdapter;

/**
 * Created by Amandeep on 11/4/2016.
 */

public class CommentModel {
    private String commentUser;
    private String commentContent;
    private String commentDate;

    public CommentModel(String commentUser, String commentContent, String commentDate) {
        this.commentUser = commentUser;
        this.commentContent = commentContent;
        this.commentDate = commentDate;
    }

    public String getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }
}
