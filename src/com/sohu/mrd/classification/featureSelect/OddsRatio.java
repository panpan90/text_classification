package com.sohu.mrd.classification.featureSelect;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.sohu.mrd.classification.utils.FileKit;
import com.sohu.mrd.classification.utils.MapKit;
import com.sohu.mrd.classification.utils.MathKit;
/**
 * @author Jin Guopan
 * @creation 2016年11月30日 使用优势率进行特征选择
 *           log(p(t/pos)(1-p(t/neg)))/(p(t/neg)(p(1-p(t/pos))))
 */
public class OddsRatio {
	public static final Logger LOG = Logger.getLogger(OddsRatio.class);
	public static final String  FEATURE_SELECT_PATH="data/online_ads/feature_select";
	public static HashMap<String, Integer> sexy_count = new HashMap<String, Integer>();
	public static HashMap<String, Integer> ordernary_count = new HashMap<String, Integer>();
	public static HashMap<String,Double>   odds_map = new HashMap<String,Double>();
	public static int  sexy_word_count=0;
	public static int  ordernary_word_count=0;
	static {
		try {
			FileInputStream sexy_fis = new FileInputStream(
					"data/online_ads/ads_count");
			FileInputStream ordernary_fis = new FileInputStream(
					"data/online_ads/ordernary_count");
			BufferedReader sex_br = new BufferedReader(new InputStreamReader(
					sexy_fis));
			String sexy_temp = "";
			while ((sexy_temp = sex_br.readLine()) != null) {
				String[]  ss=sexy_temp.split("\t", -1);
				String word=ss[0];
				int  time=Integer.valueOf(ss[1]);
				sexy_word_count+=time;
				sexy_count.put(word, time);
			}
			BufferedReader ordernary_br = new BufferedReader(new InputStreamReader(
					ordernary_fis));
			String ordernary_temp = "";
			while ((ordernary_temp = ordernary_br.readLine()) != null) {
				String[]  ss=ordernary_temp.split("\t", -1);
				String word=ss[0];
				int  time=Integer.valueOf(ss[1]);
				ordernary_word_count+=time;
				ordernary_count.put(word, time);
			}
			LOG.info("sexy_word_count"+sexy_word_count);
			LOG.info("ordernary_word_count "+ordernary_word_count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Set<String>  set= sexy_count.keySet();
		Iterator<String> it=set.iterator();
		while(it.hasNext())
		{
			String word=it.next();
			if(odds_map.get(word)==null)
			{
				double word_pos_raio=(double)sexy_count.get(word)/sexy_word_count;
				Integer ordernary_time=ordernary_count.get(word);
				if(ordernary_time==null)
				{
					ordernary_time=0;
				}
				double word_neg_raio=(double)ordernary_time/ordernary_word_count;
				double fenZi=word_pos_raio*(1-word_neg_raio);
				double fenMu=word_neg_raio*(1-word_pos_raio);
				double value=fenZi/fenMu;
				Double oddsRatio;
				if(fenMu == 0) //如果该值在负样本没有出现，给 oddsRatio赋予最大的值
				{
					oddsRatio=Double.MAX_VALUE;
				}else{
					oddsRatio=MathKit.getLog(10, value+0.1); // + 0.1 是为了平滑，防止value =0 出现
				}
//				if(oddsRatio.isInfinite())
//				{
//					LOG.info("fenZi "+fenZi);
//					LOG.info("fenMu "+fenMu);
//					LOG.info("word_pos_raio "+word_pos_raio);
//					LOG.info("word_neg_raio "+word_neg_raio);
//				}
				odds_map.put(word, oddsRatio);
			}
		}
		Set<String> ordernary_set=ordernary_count.keySet();
		Iterator<String>   ordernary_it=ordernary_set.iterator();
		while(ordernary_it.hasNext())
		{
			String word=ordernary_it.next();
			if(odds_map.get(word)==null)
			{
				Integer ordernary_time=ordernary_count.get(word);
				Integer sexy_time=sexy_count.get(word);
				if(sexy_time==null)
				{
					sexy_time=0;
				}
				double word_pos_raio=(double)sexy_time/sexy_word_count;
				double word_neg_raio=(double)ordernary_time/ordernary_word_count;
				double fenZi=word_pos_raio*(1-word_neg_raio);
				double fenMu=word_neg_raio*(1-word_pos_raio);
				double value=fenZi/fenMu;
				double oddsRatio=0;
				oddsRatio=MathKit.getLog(10, value+0.1); // +0.1 为了进行平滑
				odds_map.put(word, oddsRatio);
			}
		}
		Map<String, Double> sortOddsMap = MapKit.sortMapByDoubleValue(odds_map);
		Set<String> odds_set=sortOddsMap.keySet();
		Iterator<String> odds_it=odds_set.iterator();
		int count=0;
		
		while(odds_it.hasNext() &&  count < 1000)
		{
			String word=odds_it.next();
			double radio=sortOddsMap.get(word);
			if(radio>0)
			{
				FileKit.write2File(word, FEATURE_SELECT_PATH);
			    count++;
			}
		}
		
	}
}
