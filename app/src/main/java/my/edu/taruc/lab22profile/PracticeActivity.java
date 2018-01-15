package my.edu.taruc.lab22profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Random;

import javax.xml.transform.Result;

public class PracticeActivity extends AppCompatActivity {

    private static final int REQUEST_RESULT = 1;

    public TextView textViewNum;
    public  TextView textViewQuestion;
    public Button buttonA;
    public Button buttonB;
    public Button buttonC;
    public Button buttonD;
    public int countNum = 1;
    public float actualAns;
    public float selectAns;
    public int TotalCorrect = 0;

    public int prevScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_practice);

        SharedPreferences sharedPref = getSharedPreferences("userScore", Context.MODE_PRIVATE);

        prevScore = sharedPref.getInt("score", 0);


        textViewNum = (TextView)findViewById(R.id.textViewNum);
        textViewQuestion = (TextView)findViewById(R.id.textViewQuestion);
        buttonA = (Button)findViewById(R.id.buttonA);
        buttonB = (Button)findViewById(R.id.buttonB);
        buttonC = (Button)findViewById(R.id.buttonC);
        buttonD = (Button)findViewById(R.id.buttonD);
        CreateQuestion();
        textViewNum.setText(""+countNum);
    }


    void CreateQuestion(){
        int min = 1;
        int min2 = 0;
        int max = 15;
        float ans;

        Bundle bundle = getIntent().getExtras();
        String operation= bundle.getString("operation");

        Random r = new Random();
        int num1 = r.nextInt(max - min + 1) + min;
        int num2 = r.nextInt(num1 - min2 + 1) + min;
        int ansnum = r.nextInt(4-1+1)+1;

        ans = getAnswer(operation,num1,num2);

        setAnswer(ansnum,ans);
    }

    public float getAnswer(String operation,float num1,float num2){
        float ans = 0;
        float mix = randomAns(1,4);
        if(operation.equals("ADD")||(operation.equals("MIX")&&mix==1)){
            textViewQuestion.setText(num1 + "+" + num2 + " = ?");
            ans = num1 + num2;
        }
        else if(operation.equals("SUB")||(operation.equals("MIX")&&mix==2)){
            textViewQuestion.setText(num1 + "-" + num2 + " = ?");
            ans = num1 - num2;
        }
        else if(operation.equals("MUL")||(operation.equals("MIX")&&mix==3)){
            textViewQuestion.setText(num1 + "X" + num2 + " = ?");
            ans = num1 * num2;
        }
        else if(operation.equals("DIV")||(operation.equals("MIX")&&mix==4)){
            textViewQuestion.setText(num1 + "÷" + num2 + " = ?");
            ans = num1 / num2;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        ans = Float.valueOf(decimalFormat.format(ans));
        return ans;
    }

    public void setAnswer(int ansnum,float ans){
        buttonA.setText(""+randomAns((int)ans-10,(int)ans+10));
        buttonB.setText(""+randomAns((int)ans-10,(int)ans+10));
        buttonC.setText(""+randomAns((int)ans-10,(int)ans+10));
        buttonD.setText(""+randomAns((int)ans-10,(int)ans+10));
        actualAns = ans;

        if(ansnum == 1){
            buttonA.setText(""+ans);
        }
        else if(ansnum == 2){
            buttonB.setText(""+ans);
        }
        else if(ansnum == 3){
            buttonC.setText(""+ans);
        }
        else if(ansnum == 4){
            buttonD.setText(""+ans);
        }
    }

    public float randomAns(int min,int max){
        Random r = new Random();
        float randans = r.nextInt(max - min + 1) + min;
        return randans;
    }

    public void buttonAans(View view){
        if(checkAns(view)){
            TotalCorrect++;
        }
        if(countNum<12){
            countNum++;
            textViewNum.setText(""+countNum);
            CreateQuestion();
        }
        else if(countNum >= 12){
            String comment;

            SharedPreferences sharedPref = getSharedPreferences("userScore", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putInt("score", TotalCorrect);
            editor.apply();

            if(TotalCorrect >= prevScore)
            {
                comment = "Congratulations!! YOU BEAT YOUR HIGH SCORE!!";
            }
            else
            {
                comment = "Don't Give Up! Try to beat your high score next time!";
            }

            String i= Integer.toString(TotalCorrect);
            String type = "practice";
            Bundle bundle = new Bundle();
            bundle.putString("result", i);
            bundle.putString("layout", type);
            bundle.putString("comment", comment);

            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_RESULT);
            finish();
        }

    }

    public boolean checkAns(View view){
        String tempstr = "999999";
        if (view.getId() == R.id.buttonA){
            tempstr = buttonA.getText().toString();
            selectAns = Float.parseFloat(tempstr);
        }
        else if (view.getId() == R.id.buttonB){
            tempstr = buttonB.getText().toString();
            selectAns = Float.parseFloat(tempstr);
        }
        else if (view.getId() == R.id.buttonC){
            tempstr = buttonC.getText().toString();
            selectAns = Float.parseFloat(tempstr);
        }
        else if (view.getId() == R.id.buttonD){
            tempstr = buttonD.getText().toString();
            selectAns = Float.parseFloat(tempstr);
        }
        if(selectAns == actualAns){
            return true;
        }
        else {
            return false;
        }
    }

}
