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
        return 7;
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
                    imageViewIte.setImageResource(R.drawable.amu_bubble_mask);
                    break;
                case 1:
                    textViewTitle.setText("View marker map");
                    imageViewIte.setImageResource(R.drawable.amu_bubble_mask);
                    break;
                case 2:
                    textViewTitle.setText("View route map");
                    imageViewIte.setImageResource(R.drawable.amu_bubble_mask);
                    break;
                case 3:
                    textViewTitle.setText("Record route");
                    imageViewIte.setImageResource(R.drawable.amu_bubble_mask);
                    break;
                case 4:
                    textViewTitle.setText("Manage routes");
                    imageViewIte.setImageResource(R.drawable.amu_bubble_mask);
                    break;
                case 5:
                    textViewTitle.setText("Set route alarm");
                    imageViewIte.setImageResource(R.drawable.amu_bubble_mask);
                    break;
                case 6:
                    textViewTitle.setText("Settings");
                    imageViewIte.setImageResource(R.drawable.amu_bubble_mask);
                    break;
            }
        }

        return row;

    }
}
