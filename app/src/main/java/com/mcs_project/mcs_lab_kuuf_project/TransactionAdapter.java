package com.mcs_project.mcs_lab_kuuf_project;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    Context ctx;
    Vector<Transactions> transVec = new Vector<>();
    static int removed = 0;

    public TransactionAdapter(Context ctx) {
        this.ctx = ctx;
    }

    public void setTransVec(Vector<Transactions> transVec) {
        this.transVec = transVec;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.transaction_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.ViewHolder holder, int position) {
        int productId = transVec.get(position).getProductId();
        DBHelper db = new DBHelper(ctx);
        ProductsDB productsDB = new ProductsDB(db);
        Product product = productsDB.getProductDetail(productId);
        holder.tvTransName.setText("Product Name: " + product.getProductName());
        holder.tvTransPrice.setText("Product Price: " + product.getPrice());
        holder.tvTransactionDate.setText("Transaction date: " + transVec.get(position).getTranscationDate());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = transVec.get(position).getTransId();
                transVec.remove(position);

                TransactionsDB transactionsDB = new TransactionsDB(ctx);
                transactionsDB.deleteTransaction(id);

                Toast.makeText(ctx, "Transaction Removed", Toast.LENGTH_LONG).show();
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,transVec.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return transVec.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTransName, tvTransactionDate, tvTransPrice;
        Button btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTransName = itemView.findViewById(R.id.tvTransName);
            tvTransactionDate = itemView.findViewById(R.id.tvTransactionDate);
            tvTransPrice = itemView.findViewById(R.id.tvTransPrice);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
