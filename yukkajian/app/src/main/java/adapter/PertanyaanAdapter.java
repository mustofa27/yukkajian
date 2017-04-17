package adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.almuflihun.yukkajian.DetailKelolaKajian;
import com.almuflihun.yukkajian.R;

import java.util.List;

import entity.Kajian;
import entity.Pertanyaan;
import helper.FontManager;

/**
 * Created by LENOVO on 3/7/2017.
 */

public class PertanyaanAdapter extends ArrayAdapter {
    Activity activity;
    List<Pertanyaan> pertanyaanList;

    public PertanyaanAdapter(Activity activity, List<Pertanyaan> pertanyaanList) {
        super(activity, R.layout.kajian_item, pertanyaanList);
        this.activity = activity;
        this.pertanyaanList = pertanyaanList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = activity.getLayoutInflater().inflate(R.layout.pertanyaan_item, parent, false);
        final Pertanyaan current = pertanyaanList.get(position);
        FontManager.markAsIconContainer(convertView.findViewById(R.id.container),FontManager.getTypeface(activity,FontManager.font_awesome));
        TextView user = (TextView) convertView.findViewById(R.id.user);
        TextView pertanyaan = (TextView) convertView.findViewById(R.id.pertanyaan);
        user.setText("USER"+current.getId_user());
        pertanyaan.setText(current.getPertanyaan());
        return convertView;
    }
}
