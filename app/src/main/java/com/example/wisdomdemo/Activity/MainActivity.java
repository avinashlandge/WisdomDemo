package com.example.wisdomdemo.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.wisdomdemo.Adapter.ProductListAdapter;
import com.example.wisdomdemo.Model.Product;
import com.example.wisdomdemo.R;
import com.example.wisdomdemo.Utils.AdapterCallBackListener;
import com.example.wisdomdemo.Viewmodel.ProductViewModel;
import com.example.wisdomdemo.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterCallBackListener {

    private ProductViewModel productViewModel;
    private ActivityMainBinding binding;
    public ArrayList<Product> productList= new ArrayList<>();
    public static RequestQueue queue;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

         queue = Volley.newRequestQueue(this);

        binding.progressBarLarge.setVisibility(View.VISIBLE);

        binding.swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                setAdapter();
            }
        });

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);



        setAdapter();
    }



    private void setAdapter()
    {
        productViewModel.getProductArrayList().observe(this, productArrayList ->
        {

            if (productArrayList != null && !productArrayList.isEmpty())
            {
                binding.progressBarLarge.setVisibility(View.GONE);

                productList = productArrayList;

                binding.txtNoData.setVisibility(View.GONE);
                binding.rvProductList.setVisibility(View.VISIBLE);
                linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                binding.rvProductList.setLayoutManager(linearLayoutManager);

                ProductListAdapter adapter = new ProductListAdapter(productArrayList, MainActivity.this, this);
                binding.rvProductList.setAdapter(adapter);
                binding.swipeToRefresh.setRefreshing(false);

            }
            else
            {
                binding.txtNoData.setVisibility(View.VISIBLE);
                binding.rvProductList.setVisibility(View.GONE);
            }

        });
    }

    @Override
    public void onRowClick(String url) {

        DownloadImage(url);
    }


    void DownloadImage(String ImageUrl)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            Toast.makeText(MainActivity.this,"Need Permission to access storage for Downloading Image",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(MainActivity.this,"Downloading Image...",Toast.LENGTH_SHORT).show();

            new DownloadsImage().execute(ImageUrl);
        }
    }

    class DownloadsImage extends AsyncTask<String, Void,Void> {

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bm = null;
            try {
                bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+ "/WisdomImage"); //Creates app specific folder

            if(!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis())+".png"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try{
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();

                MediaScannerConnection.scanFile(MainActivity.this,new String[] { imageFile.getAbsolutePath() }, null,new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {

                    }
                });
            } catch(Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(MainActivity.this,"Image Saved!",Toast.LENGTH_SHORT).show();
        }
    }

}

