package com.example.tracker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private Corona detail=new Corona();
    private TextView gConfirmed,gDeath,gRecovered;
    private ListView listView;
    private List<Countries> list;
    //private List<String> countryName;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    //private ArrayAdapter<String> stringArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        listView = (ListView) findViewById(R.id.listView);
        list= new ArrayList<>();
        //countryName=new ArrayList<>();
        init();
        loadData();
    }

    private void init() {
        gConfirmed=findViewById(R.id.global_total_confirmed_cases);
        gDeath=findViewById(R.id.global_total_death_cases);
        gRecovered=findViewById(R.id.global_total_recovered_cases);
        recyclerView=findViewById(R.id.country_list);

        myAdapter=new MyAdapter(this,list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
    }

    private void loadData() {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url="https://api.covid19api.com/summary";

        // Request a JSON response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                response -> {
                    Log.e("Response",response.toString());
                    Gson gson=new Gson();
                    detail=gson.fromJson(response.toString(),Corona.class);
                    String confirmed=detail.getGlobal().getTotalConfirmed();
                    String death=detail.getGlobal().getTotalDeaths();
                    String recovered=detail.getGlobal().getTotalRecovered();

                    gConfirmed.setText("Total Confirmed Cases:"+confirmed);
                    gDeath.setText("Total Death Cases:"+death);
                    gRecovered.setText("Total Recovered:"+recovered);
                    //countryName.clear();
                    list.clear();
                    for(Countries c:detail.getCountries()){
                        list.add(c);
                        //countryName.add(c.getCountry());
                    }
//                    stringArrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,countryName);
//                    listView.setAdapter(stringArrayAdapter);
                    myAdapter.notifyDataSetChanged();

                }, error -> Log.e("Error:", error.toString()));

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.option_menu,menu);

//        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Countries c= list.stream()
//                        .filter(country->query.equalsIgnoreCase(country.getCountry()))
//                        .findAny()
//                        .orElse(null);
//                list.clear();
//                if(c!=null)
//                    list.add(c);
//                searchView.clearFocus();
//                myAdapter.notifyDataSetChanged();
//             /*   if(list.contains(query)){
//                    adapter.getFilter().filter(query);
//                }else{
//                    Toast.makeText(MainActivity.this, "No Match found",Toast.LENGTH_LONG).show();
//                }*/
//                return false;
//
//            }
//
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                stringArrayAdapter.getFilter().filter(newText);
//                List c= list.stream()
//                        .filter(country->country.getCountry().toLowerCase().startsWith(newText.toLowerCase()))
//                        .collect(Collectors.toList());
//                list.clear();
//                for(Object d:c){
//                    list.add((Countries) d);
//                }
//                myAdapter.notifyDataSetChanged();
//                return false;
//            }
//        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {

        if(item.getItemId()==R.id.refresh){
            Toast.makeText(this,"Reloading",Toast.LENGTH_LONG).show();
            loadData();
        }
        else if(item.getItemId()==R.id.total_confirmed_cases){
            Collections.sort(
                    list,(c1,c2)-> Integer.parseInt(c2.getTotalConfirmed())- Integer.parseInt(c1.getTotalConfirmed())
            );
            myAdapter.notifyDataSetChanged();
        }
        else if(item.getItemId()==R.id.total_death_cases){
            Collections.sort(
                    list,(c1,c2)-> Integer.parseInt(c2.getTotalDeaths())- Integer.parseInt(c1.getTotalDeaths())
            );
            myAdapter.notifyDataSetChanged();
        }
        else if(item.getItemId()==R.id.total_recovered_cases){
            Collections.sort(
                    list,(c1,c2)-> Integer.parseInt(c2.getTotalRecovered())- Integer.parseInt(c1.getTotalRecovered())
            );
            myAdapter.notifyDataSetChanged();
        }
        return super.onOptionsItemSelected(item);
    }
}