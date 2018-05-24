package com.example.vladimir.contactreader;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String TAG = "TAG";
    private ItemClickListener itemClickListener;
    private final int KEY = 1;
    private List<Contact> arrayList;

    public interface ItemClickListener {
        void onItemClick(String id);
    }

    public CustomAdapter( ItemClickListener itemClickListener) {
        this.arrayList = new ArrayList<>();
        this.itemClickListener = itemClickListener;
    }

    public void setContacts(List<Contact> list){
        if (list != null && list.size() > 0) {
            this.arrayList.clear();
            this.arrayList.addAll(list);
            Log.d(TAG, "setCOntacts " + arrayList.size());
            notifyDataSetChanged();
        }

    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.contacts_list_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cursor.moveToPosition(viewHolder.getAdapterPosition());
//                String key = cursor.getString(KEY);
//                itemClickListener.onItemClick(key);
                Log.d(TAG, "click");
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
            holder.namTextView.setText(arrayList.get(position).getDisplayName());
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
