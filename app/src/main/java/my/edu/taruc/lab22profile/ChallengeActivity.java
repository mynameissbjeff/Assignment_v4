package my.edu.taruc.lab22profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Set;


public class ChallengeActivity extends AppCompatActivity {

    private static final int REQUEST_MAIN_MENU = 1;
    private static final int REQUEST_RESULT = 1;

    private LinearLayout player1layout;
    private LinearLayout player2layout;

    public  TextView textViewQuestions;
    public TextView textViewNums;
    public TextView textViewTimer;
    public TextView textViewAnswer1;
    public TextView textViewAnswer2;

    public ImageButton imageButtonTrue1;
    public ImageButton imageButtonFalse1;
    public ImageButton imageButtonTrue2;
    public ImageButton imageButtonFalse2;

    public int counter;

    public int countNum = 1;
    public int TotalCorrect1=0;
    public int TotalCorrect2=0;
    public boolean givenAns;
    public boolean haveanswer1 =false, haveanswer2 =false;
    String comment2 = " ";
    String comment3 = " ";
    String comment4 = " ";
    String comment5 = " ";
    Set<String> commentset;


    public CountDownTimer counttimer;
    public CountDownTimer pausetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_challenge);

        SharedPreferences sharedPref = getSharedPreferences("ChallengeScore", Context.MODE_PRIVATE);

        if(sharedPref!=null) {
            comment2 = sharedPref.getString("score1", "");
            comment3 = sharedPref.getString("score2", "");
            comment4 = sharedPref.getString("score3", "");
            comment5 = sharedPref.getString("score4", "");
        }

        player1layout = (LinearLayout)findViewById(R.id.linearLayoutPlayer1);
        player2layout = (LinearLayout)findViewById(R.id.linearLayoutPlayer2);

        textViewNums = (TextView)findViewById(R.id.textViewNums);
        textViewQuestions = (TextView)findViewById(R.id.textViewQuestions);
        textViewTimer = (TextView)findViewById(R.id.textViewTimer);
        textViewNums.setText(countNum + "/12");
        CreateQuestions();
    }

    void CreateQuestions(){
        player1layout.setBackgroundColor(getResources().getColor(R.color.lightpink));
        player2layout.setBackgroundColor(getResources().getColor(R.color.lightpink));
        counter = 10;
        counttimer = new CountDownTimer(11000, 1000){
            public void onTick(long millisUntilFinished){
                textViewTimer.setText(String.valueOf("Time Left: "+ counter));
                counter--;
                haveAnswerCheck();
            }
            public void onFinish(){
                textViewTimer.setText("Times Up!!");
                haveanswer1=true;
                haveanswer2=true;
                haveAnswerCheck();

            }
        };
        counttimer.start();
        haveanswer1 =false;
        haveanswer2 =false;
        int min = 1;
        int min2 = 0;
        int max = 15;

        Random r = new Random();
        int num1 = r.nextInt(max - min + 1) + min;
        int num2 = r.nextInt(num1 - min2 + 1) + min;

        givenAns = getAnswers(num1,num2);
    }

    public void buttonAnss(View view){
        checkAnss(view);
        haveAnswerCheck();
    }

    public void checkAnss(View view){
        if(!haveanswer1){
            if (view.getId() == R.id.imageButtonTrue1){
                if(givenAns) {
                    TotalCorrect1++;
                }
                haveanswer1 =true;
            }
            else if (view.getId() == R.id.imageButtonFalse1){
                if(!givenAns) {
                    TotalCorrect1++;
                }
                haveanswer1 =true;
            }
        }
        if(!haveanswer2){
            if (view.getId() == R.id.imageButtonTrue2){
                if(givenAns) {
                    TotalCorrect2++;
                }
                haveanswer2 = true;
            }
            else if (view.getId() == R.id.imageButtonFalse2) {
                if (!givenAns) {
                    TotalCorrect2++;
                }
                haveanswer2 = true;
            }
        }
    }

    public boolean getAnswers(float num1,float num2){
        float ans = 0;
        float mix = randomAnss(1,4);
        float answer = randomAnss(0,1);

        if(mix==1){
            ans = num1 + num2;
            textViewQuestions.setText((int)num1 + "+" + (int)num2 + " = ");
        }
        else if(mix==2){
            ans = num1 - num2;
            textViewQuestions.setText((int)num1 + "-" + (int)num2 + " = ");
        }
        else if(mix==3){
            ans = num1 * num2;
            textViewQuestions.setText((int)num1 + "X" + (int)num2 + " = ");
        }
        else if(mix==4){
            ans = num1 / num2;
            textViewQuestions.setText((int)num1 + "÷" + (int)num2 + " = ");
        }
        if(answer==0){
            givenAns=true;
        }
        else if(answer==1){
            ans = randomAnss((int)ans-10,(int)ans+10);
            givenAns=false;
        }
        if(Math.round(ans)!=ans){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            ans = Float.valueOf(decimalFormat.format(ans));
        }
        else
        {
            ans = (int)ans;
        }
        textViewQuestions.setText(textViewQuestions.getText()+""+ans);
        //DecimalFormat decimalFormat = new DecimalFormat("#.##");
        //ans = Float.valueOf(decimalFormat.format(ans));
        return givenAns;
    }

    public float randomAnss(int min,int max){
        Random r = new Random();
        float randans = r.nextInt(max - min + 1) + min;
        if(Math.round(randans)!=randans){
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            randans = Float.valueOf(decimalFormat.format(randans));
        }
        else
        {
            randans = (int)randans;
        }
        return randans;
    }

    public void haveAnswerCheck(){
        if(haveanswer1 == true){
            player1layout.setBackgroundColor(getResources().getColor(R.color.lightgreen));
        }
        if(haveanswer2 == true){
            player2layout.setBackgroundColor(getResources().getColor(R.color.lightgreen));
        }
        if(countNum<12){
            if(haveanswer1 ==true&& haveanswer2 ==true) {
                counttimer.cancel();
                countNum++;
                textViewNums.setText(countNum + "/12");
                CreateQuestions();
            }
        }
        else if(countNum >= 12&& haveanswer1 ==true&& haveanswer2 ==true){
            counttimer.cancel();

            String comment;

            SharedPreferences sharedPref = getSharedPreferences("ChallengeScore", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            //String[] SET_VALUES = new String[] {Integer.toString(TotalCorrect1),Integer.toString(TotalCorrect2)};
            //Set<String> MY_SET = new HashSet<>(Arrays.asList(SET_VALUES));

            //editor.putStringSet("score3",MY_SET);

            if(TotalCorrect1 > TotalCorrect2)
            {
                comment = "Player 1 Beat Player 2";
            }
            else if(TotalCorrect1 < TotalCorrect2)
            {
                comment = "Player 2 Beat Player 1";
            }
            else
            {
                comment = "Player 1 Draw Player 2";
            }

            //String[] SET_HISTORY = new String[] {comment,comment2,comment3};
            //Set<String> VS_SET = new HashSet<>(Arrays.asList(SET_HISTORY));
            //editor.putStringSet("score4",VS_SET);

            editor.putString("score1",comment);
            editor.putString("score2",comment2);
            editor.putString("score3",comment3);
            editor.putString("score4",comment4);
            editor.putString("score5",comment5);

            editor.apply();

            String i= Integer.toString(TotalCorrect1);
            String j= Integer.toString(TotalCorrect2);
            String type = "challenge";
            Bundle bundle = new Bundle();
            bundle.putString("result1", i);
            bundle.putString("result2", j);
            bundle.putString("layout", type);

            bundle.putString("comment2", comment);

            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_RESULT);
            finish();
        }
    }
}
