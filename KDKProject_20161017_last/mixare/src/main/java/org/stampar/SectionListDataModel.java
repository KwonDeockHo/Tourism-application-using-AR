package org.stampar;

/**
 * Created by leejongho on 2016-11-19.
 */
import java.util.ArrayList;

/**
 * Created by pratap.kesaboyina on 30-11-2015.
 */
public class SectionListDataModel {

    private String headerTitle;
    private ArrayList<Store_data> allItemsInSection;


    public SectionListDataModel() {

    }
    public SectionListDataModel(String headerTitle, ArrayList<Store_data> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<Store_data> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<Store_data> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}
