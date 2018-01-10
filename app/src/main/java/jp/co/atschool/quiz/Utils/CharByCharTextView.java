package jp.co.atschool.quiz.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by nttr on 2017/12/14.
 */

public class CharByCharTextView extends android.support.v7.widget.AppCompatTextView {
    String defautText = "文字列を１文字ずつ出力するテスト";
    private static int TIMEOUT_MESSAGE = 1;
    private static int INTERVAL = 2;

    // Meta Data
    int i = 0;
    String putWord = "";
    String putText = "";

    public void startCharByCharAnim() {
        initMetaData();
        handler.sendEmptyMessage(TIMEOUT_MESSAGE);
    }

    public void setTargetText(String target) {
        this.defautText = target;
    }

    public CharByCharTextView(Context context) {
        super(context);
    }

    public CharByCharTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CharByCharTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initMetaData() {
        i = 0;
        putWord = "";
        putText = "";
    }

    // 文字列を一文字ずつ出力するハンドラ
    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            // 文字列を配列に１文字ずつセット
            char data[] = defautText.toCharArray();

            // 配列数を取得
            int arrNum = data.length;

            if (i < arrNum) {
                if (msg.what == TIMEOUT_MESSAGE) {
                    putWord = String.valueOf(data[i]);
                    putText = putText + putWord;

                    setText(putText);
                    handler.sendEmptyMessageDelayed(TIMEOUT_MESSAGE, INTERVAL * 50);
                    i++;
                } else {
                    super.dispatchMessage(msg);
                }
            }
        }
    };
}
