package com.why.project.viewpagerwithviewdemo.bean;

import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by HaiyuKing
 * Used
 */

public class ViewPagerLayoutViewsBean {
	private CheckBox mHegeCB;//【检查结果：1、2、3、4与合格。严重，主要，一般。-1代表未检查】
	private CheckBox mYanZhongCB;
	private CheckBox mZhuYaoCB;
	private CheckBox mYiBanCB;

	private EditText mContent;

	public CheckBox getmHegeCB() {
		return mHegeCB;
	}
	public void setmHegeCB(CheckBox mHegeCB) {
		this.mHegeCB = mHegeCB;
	}
	public CheckBox getmYanZhongCB() {
		return mYanZhongCB;
	}
	public void setmYanZhongCB(CheckBox mYanZhongCB) {
		this.mYanZhongCB = mYanZhongCB;
	}
	public CheckBox getmZhuYaoCB() {
		return mZhuYaoCB;
	}
	public void setmZhuYaoCB(CheckBox mZhuYaoCB) {
		this.mZhuYaoCB = mZhuYaoCB;
	}
	public CheckBox getmYiBanCB() {
		return mYiBanCB;
	}
	public void setmYiBanCB(CheckBox mYiBanCB) {
		this.mYiBanCB = mYiBanCB;
	}
	public EditText getmContent() {
		return mContent;
	}
	public void setmContent(EditText mContent) {
		this.mContent = mContent;
	}

	/**自定义获取检查结果数值
	 * 【检查结果：1、2、3、4与合格。严重，主要，一般。-1代表未检查】*/
	public String getChangedResult(){
		String changedResult = "-1";
		if(mHegeCB.isChecked()){
			changedResult = "1";
		}else if(mYanZhongCB.isChecked()){
			changedResult = "2";
		}
		else if(mZhuYaoCB.isChecked()){
			changedResult = "3";
		}
		else if(mYiBanCB.isChecked()){
			changedResult = "4";
		}
		return changedResult;
	}
}
