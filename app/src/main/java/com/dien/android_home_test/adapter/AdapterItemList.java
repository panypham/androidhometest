package com.dien.android_home_test.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dien.android_home_test.R;
import com.dien.android_home_test.model.Item;
import com.dien.android_home_test.utils.Utils;

import java.util.List;
import java.util.Random;

public class AdapterItemList extends RecyclerView.Adapter<AdapterItemList.ViewHolder> {
    private Context context;
    private List<Item> items;

    public AdapterItemList(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public AdapterItemList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItemList.ViewHolder holder, int position) {
        String lines = Utils.getLines(items.get(position).getText());
        holder.tvItem.setText(lines);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvItem;
        private Random random = new Random();

        ViewHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvItem);
            tvItem.setBackgroundResource(R.drawable.bt_item);
            GradientDrawable gradientDrawable = (GradientDrawable) tvItem.getBackground();
            int color = Color.argb(255, random.nextInt(256), random.nextInt(150), random.nextInt(255));
            gradientDrawable.setColor(color);
            tvItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Click \n" + tvItem.getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
