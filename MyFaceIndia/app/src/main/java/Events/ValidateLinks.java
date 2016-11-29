package Events;

/**
 * Created by Akash on 09-11-2016.
 */

public class ValidateLinks {

    public static boolean validateMusic(String ext)
    {
        String[] music_array = {"mp3","ogg","pcm","wav","aiff","wma","aac","flac","alac"};
        int i=0;
        for(i=0;i<music_array.length;i++)
        {
            if(ext.toLowerCase().equalsIgnoreCase(music_array[i]))
                return true;
        }
        return false;
    }

    public static boolean validateVideo(String ext)
    {
        String[] video_array = {"mp4","avi","asf","mov","flv","mpg","wmv","divx","3gp","webm"};
        int i=0;
        for(i=0;i<video_array.length;i++)
        {
            if(ext.toLowerCase().equalsIgnoreCase(video_array[i]))
                return true;
        }
        return false;
    }

    public static boolean validateImage(String ext)
    {
        String[] image_array = {"jpg","png","jpeg","gif","tif","raw"};
        int i=0;
        for(i=0;i<image_array.length;i++)
        {
            if(ext.toLowerCase().equalsIgnoreCase(image_array[i]))
                return true;
        }
        return false;
    }
}
