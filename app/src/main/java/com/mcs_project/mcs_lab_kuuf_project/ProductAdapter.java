package com.mcs_project.mcs_lab_kuuf_project;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public static final String SEND_PRODUCT_ID = "com.example.application.MCS_LAB_KUUF_PROJECT.SEND_PRODUCT_ID";
    public static final String SEND_USER_ID = "com.example.application.MCS_LAB_KUUF_PROJECT.SEND_USER_ID";

    Context ctx;
    Vector<Product> vecProduct = new Vector<>();
    int userId;


    public void setUserId(int userId) {
        this.userId = userId;
    }
    public ProductAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void setVecProduct(Vector<Product> vecProduct) {
        this.vecProduct = vecProduct;
    }


    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.ViewHolder holder, final int position) {
        holder.tvProductName.setText("Product Name: " + vecProduct.get(position).getProductName());
        holder.tvMinPlayer.setText(String.valueOf("Min Player: " + vecProduct.get(position).getMinPlayer()));
        holder.tvMaxPlayer.setText(String.valueOf("Max Player: " + vecProduct.get(position).getMaxPlayer()));
        holder.tvProductPrice.setText(String.valueOf("Price: Rp. " + vecProduct.get(position).getPrice()));

        holder.cvProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ProductDetailActivity.class);
                intent.putExtra(SEND_PRODUCT_ID,vecProduct.get(position).getProductId());
                intent.putExtra(SEND_USER_ID,userId);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return vecProduct.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvProductName, tvMinPlayer, tvMaxPlayer, tvProductPrice;
        CardView cvProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvMinPlayer = itemView.findViewById(R.id.tvMinPlayer);
            tvMaxPlayer = itemView.findViewById(R.id.tvMaxPlayer);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            cvProduct = itemView.findViewById(R.id.productCardView);
        }
    }
}
