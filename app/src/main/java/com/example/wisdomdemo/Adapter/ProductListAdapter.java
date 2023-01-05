package com.example.wisdomdemo.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wisdomdemo.Model.Product;
import com.example.wisdomdemo.R;
import com.example.wisdomdemo.Utils.AdapterCallBackListener;
import com.example.wisdomdemo.databinding.ProductListBinding;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProductListAdapter extends RecyclerView.Adapter
{
    private ArrayList<Product> productArrList;
    Context context;
    private AdapterCallBackListener callBackListener;
    public static Handler handlerDownload;

    public ProductListAdapter(ArrayList<Product> productArrList, Context context,AdapterCallBackListener itemClickListener)
    {
        this.productArrList = productArrList;
        this.context = context;
        this.callBackListener = itemClickListener;
        handlerDownload=new Handler();
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ProductListBinding binding = ProductListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        final ViewHolder viewHolderClass = (ViewHolder) holder;

        viewHolderClass.binding.txtAuthor.setText(productArrList.get(position).getAuthor());
        viewHolderClass.binding.txtId.setText("ID : "+productArrList.get(position).getID());

        Glide.with(context)
                .load(productArrList.get(position).getDownload_url())
                .error(R.drawable.product_placeholder)
                .placeholder(R.drawable.product_placeholder)
                .into(viewHolderClass.binding.imgProduct);

        viewHolderClass.binding.rlDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                callBackListener.onRowClick(productArrList.get(position).getDownload_url());
            }
        });

        viewHolderClass.binding.relativeRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(context,productArrList.get(position).getID(),productArrList.get(position).getAuthor(),productArrList.get(position).getDownload_url());
            }
        });

    }

    @Override
    public int getItemCount() {
        return productArrList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ProductListBinding binding;

        public ViewHolder(ProductListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }
    }

    public void showDialog(Context context,String id, String author, String download_url){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dailogbox);

        AppCompatTextView text_id = (AppCompatTextView) dialog.findViewById(R.id.txt_id);
        text_id.setText("ID : "+id);

        AppCompatTextView text_author = (AppCompatTextView) dialog.findViewById(R.id.txt_productAuthor);
        text_author.setText(author);

        AppCompatImageView imageViewproduct = (AppCompatImageView) dialog.findViewById(R.id.imgproduct);

        System.out.println("Url is :"+download_url);
        Glide.with(context)
                .load(download_url)
                .placeholder(R.drawable.product_placeholder)
                .into(imageViewproduct);

        AppCompatImageView dialogButton = (AppCompatImageView) dialog.findViewById(R.id.img_close);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
