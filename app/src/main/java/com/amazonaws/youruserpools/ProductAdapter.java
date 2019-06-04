package com.amazonaws.youruserpools;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<Product> productList;

    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_list, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.ipaddress.setText(product.getipaddress());
        holder.lat.setText(String.valueOf( product.getlatitude()));
        holder.log.setText(String.valueOf(product.getlongitude()));
        holder.cl1.setText(product.getColume_Manfucture());


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView ipaddress, lat, log, cl1, rs1, cm1, ct1, chg1, nd1, dd1, ft1, bt1, bl1, eage1, lm1;


        public ProductViewHolder(View itemView) {
            super(itemView);

            ipaddress = itemView.findViewById(R.id.t1);
            lat = itemView.findViewById(R.id.t2);
            log = itemView.findViewById(R.id.t3);
            cl1 = itemView.findViewById(R.id.t5);
            rs1 = itemView.findViewById(R.id.t6);
            cm1 = itemView.findViewById(R.id.t7);
            ct1 = itemView.findViewById(R.id.t8);
            chg1 = itemView.findViewById(R.id.t9);
            nd1 = itemView.findViewById(R.id.t10);
            dd1 = itemView.findViewById(R.id.t11);
            ft1 = itemView.findViewById(R.id.t12);
            bt1 = itemView.findViewById(R.id.t13);
            bl1 =itemView.findViewById(R.id.t14);
            eage1 = itemView.findViewById(R.id.t15);
            lm1 = itemView.findViewById(R.id.t16);

        }
    }
}