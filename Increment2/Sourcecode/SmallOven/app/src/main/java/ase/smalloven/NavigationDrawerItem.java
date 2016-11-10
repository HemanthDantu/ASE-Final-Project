package ase.smalloven;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daras on 14-Oct-16.
 */
public class NavigationDrawerItem {

    private int ImageID;
    private String title;

    public int getImageID() {
        return ImageID;
    }

    public void setImageID(int imageID) {
        ImageID = imageID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static List<NavigationDrawerItem> getData()
    {
        List<NavigationDrawerItem> items = new ArrayList<>();
        int[] Imageids = getImages();
        String[] ImagesTiles = getTtiles();

        for (int i=0; i<Imageids.length;i++)
        {
            NavigationDrawerItem item = new NavigationDrawerItem();
            item.setImageID(Imageids[i]);
            item.setTitle(ImagesTiles[i]);
            items.add(item);


        }
        return  items;
    }

    private static int[] getImages()
    {
        return new int[]{

            R.drawable.ic_home,
                R.drawable.ic_fav,
                R.drawable.ic_date,
                R.drawable.ic_settings

        };
    }

    private static String[] getTtiles()
    {
        return new String[]
                {
                  "Home","Favorites","Weekly Meal","Settings"
                };
    }
}
