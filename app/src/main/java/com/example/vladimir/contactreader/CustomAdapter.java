package com.example.vladimir.contactreader;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String TAG = "TAG";
    private ItemClickListener itemClickListener;
    private List<String> arrayList;
    private List<String> dataSet;

    public interface ItemClickListener {
        void onItemClick(int id);
    }

    public CustomAdapter(ItemClickListener itemClickListener) {
        this.arrayList = new ArrayList<>();
        this.itemClickListener = itemClickListener;
        this.dataSet = new ArrayList<>();
    }

    public void setContacts(List<String> list) {
        if (list != null && list.size() > 0) {
            this.arrayList.clear();
            this.arrayList.addAll(list);
            this.dataSet.clear();
            this.dataSet.addAll(list);
            notifyDataSetChanged();
        }

    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if (charText.length() == 0) {
            arrayList.clear();
            arrayList.addAll(dataSet);
        } else {
            ArrayList<String> result = new ArrayList<>();
            for (int i = 0; i < arrayList.size(); i++) {
                String item = arrayList.get(i);
                Log.d(TAG, "item = " + item);
                if (item.toLowerCase().contains(charText)) {
                    result.add(item);
                }
            }
            arrayList.clear();
            arrayList.addAll(result);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.contacts_list_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = viewHolder.getAdapterPosition();
                Log.d(TAG, "getAdapterPosition " + id);
                itemClickListener.onItemClick(id);
                Log.d(TAG, "click");
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        holder.namTextView.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView namTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.namTextView = itemView.findViewById(R.id.item_textView);
        }
    }
}
