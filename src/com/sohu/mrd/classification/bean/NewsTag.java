package com.sohu.mrd.classification.bean;
/**
 * @author Jin Guopan
   @creation 2016年12月1日
 */
public class NewsTag {
	 private String id;
	 private String nid;//即docId
	 private String oid;//新闻oid
	 private String title;
	 private String channel_id;//频道ID
	 private String content_tag;//内容标签
	 private String quality_tag;//质量标签 json "1":"旧闻","2":"谣言","3":"内容不全","4":"低俗色情","5":"内容质量差","6":"广告软文","7":"欺诈信息","8":"标题党","9":"政治反动","10":"垃圾媒体或来源","11":"排版混乱","12":"竞媒文章","13":"与大分类不相关"'
	 private String original_tag;//原始标签
	 private String  mark_flag;//1、机器未标注；2、标注未修改；3、标注已修改'
	 private String  operator;//操作人。
	 private String created;//创建时间
	 private String modified;//修改时间
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the nid
	 */
	public String getNid() {
		return nid;
	}
	/**
	 * @param nid the nid to set
	 */
	public void setNid(String nid) {
		this.nid = nid;
	}
	/**
	 * @return the oid
	 */
	public String getOid() {
		return oid;
	}
	/**
	 * @param oid the oid to set
	 */
	public void setOid(String oid) {
		this.oid = oid;
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
	 * @return the channel_id
	 */
	public String getChannel_id() {
		return channel_id;
	}
	/**
	 * @param channel_id the channel_id to set
	 */
	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}
	/**
	 * @return the content_tag
	 */
	public String getContent_tag() {
		return content_tag;
	}
	/**
	 * @param content_tag the content_tag to set
	 */
	public void setContent_tag(String content_tag) {
		this.content_tag = content_tag;
	}
	/**
	 * @return the quality_tag
	 */
	public String getQuality_tag() {
		return quality_tag;
	}
	/**
	 * @param quality_tag the quality_tag to set
	 */
	public void setQuality_tag(String quality_tag) {
		this.quality_tag = quality_tag;
	}
	/**
	 * @return the original_tag
	 */
	public String getOriginal_tag() {
		return original_tag;
	}
	/**
	 * @param original_tag the original_tag to set
	 */
	public void setOriginal_tag(String original_tag) {
		this.original_tag = original_tag;
	}
	/**
	 * @return the mark_flag
	 */
	public String getMark_flag() {
		return mark_flag;
	}
	/**
	 * @param mark_flag the mark_flag to set
	 */
	public void setMark_flag(String mark_flag) {
		this.mark_flag = mark_flag;
	}
	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}
	/**
	 * @param created the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}
	/**
	 * @return the modified
	 */
	public String getModified() {
		return modified;
	}
	/**
	 * @param modified the modified to set
	 */
	public void setModified(String modified) {
		this.modified = modified;
	}
	 
}
