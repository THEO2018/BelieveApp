package com.netset.taskgagandeep12_01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.netset.taskgagandeep12_01.adapter.ImageAdapter;
import com.netset.taskgagandeep12_01.model.ImageResultModel;
import com.netset.taskgagandeep12_01.network.AllApi;
import com.netset.taskgagandeep12_01.network.AllUrl;
import com.netset.taskgagandeep12_01.network.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements onCLick {

    private RecyclerView recycleView;
    private List<ImageResultModel> resultModelList;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    public void initComponents(){
        recycleView = findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        resultModelList = new ArrayList<>();
        adapter =new ImageAdapter(this,resultModelList,this);
        recycleView.setAdapter(adapter);
        callApi();
    }


    public void callApi(){
        Retrofit retrofit = RestClient.build(AllUrl.Base_Url);
        AllApi  api = retrofit.create(AllApi.class);

        Call<List<ImageResultModel>> call = api.getImageApi();
        call.enqueue(new Callback<List<ImageResultModel>>() {
            @Override
            public void onResponse(Call<List<ImageResultModel>> call, Response<List<ImageResultModel>> response) {
                if(response.isSuccessful()){
                    resultModelList = (List<ImageResultModel>)response.body();
                    List<ImageResultModel> modelList =new ArrayList<>();
                    boolean flag = false;
                    int count=0;
                    for (int i = 0; i <resultModelList.size() ; i++) {
                        if(count <=resultModelList.size()-1){
                            if(!flag){
                               modelList.add(resultModelList.get(count));
                                flag=true;
                                count++;
                        }
                            else {
                                if(count+1<resultModelList.size()) {
                                modelList.add(resultModelList.get(count));
                                modelList.get(modelList.size()-1).setImageResultModel(resultModelList.get(count+1));
                                count = count + 2;
                                flag = false;
                                }
                            }
                        }
                    }
                    adapter.refreshData(modelList);
                }
            }

            @Override
            public void onFailure(Call<List<ImageResultModel>> call, Throwable t) {
                Log.e("exception",t.getCause().getMessage());
            }
        });
    }

    @Override
    public void onClick(int position) {

    }
}
