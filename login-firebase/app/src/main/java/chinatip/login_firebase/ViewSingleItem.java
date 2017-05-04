package chinatip.login_firebase;

/**
 * Created by Asus on 4/5/2560.
 */

public class ViewSingleItem {
    private String Image_URL, Image_Title;

    public ViewSingleItem(String image_URL, String image_Title) {
        Image_URL = image_URL;
        Image_Title = image_Title;
    }
    public ViewSingleItem(){
        Image_URL = "";
        Image_Title = "";
    }

    public String getImage_URL() {
        return Image_URL;
    }

    public void setImage_URL(String image_URL) {
        Image_URL = image_URL;
    }

    public String getImage_Title() {
        return Image_Title;
    }

    public void setImage_Title(String image_Title) {
        Image_Title = image_Title;
    }

}