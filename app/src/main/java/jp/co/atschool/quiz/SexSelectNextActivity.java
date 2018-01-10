package jp.co.atschool.quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

import jp.co.atschool.quiz.Utils.CharByCharTextView;

public class SexSelectNextActivity extends AppCompatActivity {

    CharByCharTextView tvSexSelectNextMessage;
    ConstraintLayout clGirl;
    ImageView ivCharactor;
    CharByCharTextView tvSexSelectNextGirlMessage;
    Button bNext;

    private Handler mHandler = new Handler();
    private Runnable updateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex_select_next);

        tvSexSelectNextMessage = findViewById(R.id.tvSexSelectNextMessage);
        clGirl = findViewById(R.id.clGirl);
        ivCharactor = findViewById(R.id.ivCharactor);
        tvSexSelectNextGirlMessage = findViewById(R.id.tvSexSelectNextGirlMessage);
        bNext = findViewById(R.id.bNext);

        clGirl.setVisibility(View.GONE); // お供は最初非表示
        bNext.setVisibility(View.GONE); // 開始ボタンを非表示

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SexSelectNextActivity.this, QuizActivity.class);
                startActivity(intent);

                finish(); // 画面を消しておく。
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.setCharactor();
        this.messageAction();
    }

    private void messageAction() {
        // 待つ時間
        int waitTime;

        // 吹き出しのメッセージを入れ込む
        Random r = new Random();
        int n = r.nextInt(tvSexSelectNextMessage.getText().length());
        tvSexSelectNextMessage.setTargetText((String) tvSexSelectNextMessage.getText());
        tvSexSelectNextMessage.startCharByCharAnim();

        waitTime =tvSexSelectNextMessage.getText().length()*300 - (tvSexSelectNextMessage.getText().length()-5)*200;

        updateText = new Runnable() {
            public void run() {
                clGirl.setVisibility(View.VISIBLE); // お供を表示する
                bNext.setVisibility(View.VISIBLE); // 開始ボタンを表示する
            }
        };
        mHandler.postDelayed(updateText, waitTime);
    }

    private void setCharactor() {
        SharedPreferences data = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
        int sex = data.getInt("Sex",1 );

        if ( sex == 1 ) {
            // 男
            ivCharactor.setImageResource(R.drawable.girl);
            tvSexSelectNextGirlMessage.setText("よろしくね。");
        } else {
            // 女
            ivCharactor.setImageResource(R.drawable.boy);
            tvSexSelectNextGirlMessage.setText("よろしく！");
        }
    }
}
