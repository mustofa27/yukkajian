package adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.almuflihun.yukkajian.DetailKelolaKajian;
import com.almuflihun.yukkajian.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import entity.Kajian;
import helper.FontManager;
import helper.ImageHelper;
import network.ConnectionHandler;

/**
 * Created by LENOVO on 3/7/2017.
 */

public class MyKajianAdapter extends ArrayAdapter {
    Activity activity;
    List<Kajian> kajianList;

    public MyKajianAdapter(Activity activity, List<Kajian> kajianList) {
        super(activity, R.layout.kajian_item, kajianList);
        this.activity = activity;
        this.kajianList = kajianList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = activity.getLayoutInflater().inflate(R.layout.mykajian_item, parent, false);
        final Kajian current = kajianList.get(position);
        FontManager.markAsIconContainer(convertView.findViewById(R.id.container_icon),FontManager.getTypeface(activity,FontManager.font_awesome));
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView desc = (TextView) convertView.findViewById(R.id.desc);
        title.setText(current.getTema());
        desc.setText(current.getDeskripsi());
        convertView.findViewById(R.id.container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DetailKelolaKajian.class);
                intent.putExtra("kajian",current);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        return convertView;
    }
}
