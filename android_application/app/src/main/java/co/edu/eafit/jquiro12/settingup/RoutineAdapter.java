package co.edu.eafit.jquiro12.settingup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RoutineAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Subroutine> subroutines;

    @Override
    public int getCount() {
        return subroutines.size();
    }

    public RoutineAdapter(Context context, ArrayList<Subroutine> subroutines){
        this.context = context;
        this.subroutines = subroutines;
    }

    @Override
    public Object getItem(int position) {
        return subroutines.get(position);
    }

    @Override
    public long getItemId(int position) {
        Subroutine sub = subroutines.get(position);
        return sub.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listView;

        LayoutInflater inflater = LayoutInflater.from(context);
        listView = inflater.inflate(R.layout.subroutines_list, null);

        TextView monday_v = (TextView) listView.findViewById(R.id.l_day);
        TextView tuesday_v = (TextView) listView.findViewById(R.id.m_day);
        TextView wednesday_v= (TextView) listView.findViewById(R.id.w_day);
        TextView thursday_v = (TextView) listView.findViewById(R.id.j_day);
        TextView friday_v = (TextView) listView.findViewById(R.id.v_day);
        TextView saturday_v = (TextView) listView.findViewById(R.id.s_day);
        TextView sunday_v = (TextView) listView.findViewById(R.id.d_day);

        TextView from_v = (TextView) listView.findViewById(R.id.from_hour);
        TextView till_v = (TextView) listView.findViewById(R.id.till_hour);



        Subroutine subroutine = subroutines.get(position);

        if(subroutine.isMonday() == true){
            monday_v.setVisibility(View.VISIBLE);
        }
        if(subroutine.isTuesday() == true){
            tuesday_v.setVisibility(View.VISIBLE);
        }
        if(subroutine.isWednesday() == true){
            wednesday_v.setVisibility(View.VISIBLE);
        }
        if(subroutine.isThursday() == true){
            thursday_v.setVisibility(View.VISIBLE);
        }
        if(subroutine.isFriday() == true){
            friday_v.setVisibility(View.VISIBLE);
        }
        if(subroutine.isSaturday() == true){
            saturday_v.setVisibility(View.VISIBLE);
        }
        if(subroutine.isSunday() == true){
            sunday_v.setVisibility(View.VISIBLE);
        }

        String from = subroutine.getFrom_hour() + ":" + subroutine.getFrom_minute();
        from_v.setText(from);

        String till = subroutine.getTill_hour() + ":" + subroutine.getTill_minute();
        till_v.setText(till);

        return listView;
    }
}
