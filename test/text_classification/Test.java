package text_classification;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
public class Test {
	public static void main(String[] args) {
		String dataPath = System.getProperty("user.dir");
		System.out.println("dataPath " + dataPath);
	}
	@org.junit.Test
	public void testJson() {
		String s = "{\"1\":\"0\",\"2\":\"0\",\"3\":\"0\",\"4\":\"0\",\"5\":\"0\"}";
		JSONObject jsonObject = JSON.parseObject(s);
		String value = jsonObject.getString("2");
		System.out.println("value " + value);
	}
	@org.junit.Test
	public void testJson2() {
		String ss = "{\"sort\":\"\",\"url\":\"http://news.youth.cn/yl/201611/t20161116_8851373.htm\",\"title\":\"超尴尬！素颜出镜的陈坤，"
				+ "竟被指撞脸宋小宝\",\"bread\":[],\"content\":\"<image_0></image_0><br>网友微"
				+ "博截图<br><image_1></image_1><br>陈坤<br>近日，有网友在微博中晒出"
				+ "一张照片，配文称:宋小宝太像陈坤了。乍一看去，照片中坐在民族风的床单上，皮肤黝黑，穿着休闲的人是宋小宝无疑"
				+ "但\",\"content_length\":233,\"title_length\":20,\"media\":\"中国青年网\","
				+ "\"summary\":\"\",\"image_string\":\"\",\"source\":\"中国青年网\",\"page_title\":\"\",\"is_force_rec\":0,\"cmsid\":\"-1\",\"token\":\"3bd1ed5f-c3ee-4252-88a3-014aed9f5f0c\", \"st\":1,\"old_title\":\"\"}";
		 JSONObject jsonObject = JSON.parseObject(ss);
		 String  source=jsonObject.getString("source");
		 String token=jsonObject.getString("token");
		 System.out.println("source "+source);
		 System.out.println("token "+token);
	}
}
