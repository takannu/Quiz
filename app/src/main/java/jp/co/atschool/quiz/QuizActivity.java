package jp.co.atschool.quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jp.co.atschool.quiz.Utils.CharByCharTextView;

public class QuizActivity  extends AppCompatActivity implements View.OnClickListener {

    TextView countTextView;
    TextView contentTextView;
    Button optionButtons[];
    CharByCharTextView tvGirlMessage;
    ImageView ivCharactor;
    int questionNumber;
    int point;
    List<Quiz> quizList;

    private Handler mHandler = new Handler();
    private Runnable updateText;

    private String[] messageArray = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        countTextView = (TextView) findViewById(R.id.countTextView);
        contentTextView = (TextView) findViewById(R.id.contentTextView);

        optionButtons = new Button[3];
        optionButtons[0] = findViewById(R.id.optionButton1);
        optionButtons[1] = findViewById(R.id.optionButton2);
        optionButtons[2] = findViewById(R.id.optionButton3);

        tvGirlMessage = (CharByCharTextView) findViewById(R.id.tvGirlMessage);
        ivCharactor = (ImageView) findViewById(R.id.ivCharactor);

        for (Button button : optionButtons) {
            button.setOnClickListener(this);
        }

        // 性別によってキャラクター画像やメッセージを変更する
        SharedPreferences data = getSharedPreferences("DataSave", Context.MODE_PRIVATE);
        int sex = data.getInt("Sex",1 );

        if (sex == 1) {
            // 男ならキャラクターは女の子
            messageArray = new String[]{"なるほどー。", "そうなんですね。", "そういうことですか。", "それはちょっと。", "私もその選択を\n取ると思います。", "それは予想外の\n選択です。", "そこそこ良い\n選択を取りましたね。\nお見事です。", "それを選ぶとは、\nあなたは変わったお方ですね。", "その選択は冒険者として当然ですね。", "うーん、迷いますが、\nそれでしょうかね。", "やりおりますね〜。"};
            ivCharactor.setImageResource(R.drawable.girl);
        } else {
            messageArray = new String[]{"そうか！\nうん、なるほど！！", "それな！\n俺もそう思うぜ！", "そういうことか！", "その選択はなかった！", "俺もそれを\n選ぶと思うぜ！", "それは予想外だな！", "なかなか良い選択だな！", "それを選ぶとは、\n君は変わった人だな！", "その選択は冒険者として当然だな！", "迷いどころだが、それしかないな！", "そりゃそうだよな！"};
            ivCharactor.setImageResource(R.drawable.boy);
        }

        quizReset();
    }

    void showQuiz() {
        countTextView.setText((questionNumber + 1) + "問目");
        Quiz quiz = quizList.get(questionNumber);
        contentTextView.setText(quiz.content);
        optionButtons[0].setText(quiz.option1);
        optionButtons[1].setText(quiz.option2);
        optionButtons[2].setText(quiz.option3);
        tvGirlMessage.setText("");
    }

    void createQuiz() {
        Quiz quiz1 = new Quiz("いよいよ冒険の出発です。\n最初の街で野放しにされました。\nあなたの最初の行動は？", "街を探索する", "とりあえず外に出てみる", "オープニングは見たので明日続きをやる。", "とりあえず外に出てみる");
        Quiz quiz2 = new Quiz("道に迷ってしまいました。", "ネットで調べる", "あてもなく歩く", "電源を切る", "あてもなく歩く");
        Quiz quiz3 = new Quiz("お金が溜まってきました。\n何を買う？", "攻撃力10の剣", "防御力10の鎧", "さらに貯める", "攻撃力10の剣");
        Quiz quiz4 = new Quiz("やたらと強い高価な武器を拾いました。\n何か危険な匂いがします。\nリセット出来ません。どうする？", "装備する", "残しておく", "高値で売れるので売る", "装備する");
        Quiz quiz5 = new Quiz("旦那or嫁、3人の中から選ぶイベントが発生しました。", "一番強い人を選ぶ", "興味がないのでランダムに選ぶ", "好みのタイプにする", "好みのタイプにする");
        Quiz quiz6 = new Quiz("10時間プレイしてデータが消えてしまいました。\nこのゲームについて、あまり面白くないと思っていたあなたならどうする？", "エンディングがみたいのでもう一度やる", "興味がなくなったので別のゲームを買う", "ネタバレサイトを見て終わる", "興味がなくなったので別のゲームを買う");
        Quiz quiz7 = new Quiz("無限にアイテムが増やせるバグが発覚しました。\nどうする？", "最大限に利用する", "使わずにプレイする", "勝てない敵が出てきたら使う", "最大限に利用する");

        quizList = new ArrayList<>();
        quizList.add(quiz1);
        quizList.add(quiz2);
        quizList.add(quiz3);
        quizList.add(quiz4);
        quizList.add(quiz5);
        quizList.add(quiz6);
        quizList.add(quiz7);
    }

    void quizReset() {
        questionNumber = 0;
        point = 0;
        createQuiz();
        showQuiz();
    }

    void updateQuiz() {
        questionNumber++;
        if (questionNumber < quizList.size()) {
            showQuiz();
        } else {

            Intent intent = new Intent(QuizActivity.this, QuizResult.class);
            intent.putExtra("point", point);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, 1);

            finish(); // 画面を消しておく。
        }
    }

    @Override
    public void onClick(final View view) {

        // 待つ時間
        int waitTime = 2000;

        // ボタン連打無効にする
        setEnabledButton(false);

        Button clickedButton = (Button) view;
        if (questionNumber < quizList.size()) {
            Quiz quiz = quizList.get(questionNumber);

            if (TextUtils.equals(clickedButton.getText(), quiz.answer)) {
                point++;
            }

            // 吹き出しのメッセージを入れ込む
            Random r = new Random();
            int n = r.nextInt(messageArray.length);
            tvGirlMessage.setTargetText(messageArray[n]);
            tvGirlMessage.startCharByCharAnim();

            waitTime = messageArray[n].length()*300 - (messageArray[n].length()-5)*200;
        }

        updateText = new Runnable() {
            public void run() {
                updateQuiz();
                setEnabledButton(true);
            }
        };
        mHandler.postDelayed(updateText, waitTime);
    }

    private void setEnabledButton(boolean bool) {
        optionButtons[0].setEnabled(bool);
        optionButtons[1].setEnabled(bool);
        optionButtons[2].setEnabled(bool);
    }
}
