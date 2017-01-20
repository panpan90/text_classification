package com.sohu.mrd.classification.bean;

import java.util.List;

/**
 * @author Jin Guopan
   @creation 2017年1月16日
 */
public class News {
	private  String url;
	private  String title;
	private  String content;
	private  String sort;
	private  String imageCount;//图片个数
	private  String media;//媒体类型
	private  List<String> keyWords;
	
	/**
	 * @return the imageCount
	 */
	public String getImageCount() {
		return imageCount;
	}
	/**
	 * @param imageCount the imageCount to set
	 */
	public void setImageCount(String imageCount) {
		this.imageCount = imageCount;
	}
	/**
	 * @return the media
	 */
	public String getMedia() {
		return media;
	}
	/**
	 * @param media the media to set
	 */
	public void setMedia(String media) {
		this.media = media;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the keyWords
	 */
	public List<String> getKeyWords() {
		return keyWords;
	}
	/**
	 * @param keyWords the keyWords to set
	 */
	public void setKeyWords(List<String> keyWords) {
		this.keyWords = keyWords;
	}
	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}
	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	} 
	
	
	
}
