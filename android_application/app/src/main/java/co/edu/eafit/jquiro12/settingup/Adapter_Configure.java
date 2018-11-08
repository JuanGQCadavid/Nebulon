package co.edu.eafit.jquiro12.settingup;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Configure extends BaseAdapter {

    //The data tha is going to be showed
    ArrayList<Datos_List_Nebulizer> dataList;

    //The app context.
    Context context;


    public Adapter_Configure(Context context , ArrayList<Datos_List_Nebulizer> dataList){
        this.dataList=dataList;
        this.context=context;

    }

    /*
        Return the size of elements of the
        list.
     */
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        //Find the Object
        Datos_List_Nebulizer nebulizerObject = dataList.get(position);
        //return the Object id.
        return nebulizerObject.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vista = convertView;

        LayoutInflater inflater = LayoutInflater.from(context);
        vista= inflater.inflate(R.layout.list_configure_nebulones,null);


        TextView text_title_id = (TextView) vista.findViewById(R.id.text_id);
        TextView text_title_mac = (TextView) vista.findViewById(R.id.text_mac);


        text_title_id.setText(dataList.get(position).getId()+ "");
        text_title_mac.setText(dataList.get(position).getIp() + "");

        return vista;

    }
}
