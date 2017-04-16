package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.almuflihun.yukkajian.R;

import java.util.Calendar;

import helper.FontManager;

/**
 * Created by LENOVO on 3/6/2017.
 */

public class CalendarAdapter extends BaseAdapter{
    private Activity context;
    private Calendar calendar;
    public CalendarAdapter(Activity context){
        this.context = context;
        calendar = Calendar.getInstance();
    }
    @Override
    public int getCount() {
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = context.getLayoutInflater().inflate(R.layout.calendar_item, viewGroup, false);
            FontManager.markAsIconContainer(view.findViewById(R.id.im_calendar),FontManager.getTypeface(context,FontManager.font_awesome));
        }
        return view;
    }
}