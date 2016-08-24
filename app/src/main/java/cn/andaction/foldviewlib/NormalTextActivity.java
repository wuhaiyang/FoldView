package cn.andaction.foldviewlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;

/**
 * User: Geek_Soledad(wuhaiyang@danlu.com)
 * Date: 2016-08-23
 * Time: 15:29
 * Description: .....
 */
public class NormalTextActivity extends AppCompatActivity {


    String[] promotionTmpList = new String[] {
            "满[￥20]，单价减[￥1];满[￥50]，单价减[￥2];满[￥100]，单价减[￥3];满[￥500]，单价减[￥10]",
            "满[￥20]，总价减[￥5];满[￥50]，总价减[￥10];满[￥100]，总价减[￥20];满[￥500]，总价减[￥100]",
            "每满[2]瓶，赠送[本品]×[1]瓶，最多赠送[3]瓶",
            "满[￥10]，赠送[测试自动化红茶01MaMaxMaxMaxMaxMaxMaxMaxMaxMaxMaxMaxMaxMax 扩展名MaxMaxMaxMaxMaxMa]×[1]袋",
            "每满[3]瓶，单价减[￥1.01]",
            "每满[3]瓶，总价减[￥6.66]"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_activity_normaltext);
        final RichTextView rtvContent = (RichTextView) findViewById(R.id.rtv_content);
        List<String> strings = Arrays.asList(promotionTmpList);
        rtvContent.bindData(strings, true);
        final ImageView ivFold = (ImageView) findViewById(R.id.iv_fold);
        ivFold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rtvContent.foldAction();
            }
        });
        rtvContent.setOnFoldListener(new RichTextView.OnFoldListener() {
            @Override
            public void onFold(boolean isFold) {
                ivFold.setImageResource(isFold ? R.drawable.icon_fold_down : R.drawable.dl_icon_fold_up);
            }
        });
    }


}
