package com.example.vladimir.contactreader;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CustomAdapter extends CursorRecyclerAdapter<CustomAdapter.ViewHolder> {

    private ItemClickListener itemClickListener;
    private final int KEY = 1;

    public interface ItemClickListener {
        void onItemClick(String id);
    }

    public CustomAdapter(Cursor cursor, ItemClickListener itemClickListener) {
        super(cursor);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
        viewHolder.namTextView.setText(name);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.contacts_list_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor.moveToPosition(viewHolder.getAdapterPosition());
                String key = cursor.getString(KEY);
                itemClickListener.onItemClick(key);
            }
        });
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView namTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.namTextView = itemView.findViewById(R.id.item_textView);
        }
    }
}
