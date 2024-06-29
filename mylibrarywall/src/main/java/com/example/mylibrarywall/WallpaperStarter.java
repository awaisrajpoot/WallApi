package com.example.mylibrarywall;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WallpaperStarter {

    private RequestQueue mQueue;
    Context mContext;
    WallpaperListener wallpaperListener;
    String webPrefix;
    String fileExe = ".jpg";


    public WallpaperStarter(Context mContext, String webPrefix){
        this.mContext = mContext;
        this.webPrefix = webPrefix;

        this.wallpaperListener = (WallpaperListener) mContext;

        mQueue = Volley.newRequestQueue(mContext);
    }

    public void setStart(String rootDataLink){

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                rootDataLink, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("server_response",
                                    "server response is : " + response.toString());

                            JSONArray jsonArray = response.getJSONArray("categoryList");

                            gettingData(jsonArray);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }


    public void gettingData(JSONArray jsonArray){

        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> countList = new ArrayList<>();

        for (int cnt=0; cnt<jsonArray.length(); cnt++){
            try {
                JSONArray singleNameArr = jsonArray.getJSONArray(cnt);
                nameList.add(singleNameArr.get(0).toString());
                Log.e("data_list", "server response is : " + singleNameArr.get(0));

                JSONArray singleCountArr = jsonArray.getJSONArray(cnt);
                countList.add(singleCountArr.get(1).toString());

                Log.e("data_list", "server response is : " + singleCountArr.get(1));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        //wallpaperListener.onDataLoaded(nameList, countList);
        if (nameList.size() > 1 && countList.size()>1){
            wallpaperListener.onDataLoaded(nameList, countList);
        }
    }

    public ArrayList<String> getSingleItems(String catName, String catLength){

        ArrayList<String> dataList = new ArrayList<>();

        int mCnt = Integer.parseInt(catLength);
        String folderName = catName + "/";

        Log.i("address",""+catName + "length is : " + mCnt);

        for (int nt=0; nt<mCnt; nt++){
            int rightCnt = nt+1;//to avoid 0 index

            //web prefix,
            // category name,
            // counter,
            //underscore
            // file extension
            String address = webPrefix + folderName + catName + "_" + rightCnt + fileExe;
            Log.i("address","address is : "+address);

            dataList.add(address);
        }

        return dataList;
    }

    public interface WallpaperListener{
        void onDataLoaded(ArrayList<String> nameList, ArrayList<String> countList);
    }

}
