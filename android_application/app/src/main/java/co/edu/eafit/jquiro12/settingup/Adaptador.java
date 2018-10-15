package co.edu.eafit.jquiro12.settingup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

public class Adaptador extends BaseAdapter {

    Context contexto;
    List<Datos_List_Nebulizer> listaObjetos;

    public Adaptador(Context contexto, List<Datos_List_Nebulizer> listaObjetos) {
        this.contexto = contexto;
        this.listaObjetos = listaObjetos;
    }

    @Override
    public int getCount() {
        //cantidad de objetos
        return listaObjetos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaObjetos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaObjetos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vista = convertView;

        LayoutInflater inflater = LayoutInflater.from(contexto);
        vista= inflater.inflate(R.layout.itemlistview,null);

        Switch switch_s = (Switch) vista.findViewById(R.id.switch_select);
        TextView text_title_id = (TextView) vista.findViewById(R.id.text_id);
        TextView text_title_mac = (TextView) vista.findViewById(R.id.text_mac);


        text_title_id.setText(listaObjetos.get(position).getId_title().toString());
        text_title_mac.setText(listaObjetos.get(position).getMac_title().toString());

        return vista;

    }
}
