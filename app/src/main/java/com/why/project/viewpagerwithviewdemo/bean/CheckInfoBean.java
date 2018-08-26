package com.why.project.viewpagerwithviewdemo.bean;

/**
 * Created by HaiyuKing
 * Used 检查结果bean类
 */

public class CheckInfoBean {
	/**检查结果【检查结果：1、2、3、4与合格。严重，主要，一般。-1代表未检查】*/
	private String checkResult;
	/**检查说明*/
	private String checkContent;

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	public String getCheckContent() {
		return checkContent;
	}

	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}
}
