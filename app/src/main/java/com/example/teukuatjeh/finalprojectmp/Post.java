package com.example.teukuatjeh.finalprojectmp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Teuku Atjeh on 12/04/2018.
 */

public class Post {
    @SerializedName("id")
    public Integer id;
    @SerializedName("judul")
    public String judul;
    @SerializedName("deskripsi")
    public String deskripsi;
    @SerializedName("image")
    public String image;
}
