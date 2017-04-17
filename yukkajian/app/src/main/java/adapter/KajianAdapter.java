package adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.almuflihun.yukkajian.DetailKajian;
import com.almuflihun.yukkajian.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import entity.Kajian;
import helper.ImageHelper;
import network.ConnectionHandler;

/**
 * Created by LENOVO on 3/7/2017.
 */

public class KajianAdapter extends ArrayAdapter {
    Activity activity;
    List<Kajian> kajianList;
    ImageHelper imageHelper;
    int width;

    public KajianAdapter(Activity activity, List<Kajian> kajianList, int width) {
        super(activity, R.layout.kajian_item, kajianList);
        this.activity = activity;
        this.kajianList = kajianList;
        imageHelper = new ImageHelper(activity);
        this.width = width;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = activity.getLayoutInflater().inflate(R.layout.kajian_item, parent, false);
        final Kajian current = kajianList.get(position);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView desc = (TextView) convertView.findViewById(R.id.desc);
        final ImageView thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
        final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
        title.setText(current.getTema());
        desc.setText(current.getDeskripsi());
        String url = ConnectionHandler.IMAGE_URL + current.getThumbnail();
        Picasso.with(activity)
                .load(url)
                .into(new Target() {

                    @Override
                    public void onPrepareLoad(Drawable arg0) {
                    }

                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom arg1) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            Bitmap tmp = imageHelper.getFitScreenBitmap(bitmap, width);
                            thumbnail.setImageBitmap(tmp);
                            thumbnail.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onBitmapFailed(Drawable arg0) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            thumbnail.setVisibility(View.VISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        convertView.findViewById(R.id.detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DetailKajian.class);
                intent.putExtra("kajian",current);
                activity.startActivity(intent);
                activity.finish();
            }
        });
        return convertView;
    }
}
