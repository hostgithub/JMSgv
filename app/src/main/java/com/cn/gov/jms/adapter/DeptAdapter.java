package com.cn.gov.jms.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cn.gov.jms.model.DeptBean;
import com.cn.gov.jms.ui.R;

import java.util.List;

/**
 * Created by wangjiawei on 2017-9-19.
 */

public class DeptAdapter extends android.widget.BaseAdapter {

    private List<DeptBean.ResultsBean> sublist;
    private Context context;

    public DeptAdapter(List<DeptBean.ResultsBean> sublist, Context context){
        this.sublist=sublist;
        this.context=context;
    }
    @Override
    public int getCount() {
        return sublist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_dept, null);
            vh = new MyViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (MyViewHolder) convertView.getTag();
        }
        Log.e("-------------",sublist.get(position).deptName);
        vh.tv_dept.setText(sublist.get(position).deptName);
        return convertView;
    }

    class MyViewHolder {
        View itemView;
        TextView tv_dept;
        public MyViewHolder(View itemView) {
            this.itemView = itemView;
            tv_dept = (TextView) itemView.findViewById(R.id.tv_dept);
        }
    }
}
