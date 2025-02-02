package com.github.tvbox.osc.ui.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.github.tvbox.osc.R;
import com.github.tvbox.osc.bean.SourceBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheckboxSearchAdapter extends ListAdapter<SourceBean, CheckboxSearchAdapter.ViewHolder> {

    public CheckboxSearchAdapter(DiffUtil.ItemCallback<SourceBean> diffCallback) {
        super(diffCallback);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_checkbox_search, parent, false));

    }

    private void setCheckedSource(HashMap<String, SourceBean> checkedSources) {
        mCheckedSources = checkedSources;
    }

    private ArrayList<SourceBean> data = new ArrayList<>();
    public HashMap<String, SourceBean> mCheckedSources = new HashMap<>();

    public void setData(List<SourceBean> newData, HashMap<String, SourceBean> checkedSources) {
        data.clear();
        data.addAll(newData);
        setCheckedSource(checkedSources);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        SourceBean sourceBean = data.get(position);
        holder.oneSearchSource.setOnCheckedChangeListener(null);
        holder.oneSearchSource.setText(sourceBean.getName());
        if (mCheckedSources != null) {
            holder.oneSearchSource.setChecked(mCheckedSources.containsKey(sourceBean.getKey()));
        }
        holder.oneSearchSource.setTag(sourceBean);
        holder.oneSearchSource.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    return;
                }
                if (isChecked) {
                    mCheckedSources.put(sourceBean.getKey(), sourceBean);
                } else {
                    mCheckedSources.remove(sourceBean.getKey());
                }
                notifyItemChanged(position);
            }
        });
        holder.oneSearchSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked = mCheckedSources.containsKey(sourceBean.getKey());
                if (isChecked) {
                    mCheckedSources.remove(sourceBean.getKey());
                    holder.oneSearchSource.setChecked(false);
                } else {
                    mCheckedSources.put(sourceBean.getKey(), sourceBean);
                    holder.oneSearchSource.setChecked(true);
                }
                notifyItemChanged(position);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox oneSearchSource;

        public ViewHolder(View view) {
            super(view);
            oneSearchSource = (CheckBox) view.findViewById(R.id.oneSearchSource);
        }
    }

}
