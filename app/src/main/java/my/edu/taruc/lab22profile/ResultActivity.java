package my.edu.taruc.lab22profile;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    public TextView textViewResult;
    public TextView textViewPlayer1Result;
    public TextView textViewPlayer2Result;
    public TextView textViewComment;
    public Button buttonOk;
    String result,player1,player2, comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Bundle bundle = getIntent().getExtras();
        result= bundle.getString("layout");
        if(result.equals("practice")){
            setContentView(R.layout.activity_result);
            textViewResult = (TextView)findViewById(R.id.textViewResult);
            textViewComment = (TextView) findViewById(R.id.textViewComment);
            getResult1();
            saveRecord();
        }
        else if(result.equals("challenge")){
            setContentView(R.layout.activity_result2);
            textViewPlayer1Result = (TextView)findViewById(R.id.textViewPlayer1Result);
            textViewPlayer2Result = (TextView)findViewById(R.id.textViewPlayer2Result);
            textViewComment = (TextView) findViewById(R.id.textViewComment2);
            getResult2();
        }
        buttonOk = (Button)findViewById(R.id.buttonOk);
    }

    public void saveRecord() {
        Score score = new Score();

        score.setScore(textViewResult.getText().toString());

        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "No network", Toast.LENGTH_SHORT).show();
        }

        try {
            makeServiceCall(this, getString(R.string.insert_score_url), score);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    void getResult1(){
        Bundle bundle = getIntent().getExtras();
        result= bundle.getString("result");
        comment = bundle.getString("comment");
        textViewResult.setText(result + " / 12");
        textViewComment.setText(comment);
    }

    void getResult2(){
        Bundle bundle = getIntent().getExtras();
        player1= bundle.getString("result1");
        player2= bundle.getString("result2");
        comment = bundle.getString("comment2");
        textViewPlayer1Result.setText(player1 + " / 12");
        textViewPlayer2Result.setText(player2 + " / 12");
        textViewComment.setText(comment);
    }

    public void makeServiceCall(Context context, String url, final Score score) {
        //mPostCommentResponse.requestStarted();
        RequestQueue queue = Volley.newRequestQueue(context);

        //Send data
        try {
            StringRequest postRequest = new StringRequest(
                    Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject;
                            try {
                                jsonObject = new JSONObject(response);
                                int success = jsonObject.getInt("success");
                                String message = jsonObject.getString("message");
                                if (success==0) {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                    //finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error. " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("score", score.getScore());
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            queue.add(postRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    }

    public void OK(View view){
        finish();
    }
}
