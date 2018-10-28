package co.edu.eafit.jquiro12.settingup;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.ArrayList;

public class ClientesConnected extends AsyncTask<Datos_List_Nebulizer, Integer, String>
{


    @Override
    protected String doInBackground(Datos_List_Nebulizer... datos) {
        System.out.println("***************************************************");

        int dataSize = datos.length;
        int count = 0;

        String ip;
        for(Datos_List_Nebulizer dato: datos){
            ip = dato.getId_title();
            try {
                if(Inet4Address.getByName(ip).isReachable(1500) == true){
                    //Put on the view
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            count++;

            publishProgress((int) (count/dataSize * 100));

        }

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
