package br.com.hisao.json;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStartProcess = (Button) findViewById(R.id.btn_startprocess);
        btnStartProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url ="https://api.github.com/users/gustavoemmel/repos";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                //mTextView.setText("Response is: "+ response.substring(0,500));

                                try {
                                    JSONArray jsonArray = new JSONArray(response);

                                    ListView lstNumbers =(ListView)findViewById(R.id.lsv_users);
                                    lstNumbers.setAdapter(new GithubUsersAdapter(jsonArray, MainActivity.this));

                                    for (int i=0; i<= jsonArray.length(); i++){

                                        JSONObject explrObject;
                                        explrObject = jsonArray.getJSONObject(i);
                                        Log.d("Gustavo", explrObject.getString("name"));
                                        Log.d("Gustavo", explrObject.getJSONObject("owner").getString("avatar_url"));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Log.d("Gustavo", "Response is: "+ response.substring(0,500));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //mTextView.setText("That didn't work!");
                    }
                });
// Add the request to the RequestQueue.
                queue.add(stringRequest);

//
//                AsyncTask<Void, Void, JSONArray> taskGetJson = new AsyncTask<Void, Void, JSONArray>() {
//                    @Override
//                    protected JSONArray doInBackground(Void... params) {
//                        return JsonUtil.getJson("https://api.github.com/users/gustavoemmel/repos");
//                    }
//
//                    @Override
//                    protected void onPostExecute(JSONArray jsonArray) {
//                        super.onPostExecute(jsonArray);
//                        if (BuildConfig.DEBUG){
//                            try {
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONObject explrObject;
//                                    explrObject = jsonArray.getJSONObject(i);
//                                    Log.d("Gustavo", explrObject.getString("name"));
//                                    Log.d("Gustavo", explrObject.getJSONObject("owner").getString("avatar_url"));
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            } catch (Exception e){
//                                e.printStackTrace();
//                            }
//                        }
//
//                        ListView lstNumbers =(ListView)findViewById(R.id.lsv_users);
//                        lstNumbers.setAdapter(new GithubUsersAdapter(jsonArray, MainActivity.this));
//                    }
//                };
//                taskGetJson.execute(null, null, null);
            }
    }

    );
}

}
