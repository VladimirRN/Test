package com.example.vladimir.contactreader;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vladimir.contactreader.model.db.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String TAG = "TAG";
    private ItemClickListener itemClickListener;
    private List<Contact> arrayList;
    private List<Contact> arrayListCopy;

    public interface ItemClickListener {
        void onItemClick(Long id);
    }

    public CustomAdapter(ItemClickListener itemClickListener) {
        this.arrayList = new ArrayList<>();
        this.itemClickListener = itemClickListener;
        this.arrayListCopy = new ArrayList<>();
    }

    public void setContacts(List<Contact> list) {
        if (list != null && list.size() > 0) {
            this.arrayList.clear();
            this.arrayList.addAll(list);
            this.arrayListCopy.clear();
            this.arrayListCopy.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if (charText.length() == 0) {
            arrayList.clear();
            arrayList.addAll(arrayListCopy);
        } else {
            ArrayList<Contact> result = new ArrayList<>();
            for (int i = 0; i < arrayList.size(); i++) {
                String item = arrayList.get(i).getDisplayName();
                if (item.toLowerCase().contains(charText)) {
                    result.add(arrayList.get(i));
                }
            }
            arrayList.clear();
            arrayList.addAll(result);
        }
        notifyDataSetChanged();
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.contacts_list_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(v -> {
            Long id = arrayList.get(viewHolder.getAdapterPosition()).getId();
            itemClickListener.onItemClick(id);
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
