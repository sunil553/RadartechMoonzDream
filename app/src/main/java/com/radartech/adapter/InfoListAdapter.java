/**
 *
 */
package com.radartech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.radartech.model.SettingSpinnerItemData;
import com.radartech.sw.R;
import com.radartech.util.AppConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Adapter that allows us to render right_arrow list of items
 */
public class InfoListAdapter extends BaseAdapter {

    private List<SettingSpinnerItemData> infoList;
    private LayoutInflater li;
    private Context context;

    /**
     * Constructor from right_arrow list of items
     */
    public InfoListAdapter(Context context, List<SettingSpinnerItemData> infoList) {
        this.context = context;
        this.infoList = infoList;
        li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int position) {
        return infoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // The search_item we want to get the view for
        // --

        // Re-use the view if possible
        // --
        SettingSpinnerItemData settingSpinnerItemData = infoList.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = li.inflate(R.layout.item_info_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Set some view properties
        holder.tagDescription.setText(settingSpinnerItemData.getText());
        if (settingSpinnerItemData.getImageId() == AppConstants.InfoConstants.ARROW_VISIBLE){
            holder.arrowTv.setVisibility(View.VISIBLE);
        }else {
            holder.arrowTv.setVisibility(View.GONE);
        }

        return convertView;
    }

    static class ViewHolder {

        public ViewHolder(View rootView) {
            ButterKnife.bind(this, rootView);
        }

        @BindView(R.id.tag_description)
        public TextView tagDescription;

        @BindView(R.id.arrow)
        public TextView arrowTv;

    }
}
