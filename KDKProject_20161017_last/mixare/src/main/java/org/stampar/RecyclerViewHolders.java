package org.stampar;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView countryName;
    public ImageView countryPhoto;


    public RecyclerViewHolders(View itemView) {
        super(itemView);

        countryName = (TextView)itemView.findViewById(R.id.country_name);
        countryPhoto = (ImageView)itemView.findViewById(R.id.country_photo);
        countryPhoto.setOnClickListener(this);


    }
    public TextView getTextViewname()
    {
        return countryName;
    }

    @Override

    public void onClick(final View view) {

        Intent intent = new Intent(view.getContext(), List_in_Cardview.class);
        intent.putExtra("list_info",getTextViewname().getText());
        view.getContext().startActivity(intent);

    }


}