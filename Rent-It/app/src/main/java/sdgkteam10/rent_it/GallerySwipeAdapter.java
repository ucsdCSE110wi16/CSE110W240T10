package sdgkteam10.rent_it;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;


/**
 * Created by TheShire on 3/5/16.
 */
public class GallerySwipeAdapter extends PagerAdapter {

    private Context ctx;
    private LayoutInflater layoutInflater;
    private final ArrayList<Bitmap> bitmapArray = new ArrayList<>();

    public GallerySwipeAdapter(Context ctx)
    {
        this.ctx = ctx;
        loadImages();
    }

    //gets item object passed in from search fragment
    GlobalItem gItem = GlobalItem.getInstance();
    Item item = gItem.getItem();


    private void loadImages()
    {
        for(int i = 0; i < item.getImageArray().length; i++) {

            byte[] imageAsBytes = Base64.decode(item.getImageArray()[i], Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

            bitmapArray.add(bmp);
        }
    }

    @Override
    public int getCount() {
        return item.getImageArray().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {


        return view==((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout,container,false);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.image_view);
        imageView.setImageBitmap(bitmapArray.get(position));
        container.addView(item_view);


        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);

    }

}
