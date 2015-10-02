package samples.exoguru.materialtabs.common.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

//建立BaseAdapter並將此Adapter置入Gallery內
public class ImageAdapter extends BaseAdapter
{
    private Context mContext ;
    private int[] mPics ;

    public ImageAdapter(Context c , int[] pics)
    {
        this.mContext = c;
        mPics = pics ;
    }

    @Override
    //Gallery圖片總數為int的最大值，目的為無限迴圈
    public int getCount()
    {
        return Integer.MAX_VALUE;
    }

    @Override
    //目前圖片位置除以圖片總數量的餘數
    public Object getItem(int position)
    {
        return position % mPics.length ;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //建立圖片
        ImageView img = new ImageView(this.mContext);
        //將圖片置入img，置入的圖片為目前位置的圖片除以圖片總數取餘數，此餘數為圖片陣列的圖片位置
        img.setImageResource(mPics[position % mPics.length]);
        //保持圖片長寬比例
        img.setAdjustViewBounds(true);
        //縮放為置中
        img.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //設置圖片長寬
        img.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.MATCH_PARENT, Gallery.LayoutParams.MATCH_PARENT));

        //回傳此建立的圖片
        return img;
    }
}