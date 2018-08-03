package au.com.iag.incidenttracker.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import au.com.iag.incidenttracker.R;

public class GridViewMenuAdapter extends ArrayAdapter {
    Context context;

    public GridViewMenuAdapter(Context context)
    {
        super(context, 0);
        this.context=context;

    }

    public int getCount()
    {
        return 6;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;

        if (row == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.menu_grid_row, parent, false);


            TextView textViewTitle = (TextView) row.findViewById(R.id.textView);
            ImageView imageViewIte = (ImageView) row.findViewById(R.id.imageView);

            switch(position) {
                case 0:
                    textViewTitle.setText("View hazard map");
                    imageViewIte.setImageResource(R.drawable.icon_hazard);
                    break;
                case 1:
                    textViewTitle.setText("Hazard history");
                    imageViewIte.setImageResource(R.drawable.icon_map_route);
                    break;
                case 2:
                    textViewTitle.setText("Record route");
                    imageViewIte.setImageResource(R.drawable.icon_record_route);
                    break;
                case 3:
                    textViewTitle.setText("Manage routes");
                    imageViewIte.setImageResource(R.drawable.icon_manage_route);
                    break;
                case 4:
                    textViewTitle.setText("Set route alarm");
                    imageViewIte.setImageResource(R.drawable.icon_alarm);
                    break;
                case 5:
                    textViewTitle.setText("Settings");
                    imageViewIte.setImageResource(R.drawable.icon_settings);
                    break;
            }
        }

        return row;

    }
}
