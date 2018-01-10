package jp.co.atschool.quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Random;

import jp.co.atschool.quiz.Utils.CharByCharTextView;

public class SexSelectActivity extends AppCompatActivity {

    CharByCharTextView tvSexSelectMessage;
    RadioGroup rgSex;
    RadioButton rbMen;
    RadioButton rbWomen;
    Button bNext;

    private Handler mHandler = new Handler();
    private Runnable updateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex_select);

        rgSex = findViewById(R.id.rgSex);
        rbMen = findViewById(R.id.rbMen);
        rbWomen = findViewById(R.id.rbWomen);
        bNext = findViewById(R.id.bNext);
        tvSexSelectMessage = findViewById(R.id.tvSexSelectMessage);

        tvSexSelectMessage.setText("プログラム診断の世界へようこそ。\nあなたは冒険者として質問に答えて下さい。\nまずはあなたの性別を教えて下さい。");

        rgSex.setVisibility(View.GONE);
        rgSex.check(R.id.rbMen);
        bNext.setVisibility(View.GONE);

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SexSelectActivity.this, SexSelectNextActivity.class);
                startActivity(intent);

                SharedPreferences data = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = data.edit();
                if (rgSex.getCheckedRadioButtonId() == R.id.rbMen) {
                    editor.putInt("Sex", 1);
                } else {
                    editor.putInt("Sex", 2);
                }
                editor.apply();

                finish(); // 画面を消しておく。
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.messageAction();
    }

    private void messageAction() {
        // 待つ時間
        int waitTime;

        // 吹き出しのメッセージを入れ込む
        Random r = new Random();
        int n = r.nextInt(tvSexSelectMessage.getText().length());
        tvSexSelectMessage.setTargetText((String) tvSexSelectMessage.getText());
        tvSexSelectMessage.startCharByCharAnim();

        waitTime =tvSexSelectMessage.getText().length()*300 - (tvSexSelectMessage.getText().length()-5)*200;

        updateText = new Runnable() {
            public void run() {
                rgSex.setVisibility(View.VISIBLE);
                bNext.setVisibility(View.VISIBLE);
            }
        };
        mHandler.postDelayed(updateText, waitTime);
    }
}
