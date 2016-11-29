package PostAdapter;

/**
 * Created by Amandeep on 11/1/2016.
 */

public class PostFetchModel {
    private String userId;
    private String postId;
    private String userImage;
    private String userName;
    private String postImage;
    private String postTime;
    private String postType;
    private String numLikes;
    private String isLikedByUser;


    public PostFetchModel(String userId, String postId, String userImage, String userName, String postImage, String postTime, String postType, String numLikes) {
        this.userId = userId;
        this.postId = postId;
        this.userImage = userImage;
        this.userName = userName;
        this.postImage = postImage;
        this.postTime = postTime;
        this.postType = postType;
        this.numLikes = numLikes;

    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(String numLikes) {
        this.numLikes = numLikes;
    }

    public String getIsLikedByUser() {
        return isLikedByUser;
    }

    public void setIsLikedByUser(String isLikedByUser) {
        this.isLikedByUser = isLikedByUser;
    }


}
