package tn.com.cellcom.cellcomevertek.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import tn.com.cellcom.cellcomevertek.R;
import tn.com.cellcom.cellcomevertek.entities.Shop;

/**
 * Created by farouk on 06/07/2017.
 */

public class ShopsAdapter extends ArrayAdapter<Shop> {

    public final static String TAG = "Devices";
    private int resourceId = 0;
    private LayoutInflater inflater;

    public ShopsAdapter(Context context, List<Shop> objects) {
        super(context, R.layout.one_shop , objects);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    static class ViewHolder {
        public TextView firstname,lastname,code;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;

        ViewHolder holder = new ViewHolder();

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
