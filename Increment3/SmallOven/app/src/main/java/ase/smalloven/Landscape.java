package ase.smalloven;

import android.graphics.Bitmap;

/**
 * Created by daras on 29-Oct-16.
 */
public class Landscape {
    private Bitmap Image;
    private  String title;
    private  String description;
    private  String Id;
    private boolean isSeleted;


    Landscape(Bitmap Image,String Title,String Description, String Id)
    {
        super();
        this.Image = Image;
        this.title = Title;
        this.description = Description;
        this.Id = Id;
    }
    public Bitmap getImage() {
        return Image;
    }

    public void setImage(Bitmap image) {
        Image = image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSeleted() {
        return isSeleted;
    }

    public void setIsSeleted(boolean isSeleted) {
        this.isSeleted = isSeleted;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }
}
