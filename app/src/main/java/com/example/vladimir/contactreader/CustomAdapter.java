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

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String TAG = "TAG";
    private ItemClickListener itemClickListener;
    private final int KEY = 1;
    private List<Contact> list;

    public interface ItemClickListener {
        void onItemClick(String id);
    }

    public CustomAdapter(List<Contact> list, ItemClickListener itemClickListener) {
        this.list = list;
        this.itemClickListener = itemClickListener;
    }

//    @Override
//    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
//        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
//        viewHolder.namTextView.setText(name);
//    }

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
            holder.namTextView.setText(list.get(position).getDisplayName());
        Log.d(TAG, "size list in adapter " + list.size());

    }

    @Override
    public int getItemCount() {
        return list.size();
//        Log.d(TAG, "size list in adapter " + list.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView namTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.namTextView = itemView.findViewById(R.id.item_textView);
        }
    }
}
