package adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.almuflihun.yukkajian.R;

import java.util.List;

/**
 * Created by LENOVO on 3/7/2017.
 */

public class KajianAdapter extends ArrayAdapter {
    Activity activity;
    List<Integer> integerList;
    public KajianAdapter(Activity activity, List<Integer> integers){
        super(activity, R.layout.kajian_item,integers);
        this.activity = activity;
        integerList = integers;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.kajian_item, parent, false);
        }
        return convertView;
    }
}
