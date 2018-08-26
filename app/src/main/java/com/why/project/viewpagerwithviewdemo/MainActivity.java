package com.why.project.viewpagerwithviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.why.project.viewpagerwithviewdemo.bean.CheckInfoBean;
import com.why.project.viewpagerwithviewdemo.bean.ViewPagerLayoutViewsBean;
import com.why.project.viewpagerwithviewdemo.viewpager.MyCustomViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private Context mContext;

	private Button mSaveBtn;
	private TextView mPageTv;

	/**中间viewpager区域*/
	private MyCustomViewPager mViewPager;
	/**ViewPager适配器*/
	private MyViewPagerAdapter mViewPageAdapter;
	//viewpager的数据集合
	private ArrayList<CheckInfoBean> mCheckInfoLists;
	/**viewpager中当前页面的下标值*/
	private int currentItemIndex = 0;

	private ArrayList<ViewPagerLayoutViewsBean> mViewPagerLayoutViewsBeanList;//viewpager内部的View集合

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContext = this;

		//初始化控件以及设置
		initView();
		//初始化数据
		initData();
		//初始化控件的点击事件
		initEvent();
	}

	@Override
	public void onDestroy() {
		mViewPager.removeAllViews();//防止内存泄漏
		System.gc();//回收

		super.onDestroy();
	}

	private void initView() {
		mSaveBtn = findViewById(R.id.btn_save);
		mPageTv = findViewById(R.id.tv_page);

		mViewPager = (MyCustomViewPager) findViewById(R.id.view_pager);
		mViewPager.setOffscreenPageLimit(3);//设置预加载的页数，之前是3【这个值指的是，当前view的左右两边的预加载的页面的个数。也就是说，如果这个值mOffscreenPageLimit　＝　3，那么任何一个页面的左边可以预加载3个页面，右边也可以加载3页面。】
	}

	private void initData() {
		//初始化viewpager当前页的view集合
		mViewPagerLayoutViewsBeanList = new ArrayList<ViewPagerLayoutViewsBean>();

		//初始化数据
		mCheckInfoLists = new ArrayList<CheckInfoBean>();
		for (int i=0;i<10;i++){
			CheckInfoBean bean = new CheckInfoBean();
			bean.setCheckResult("1");
			bean.setCheckContent("这是第" + (i+1) + "页");
			mCheckInfoLists.add(bean);

			mViewPagerLayoutViewsBeanList.add(null);//先添加一个空值，这样才可以执行set方法
		}

		//设置页码
		if(mCheckInfoLists.size() > 0){
			showPageNum();
		}

		//填充viewpager数据
		initViewPage();
	}

	private void initEvent() {
		mSaveBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(mViewPagerLayoutViewsBeanList.get(currentItemIndex) != null) {
					String changedContent = mViewPagerLayoutViewsBeanList.get(currentItemIndex).getmContent().getText().toString();
					String changedResult = mViewPagerLayoutViewsBeanList.get(currentItemIndex).getChangedResult();
					Toast.makeText(mContext,"检查结果：" + changedResult + ";备注：" + changedContent,Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	//设置页码
	private void showPageNum() {
		mPageTv.setText((currentItemIndex+1) + "/" + mCheckInfoLists.size());
	}

	/**初始化viewpager配置*/
	private void initViewPage(){
		if(mViewPageAdapter == null){
			mViewPageAdapter = new MyViewPagerAdapter();
			mViewPager.setAdapter(mViewPageAdapter);

			mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());//设置页面切换监听事件
			mViewPager.setIsCanScroll(true);//允许滑动
		}else{
			mViewPageAdapter.notifyDataSetChanged();
		}
		mViewPager.setCurrentItem(currentItemIndex);
	}

	/**ViewPager适配器*/
	public class MyViewPagerAdapter extends PagerAdapter
	{
		/**这个方法，是从ViewGroup中移出当前View*/
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(((View)object));
		}

		/**这个方法，是获取viewpager的界面数*/
		@Override
		public int getCount() {
			return mCheckInfoLists.size();
		}


		public int getItemPosition(Object object) {
			return POSITION_NONE;//-2
		}

		/**这个方法，return一个对象，这个对象表明了PagerAdapter将选择将这个对象填充到在当前ViewPager里*/
		@Override
		public Object instantiateItem(ViewGroup container, int position){
			View layout = LayoutInflater.from(mContext).inflate(R.layout.view_pager_layout, null);

			EditText mEdtContent = layout.findViewById(R.id.edt_content);

			//实例化控件
			CheckBox mHegeCB = layout.findViewById(R.id.cb_hege);
			CheckBox mYanZhongCB = layout.findViewById(R.id.cb_yanzhong);
			CheckBox mZhuYaoCB = layout.findViewById(R.id.cb_zhuyao);
			CheckBox mYiBanCB = layout.findViewById(R.id.cb_yiban);

			//将四个CheckBox放到集合中，用于控制单选规则【下标值按照1、2、3、4与合格。严重，主要，一般的规则排列】
			final ArrayList<CheckBox> mResultRadioList = new ArrayList<CheckBox>();
			mResultRadioList.add(mHegeCB);
			mResultRadioList.add(mYanZhongCB);
			mResultRadioList.add(mZhuYaoCB);
			mResultRadioList.add(mYiBanCB);

			//填充数据
			CheckInfoBean checkInfoBean = mCheckInfoLists.get(position);
			mEdtContent.setText(checkInfoBean.getCheckContent());

			String resultIndex = checkInfoBean.getCheckResult();
			if(! TextUtils.isEmpty(resultIndex) && Integer.parseInt(resultIndex) > 0){
				mResultRadioList.get(Integer.parseInt(resultIndex) - 1).setChecked(true);
			}

			//设置点击事件
			for(int i=0;i<mResultRadioList.size();i++){
				final int ckIndex = i;
				mResultRadioList.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
						if(isChecked){
							//循环mResultRadioList集合，还原其他radio不选中状态
							for(CheckBox radioBtn : mResultRadioList){
								if(radioBtn != mResultRadioList.get(ckIndex)){
									radioBtn.setChecked(false);
								}
							}
						}
					}
				});
			}

			//将布局文件view添加到viewpager中
			container.addView((View)layout);

			ViewPagerLayoutViewsBean viewBean = new ViewPagerLayoutViewsBean();
			viewBean.setmContent(mEdtContent);
			viewBean.setmHegeCB(mHegeCB);
			viewBean.setmYanZhongCB(mYanZhongCB);
			viewBean.setmYiBanCB(mYiBanCB);
			viewBean.setmZhuYaoCB(mZhuYaoCB);
			mViewPagerLayoutViewsBeanList.set(position,viewBean);//添加到集合中，用于获取当前页的数据

			return layout;
		}

		/**这个方法，在帮助文档中原文是could be implemented as return view == object,也就是用于判断是否由对象生成界面*/
		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object ? true : false;//官方提示这样写
		}
		@Override
		public void notifyDataSetChanged()
		{
			super.notifyDataSetChanged();
		}
	}

	/**ViewPage切换的事件监听
	 * http://blog.csdn.net/zhengxiaoyao0716/article/details/48805703*/
	public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener
	{
		/* 这个方法在手指操作屏幕的时候发生变化。有三个值：0（END）,1(PRESS) , 2(UP) 。
		 * arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。
		 */
		@Override
		public void onPageScrollStateChanged(int state) {
		}

		/* 用户一次滑动，这个方法会持续调用N多次，直至某个View充满视图并且稳定住！（但具体调用次数也不确定，尤其在首末位置向边界滑动，如果Log一下，会看到出现调用不确定次数的打印，且positionOffset都为0.
		 * position 当前页面，及你点击滑动的页面【position为当前屏幕上所露出的所有View的Item取下限。比如，当前Item为3，轻轻向右滑动一下，2露出了一点点，那么position就是2；而如果向左滑动，露出的4比3大，那么只要3没完全隐匿，那么position就一直按照3算。】
		 * positionOffset 当前页面偏移的百分比【positionOffset是当前Item较大的那个View占视图的百分比，0-1，没有负数！当滑动结束时，onPageScrolled();最后一次调用，positionOffset为0。】
		 * positionOffsetPixels 当前页面偏移的像素位置
		 */
		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		}

		/* 这个方法有一个参数position，代表哪个页面被选中。
		 * 当用手指滑动翻页的时候，如果翻动成功了（滑动的距离够长），手指抬起来就会立即执行这个方法
		 * position就是当前滑动到的页面。
		 * 如果直接setCurrentItem翻页，那position就和setCurrentItem的参数一致，这种情况在onPageScrolled执行方法前就会立即执行。
		 */
		@Override
		public void onPageSelected(int position) {
			currentItemIndex = position;
			showPageNum();//设置页码
		}
	}

}
