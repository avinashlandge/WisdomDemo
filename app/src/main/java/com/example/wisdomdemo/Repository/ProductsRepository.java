package com.example.wisdomdemo.Repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.wisdomdemo.Activity.MainActivity;
import com.example.wisdomdemo.Utils.Constant;
import com.example.wisdomdemo.Model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductsRepository
{
    private Application application;

    public ProductsRepository(Application application)
    {
        this.application = application;
    }


    private MutableLiveData<ArrayList<Product>> productMutableLiveData = new MutableLiveData<ArrayList<Product>>();

    private Product product;
    private ArrayList<Product> productArrayList;

    //Product List
    public MutableLiveData<ArrayList<Product>> getProductList() {

        JsonArrayRequest stringRequest = new JsonArrayRequest(Constant.BASE_URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response)
             {
                 JSONArray jsonArray = null;
                 productArrayList=new ArrayList<>();

                 try {
                     for (int i = 0; i < response.length(); i++)
                     {
                         product = new Product();
                         JSONObject jsonObject= response.getJSONObject(i);

                         product.setID(jsonObject.getString(Constant.id));
                         product.setAuthor(jsonObject.getString(Constant.author));
                         product.setWidth(jsonObject.getString(Constant.width));
                         product.setHeight(jsonObject.getString(Constant.height));
                         product.setUrl(jsonObject.getString(Constant.url));
                         product.setDownload_url(jsonObject.getString(Constant.download_url));

                         productArrayList.add(product);

                     }
                     productMutableLiveData.setValue(productArrayList);

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

             }
             },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                    }
                });
        MainActivity.queue.add(stringRequest);

        return productMutableLiveData;
    }


}
