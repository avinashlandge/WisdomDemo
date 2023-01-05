package com.example.wisdomdemo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

public class Product extends BaseObservable implements Parcelable
{


    @SerializedName("id")
    private String ID;
    @SerializedName("author")
    private String Author;
    @SerializedName("width")
    private String Width;
    @SerializedName("height")
    private String Height;
    @SerializedName("url")
    private String Url;
    @SerializedName("download_url")
    private String Download_url;

    public Product()
    {

    }

    public Product(String ID, String Auther, String Width,String Height,String Url,String Download_url)
    {
        this.ID = ID;
        this.Author = Auther;
        this.Width = Width;
        this.Height = Height;
        this.Url = Url;
        this.Download_url = Download_url;
    }


    protected Product(Parcel in)
    {
        ID = in.readString();
        Author = in.readString();
        Width = in.readString();
        Height = in.readString();
        Url = in.readString();
        Download_url = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(ID);
        parcel.writeString(Author);
        parcel.writeString(Width);
        parcel.writeString(Height);
        parcel.writeString(Url);
        parcel.writeString(Download_url);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getWidth() {
        return Width;
    }

    public void setWidth(String width) {
        Width = width;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getDownload_url() {
        return Download_url;
    }

    public void setDownload_url(String download_url) {
        Download_url = download_url;
    }

}
