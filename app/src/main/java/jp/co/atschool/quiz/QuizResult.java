package jp.co.atschool.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizResult extends AppCompatActivity {

    TextView tvTitle;
    ImageView ivPrograms;
    TextView tvContent;
    Integer point;
    Button bBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        ivPrograms = (ImageView) findViewById(R.id.ivPrograms);
        tvContent = (TextView) findViewById(R.id.tvContent);
        bBack = (Button) findViewById(R.id.bBack);

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        point = intent.getIntExtra("point", 0);

        setView();
    }

    void setView() {
        if ( point == 0 ) {
            // unity
            ivPrograms.setImageResource(R.drawable.unity);
            tvContent.setText("あなたはゲーマーでしょう。ゲームをこよなく愛している可能性が高いです。ゲームをやるのは好きだけど、開発はちょっと。。。と言った方かもしれません。そんなあなたにオススメなのは、Unityというアプリゲーム開発エンジンです。言語ではありませんが、一度触って見てはいかがでしょうか。");
        } else if ( point > 0 && point <= 2 ) {
            // c++
            ivPrograms.setImageResource(R.drawable.c);
            tvContent.setText("あなたの性格は一言でいうと堅実です。変化を求めてはいるけど、リスクは負いたくない、と言った方ではないでしょうか。まずはプログラムの基本と言われているC言語から進めていき、その後柔軟に別言語を覚えていくとよいでしょう。");
        } else if ( point > 2 && point <= 4 ) {
            // php
            ivPrograms.setImageResource(R.drawable.php);
            tvContent.setText("少し飽き性なあなたは面白味がない出来事が苦手です。変化を求め、リアルタイムな言語がオススメです。ウェブサービスとなるPHPやサーバ系がベストと言えるでしょう。");
        } else {
            // swift
            ivPrograms.setImageResource(R.drawable.swift);
            tvContent.setText("とにかく突き進むあなたは、後先を考えるよりも面白いことを優先します。これからの事を考えるよりも、今が最も大事なのでしょう。アプリエンジニアとして生きていき、これからは最新の言語を常に追い求めていくと吉です。");
        }
    }
}
