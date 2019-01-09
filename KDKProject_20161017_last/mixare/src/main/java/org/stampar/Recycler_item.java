package org.stampar;

/**
 * Created by GE62 on 2016-11-15.
 */

public class Recycler_item {
    int image;
    String title;

    Recycler_item(int image, String title)
    {
        this.image = image;
        this.title = title;
    }
    int getImage()
    {
        return this.image;
    }
    String getTitle()
    {
        return this.title;
    }

}
