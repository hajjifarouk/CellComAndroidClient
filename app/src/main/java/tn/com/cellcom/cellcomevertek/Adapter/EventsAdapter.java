package tn.com.cellcom.cellcomevertek.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tn.com.cellcom.cellcomevertek.R;
import tn.com.cellcom.cellcomevertek.entities.Visit;

/**
 * Created by farouk on 10/07/2017.
 */

public class EventsAdapter extends ArrayAdapter<Visit> {


    public final static String TAG = "Devices";
    private int resourceId = 0;
    private LayoutInflater inflater;

    public EventsAdapter(Context context, List<Visit> objects) {
        super(context, R.layout.one_shop , objects);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        public TextView firstname,lastname,code;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;

        ShopsAdapter.ViewHolder holder = new ShopsAdapter.ViewHolder();

        view = inflater.inflate(R.layout.one_shop, parent, false);
        try {
            holder.firstname = (TextView) view.findViewById(R.id.firstname);
            holder.lastname = (TextView) view.findViewById(R.id.lastname);
            holder.code = (TextView) view.findViewById(R.id.code);


        } catch( ClassCastException e ) {
            throw e;
        }
        holder.firstname.setText(getItem(position).getFirst_name());
        holder.lastname.setText(getItem(position).getLast_name());
        holder.code.setText(getItem(position).getCode()+"");


        return view;
    }
}
