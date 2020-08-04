package com.example.vehiclelicense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class HomePageSliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public String[] titles = {"Efficient", "Adequate", "Effective"};
    public String[] description = {"Providing efficient registration of vehicles within the country.", "We are adequate in all ways, try us today and be glad",
            "We are effective in all ways, try us today and be glad"};
    public int[] bg = {R.drawable.hp_image3,R.drawable.hp_image2,R.drawable.hp_image1};

    public HomePageSliderAdapter(Context context) {
        this.context = context;


    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override

    public Object instantiateItem(ViewGroup viewGroup, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.hp_slide_layout, viewGroup, false);

        TextView slideTitle = (TextView) v.findViewById(R.id.slide_title);
        TextView slideDescription = (TextView) v.findViewById(R.id.slide_description);
        ConstraintLayout constraintLayout = (ConstraintLayout) v.findViewById(R.id.slide_constraint_layout);

        slideTitle.setText(titles[position]);
        slideDescription.setText(description[position]);
        constraintLayout.setBackgroundResource(bg[position]);
        constraintLayout.setAlpha(0.7f);

        viewGroup.addView(v);
        return v;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
