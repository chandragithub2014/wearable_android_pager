package com.andr.wearable_grid_pager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.wearable.view.DotsPageIndicator;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.support.wearable.view.GridViewPager;
import android.widget.TextView;

import com.andr.columnrowmap.ColumnRowMapper;
import com.andr.fragments.dummy.BasketTableFragment;
import com.andr.fragments.dummy.ListScreenFragment;
import com.andr.fragments.dummy.OrderListFragment;

import java.util.HashMap;
import java.util.Map;

//http://www.learnandroidwear.com/gridviewpager-cardfragment/
public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    GridPagerAdapter gridPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DotsPageIndicator mPageIndicator;
        final GridViewPager mViewPager;

        final String[][] data = {
                { "Row 0, Col 0", "Row 0, Col 1", "Row 0, Col 2" ,}/*,
                { "Row 1, Col 0", "Row 1, Col 1", "Row 1, Col 2" },
                { "Row 2, Col 0", "Row 2, Col 1", "Row 2, Col 2" }*/
        };

        // Get UI references
        mPageIndicator = (DotsPageIndicator) findViewById(R.id.page_indicator);
        mViewPager = (GridViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageCount(0);
        // Assigns an adapter to provide the content for this pager
         gridPagerAdapter = new GridPagerAdapter(getFragmentManager(), data);
        mViewPager.setAdapter(gridPagerAdapter);
        mPageIndicator.setPager(mViewPager);
       mViewPager.setOnPageChangeListener(new GridViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, int i1, float v, float v1, int i2, int i3) {

            }

            @Override
            public void onPageSelected(int i, int i1) {
                if(gridPagerAdapter!=null) {
                  Fragment f =   gridPagerAdapter.getFragment(i, i1);
                    if(f instanceof ListScreenFragment){
                       ((ListScreenFragment) f).refreshFragment(MainActivity.this);
                    } else if(f instanceof  BasketTableFragment){
                        ((BasketTableFragment) f).traverseBasketMap(MainActivity.this);
                    } else if(f instanceof  OrderListFragment){
                        ((OrderListFragment) f).populateOrderList(MainActivity.this);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    private static final class GridPagerAdapter extends FragmentGridPagerAdapter {
        private Map<ColumnRowMapper, Fragment> mPageReferenceMap = new HashMap<ColumnRowMapper, Fragment>();
        String[][] mData;

        private GridPagerAdapter(FragmentManager fm, String[][] data) {
            super(fm);
            mData = data;
        }

        @Override
        public Fragment getFragment(int row, int column) {
            if(row==0 && column==0){
             return new ListScreenFragment();
            }else if(row==0 && column==1){
                BasketTableFragment basketTableFragment = new BasketTableFragment();
                return  basketTableFragment;
              /*  ListScreenFragment listScreenFragment = new ListScreenFragment();
                 ColumnRowMapper columnRowMapper  = new ColumnRowMapper();
                columnRowMapper.setRow(row);
                columnRowMapper.setColumn(column);
                mPageReferenceMap.put(columnRowMapper,listScreenFragment);
                return listScreenFragment;// ItemFragment.newInstance("HI","HI");*/
            } else  {
                return new OrderListFragment();
            }
        }

        @Override
        public int getRowCount() {
            return mData.length;
        }

        @Override
        public int getColumnCount(int row) {
            return mData[row].length;
        }

        public Fragment getFragment(ColumnRowMapper key) {

            return mPageReferenceMap.get(key);
        }


    }


   /* private static final class FragmentPagerAdapter extends FragmentStatePagerAdapter{
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
        private FragmentPagerAdapter(FragmentManager fm){
            super(fm);
        }
    }*/
    //http://stackoverflow.com/questions/13379194/how-to-add-a-fragment-inside-a-viewpager-using-nested-fragment-android-4-2
    //http://www.truiton.com/2013/05/android-fragmentstatepageradapter-example/
}
