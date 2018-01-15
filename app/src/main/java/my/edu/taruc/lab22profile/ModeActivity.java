package my.edu.taruc.lab22profile;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ModeActivity extends AppCompatActivity {

    private static final int REQUEST_PRACTICE_MODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_mode);
    }

    public void Practicemode(Bundle bundle){
        Intent intent = new Intent(this,PracticeActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_PRACTICE_MODE);
        finish();
    }

    public void Additional(View view){
        String i="ADD";
        Bundle bundle = new Bundle();
        bundle.putString("operation", i);
        Practicemode(bundle);
    }

    public void Subtraction(View view){
        String i="SUB";
        Bundle bundle = new Bundle();
        bundle.putString("operation", i);
        Practicemode(bundle);
    }

    public void Multiplication(View view){
        String i="MUL";
        Bundle bundle = new Bundle();
        bundle.putString("operation", i);
        Practicemode(bundle);
    }

    public void Division(View view){
        String i="DIV";
        Bundle bundle = new Bundle();
        bundle.putString("operation", i);
        Practicemode(bundle);
    }

    public void Mix(View view){
        String i="MIX";
        Bundle bundle = new Bundle();
        bundle.putString("operation", i);
        Practicemode(bundle);
    }

}
