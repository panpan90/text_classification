package text_classification;

import com.sohu.mrd.classification.utils.KillPuctuation;
import com.sohu.mrd.classification.utils.KillTag;

/**
 * @author Jin Guopan
   @creation 2017年1月19日
 */
public class TestBiaodian {
	public static void main(String[] args) {
		String s="我是，测试的！的 ？ 标点。句子；嗯。。。";
		String ss=KillPuctuation.killPuctuation(KillTag.killTags(s));
		System.out.println(ss);
	}
}
