package com.sohu.mrd.classification.filterRule;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
/**
 * @author Jin Guopan
   @creation 2017年1月13日
      通过该Site黑名单过滤
 */
public class SourceRuleSingletonFilter {
	private static final Logger LOG = Logger.getLogger(SourceRuleSingletonFilter.class);
	private static List<String> blackSiteList = new ArrayList<String>();
    static{
    	InputStream  is=SourceRuleSingletonFilter.class.getClassLoader().getResourceAsStream("filter_dic/black_site.txt");
    	BufferedReader br= new BufferedReader(new InputStreamReader(is));
    	String temp="";
    	try {
			while((temp=br.readLine())!=null)
			{
				blackSiteList.add(temp.trim());
			}
		} catch (IOException e) {
			LOG.error("读取黑名单网站异常",e);
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				LOG.error("关闭读取黑名单网站异常 ",e);
			}
		}
    }
    private SourceRuleSingletonFilter(){}
    private  static class InstanceHolder
    {
    	private static final SourceRuleSingletonFilter instance= new SourceRuleSingletonFilter();
    }
    
    public static SourceRuleSingletonFilter  getInstance()
    {
    	return InstanceHolder.instance;
    }
    //进行黑名单网站过滤
    public String   filterBlackSite(String url)
    {
    	String  filterReason = null;
    	//抽取host名字
    	try {
			URL cusUrl=new URL(url);
			String host=cusUrl.getHost();
			URI  uri = cusUrl.toURI();
			String httpScheme=uri.getScheme();
			String site=httpScheme+"://"+host;
			if(blackSiteList.contains(site.trim()))
			{
				filterReason="通过黑名单网站被过滤，抽取的黑名单网站为"+site.trim()+" 原始url为 "+url;
			}
		} catch (MalformedURLException e) {
			LOG.error("抽取host名字异常 ",e);
		} catch (URISyntaxException e) {
			LOG.error("抽取uri异常 ",e);
		}
    	return filterReason;
    }
    
}
