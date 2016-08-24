package cn.andaction.foldviewlib;

import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Geek_Soledad(wuhaiyang@danlu.com)
 * Date: 2016-08-23
 * Time: 15:29
 * Description: .....
 */
public class ListViewActivity extends AppCompatActivity {

    private ListView lvListView;
    private final List<WrapData> mWrapDatas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa_activity_list);
        lvListView = (ListView) findViewById(R.id.listview);
        addTestDatas();
        lvListView.setAdapter(new ContentAdapter());
    }
    private class ContentAdapter extends BaseAdapter{

        private final ArrayMap<Integer,Boolean> mCacheModels = new ArrayMap<>();
        @Override
        public int getCount() {
            return mWrapDatas.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (null == convertView) {
                convertView = LayoutInflater.from(ListViewActivity.this).inflate(R.layout.aa_item_info,parent,false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            WrapData wrapData = mWrapDatas.get(position);
            boolean isFold = true;
            Boolean cacheIsFold = mCacheModels.get(position);
            if (null != cacheIsFold) {
                isFold = cacheIsFold;
            }
            holder.rtvContent.bindData(wrapData.getPromotionLists(), isFold);
            holder.iv_fold.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RelativeLayout rlParent = (RelativeLayout) v.getParent();
                    RichTextView rtvContent = (RichTextView) rlParent.findViewById(R.id.rtv_content);
                    //调用折叠 action
                    rtvContent.foldAction();
                }
            });
            holder.rtvContent.setOnFoldListener(new RichTextView.OnFoldListener() {
                @Override
                public void onFold(boolean isFold) {
                    Boolean isFoldObj = mCacheModels.get(position);
                    isFoldObj = new Boolean(isFold);
                    mCacheModels.put(position,isFoldObj);
                }
            });
            return convertView;
        }
        private  class ViewHolder {
            RichTextView rtvContent;
            ImageView iv_fold;
            public ViewHolder(View view) {
                rtvContent = (RichTextView) view.findViewById(R.id.rtv_content);
                iv_fold = (ImageView) view.findViewById(R.id.iv_fold);
            }
        }
    }
    private void addTestDatas() {
        WrapData wrapData1 = new WrapData();
        ArrayList<String> promotion1 = new ArrayList<>();
        promotion1.add("满[￥20]，单价减[￥1];满[￥50]，单价减[￥2];满[￥100]，单价减[￥3];满[￥500]，单价减[￥10]");
        promotion1.add("满[￥10]，赠送[测试自动扩展名MaxMaxMaxMaxMaxMa]×[1]袋");
        promotion1.add("满[￥10]，赠送[测试自动化红茶01MaMaxMaxMaxMaxMaxMaxMaxMaxMaxMaxMaxMaxMax 扩展名MaxMaxMaxMaxMaxMa]×[1]袋");
        promotion1.add("每满[2]瓶，赠送[本品]×[1]瓶，最多赠送[3]瓶");
        promotion1.add("每满[3]瓶，总价减[￥6.66]");
        promotion1.add("每满[3]瓶，单价减[￥1.01]");
        wrapData1.setPromotionLists(promotion1);
        mWrapDatas.add(wrapData1);

        WrapData wrapData2 = new WrapData();
        ArrayList<String> promotion2 = new ArrayList<>();
        promotion2.add("每满[2]瓶，赠送[本品]×[1]瓶，最多赠送[3]瓶");
        promotion2.add("满[￥10]，赠送[测试自动扩展名MaxMaxMaxMaxMaxMa]×[1]袋");
        promotion2.add("每满[3]瓶，总价减[￥6.66]");
        promotion2.add("满[￥20]，单价减[￥1];满[￥50]，单价减[￥2];满[￥100]，单价减[￥3];满[￥500]，单价减[￥10]");
        promotion2.add("每满[3]瓶，单价减[￥1.01]");
        wrapData2.setPromotionLists(promotion2);
        mWrapDatas.add(wrapData2);


        WrapData wrapData3 = new WrapData();
        ArrayList<String> promotion3 = new ArrayList<>();
        promotion3.add("每满[3]瓶，单价减[￥1.01]");
        promotion3.add("满[￥20]，单价减[￥1];满[￥50]，单价减[￥2];满[￥100]，单价减[￥3];满[￥500]，单价减[￥10]");
        promotion3.add("每满[3]瓶，总价减[￥6.66]");
        wrapData3.setPromotionLists(promotion3);
        mWrapDatas.add(wrapData3);


        WrapData wrapData4 = new WrapData();
        ArrayList<String> promotion4 = new ArrayList<>();
        promotion4.add("满[￥10]，赠送[测试自动化红茶01MaMaxMaxMaxMaxMaxMaxMaxMaxMaxMaxMaxMaxMax 扩展名MaxMaxMaxMaxMaxMa]×[1]袋");
        promotion4.add("满[￥20]，单价减[￥1];满[￥50]，单价减[￥2];满[￥100]，单价减[￥3];满[￥500]，单价减[￥10]");
        promotion4.add("满[￥10]，赠送[测试自动扩展名MaxMaxMaxMaxMaxMa]×[1]袋");
        promotion4.add("每满[2]瓶，赠送[本品]×[1]瓶，最多赠送[3]瓶");
        wrapData4.setPromotionLists(promotion4);
        mWrapDatas.add(wrapData4);


        WrapData wrapData5 = new WrapData();
        ArrayList<String> promotion5 = new ArrayList<>();
        promotion5.add("每满[3]瓶，单价减[￥1.01]");
        promotion5.add("每满[3]瓶，总价减[￥6.66]");
        promotion5.add("满[￥20]，单价减[￥1];满[￥50]，单价减[￥2];满[￥100]，单价减[￥3];满[￥500]，单价减[￥10]");
        promotion5.add("每满[2]瓶，赠送[本品]×[1]瓶，最多赠送[3]瓶");
        promotion5.add("满[￥10]，赠送[测试自动化红茶01MaMaxMaxMaxMaxMaxMaxMaxMaxMaxMaxMaxMaxM 扩展名MaxMaxMaxMaxMaxMa]×[1]袋");
        wrapData5.setPromotionLists(promotion5);
        mWrapDatas.add(wrapData5);


        WrapData wrapData6 = new WrapData();
        ArrayList<String> promotion6 = new ArrayList<>();
        promotion6.add("满[￥20]，单价减[￥1];满[￥50]，单价[￥3];满[￥500]，单价减[￥10]");
        promotion6.add("满[￥10]，赠送[测试自动扩展名MaxMaxMaxMaxMaxMa]×[1]袋");
        promotion6.add("满[￥10]，赠送[ 扩展名MaxMaxMaxMaxMaxMa]×[1]袋");
        promotion6.add("每满[2]瓶，赠送[本品]×[1]瓶，最多赠送[3]瓶");
        promotion6.add("每满[3]瓶，总价减[￥6.66]");
        promotion6.add("每满[3]瓶，单价减[￥1.01]");
        wrapData6.setPromotionLists(promotion6);
        mWrapDatas.add(wrapData6);


        WrapData wrapData7 = new WrapData();
        ArrayList<String> promotion7 = new ArrayList<>();
        promotion7.add("满[￥10asd]，赠送[测试自动扩展名MaxMaxMaxMaxMaxMa]×[1]袋");
        promotion7.add("满[￥1asd0]，赠送[测试MaxMax 扩展名MaxMaxMaxMaxMaxMa]×[1]袋");
        promotion7.add("每满[2asd]瓶，赠送[本品]×[1]瓶，最多赠送[3]瓶");
        promotion7.add("每满[3]瓶，总价减[ad￥6.12366]");
        promotion7.add("每满[3]瓶，单价减[￥1.0AS1]");
        wrapData7.setPromotionLists(promotion1);
        mWrapDatas.add(wrapData7);

        WrapData wrapData8 = new WrapData();
        ArrayList<String> promotion8 = new ArrayList<>();
        promotion8.add("满[￥20]，单价减[￥1];满[￥50]，单价减[￥2];满[￥100]，单价减[￥3];满[￥500]");
        wrapData8.setPromotionLists(promotion8);
        mWrapDatas.add(wrapData8);

        WrapData wrapData9 = new WrapData();
        ArrayList<String> promotion9 = new ArrayList<>();
        promotion9.add("满[￥20]，单价减[￥1];满[￥50]，单价减[￥2];满[￥100]，单价减[￥3];满[￥500]，单价减[￥10]");
        promotion9.add("满[￥10]，赠送[测试自动扩展名MaxMaxMaxMaxMaxMa]×[1]袋");
        promotion9.add("满[￥10]，赠送[测试自动化红茶01MaMaxMaxMaxMaxMaxMaxMaxMaxMaxMaxMaxMaxMax 扩展名MaxMaxMaxMaxMaxMa]×[1]袋");
        promotion9.add("每满[2]瓶，赠送[本品]×[1]瓶，最多赠送[3]瓶");
        promotion9.add("每满[3]瓶，总价减[￥6.66]");
        promotion9.add("每满[3]瓶，单价减[￥1.01]");
        wrapData9.setPromotionLists(promotion9);
        mWrapDatas.add(wrapData9);

        WrapData wrapData10 = new WrapData();
        ArrayList<String> promotion10 = new ArrayList<>();
        promotion10.add("满[￥20]，这是一首简单的[测试数据测试数据]");
        promotion10.add("满[￥10]，赠送[测试自动扩展名MaxMaxMaxMaxMaxMa]×[1]袋");
        promotion10.add("满[￥10]，赠送[ 1* 182 扩展名MaxMaxMaxMaxMaxMa]×[1]袋");
        promotion10.add("每满[2]瓶，赠送[本品]×[1]瓶，最多赠送[3]瓶");
        promotion10.add("每满[3]瓶，总价减[￥9.09]");
        promotion10.add("每满[3]瓶，单价减[￥34.01]");
        wrapData10.setPromotionLists(promotion10);
        mWrapDatas.add(wrapData10);
    }


}
