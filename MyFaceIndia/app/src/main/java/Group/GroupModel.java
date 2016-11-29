package Group;

/**
 * Created by Akash on 08-11-2016.
 */

public class GroupModel {
    String id,name,title,image,privacy,control,description,gdate;

    public GroupModel(String id, String name, String title, String image, String privacy, String control, String description, String gdate) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.image = image;
        this.privacy = privacy;
        this.control = control;
        this.description = description;
        this.gdate = gdate;
    }
}
