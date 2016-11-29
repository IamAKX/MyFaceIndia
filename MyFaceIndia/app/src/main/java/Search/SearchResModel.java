package Search;

/**
 * Created by Amandeep on 11/11/2016.
 */

public class SearchResModel {
    private String uId;
    private String uName;
    private String uImage;

    public SearchResModel(String uId, String uName, String uImage) {
        this.uId = uId;
        this.uName = uName;
        this.uImage = uImage;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuImage() {
        return uImage;
    }

    public void setuImage(String uImage) {
        this.uImage = uImage;
    }
}
