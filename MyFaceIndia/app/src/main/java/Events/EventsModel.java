package Events;

/**
 * Created by Akash on 09-11-2016.
 */

public class EventsModel {
    String id, uname, name, url, edatetime, type, uimage;

    public EventsModel(String id, String uname, String name, String url, String type, String edatetime, String uimage) {
        this.id = id;
        this.uname = uname;
        this.name = name;
        this.url = url;
        this.type = type;
        this.edatetime = edatetime;
        this.uimage = uimage;
        }
}
