package text_classification;
import java.util.HashMap;
import org.junit.Test;
/**
 * @author Jin Guopan
 * @creation 2017年1月19日
 */
public class RegexText {
	public static void main(String[] args) {
		String str = "价格18";
		if (str.matches(".*\\d+元.*")) {
			System.out.println("标题包含价格");
		}
	}
	@Test
	public void testPhone() {
		String filterReason = null;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("零", "0");
		map.put("一", "1");
		map.put("二", "2");
		map.put("三", "3");
		map.put("四", "4");
		map.put("五", "5");
		map.put("六", "6");
		map.put("七", "7");
		map.put("八", "8");
		map.put("九", "9");
		String content = "我是内容，测试内容 ！测试、我的内容？";
		StringBuilder ch2NumberContentSb = new StringBuilder();
		// 把文章中中文数字转换为数字
		for (int i = 0; i < content.length(); i++) {
			String charStr = content.charAt(i) + "";
			if (map.containsKey(charStr.trim())) {
				String numberChar = map.get(charStr.trim());
				ch2NumberContentSb.append(numberChar);
			} else {
				ch2NumberContentSb.append(charStr);
			}
		}
		String ch2NumberContent = ch2NumberContentSb.toString();
		String splitRegu = "[,|。|，|!|！|?|？|、]";
		String[] ss = ch2NumberContent.split(splitRegu, -1); // 把文章切分为句子
		// qq 关键字
		String qqKeywordsReg = ".*[qq|QQ|qQ|Qq|扣扣|联系|地址|号码|咨询|详情|专家|热线|咨询|预约|致电|Q群|q群].*";
		// phone 关键字
		String phonekeywordsReg = ".*[热线|传真|预约|致电|电话|联系|地址|号码|咨询|拨打|手机|固话|详情|专家|热线|咨询].*";
		// 判断是否为固话的正则
		String phone_reg1 = "0\\d{2,3}-\\d{7,8}([^\\d]|$)";
		String phone_reg2 = "\\d{7,8}[^\\d]";
		String phone_reg3 = "0\\d{2,3}-\\d{3,4}-\\d{3,4}([^\\d]|$)";
		// qq正则
		String qq_reg = ".*[1-9][0-9]{4,}.*";
		// email的正则表达式
		String email_reg = ".*(\\w)+(\\.\\w+)*[@|#](\\w)+((\\.\\w{2,3}){1,3}).*";
		// 判断是否为手机号的正则
		String mobile_reg = ".*((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}.*";
		for (int i = 0; i < ss.length; i++){
			if (ss[i].matches(mobile_reg)) // 判断是否为手机号码
			{
				filterReason = "通过手机号码被过滤，包含手机号码的句子是  " + ss[i];
				// return filterReason;
			}
			if (ss[i].matches(qqKeywordsReg)) // 包含qq关键字
			{
				if (ss[i].matches(qq_reg)) {
					filterReason = "通过qq号码被过滤，包含qq号码的句子是  " + ss[i];
				}
			}
			if (ss[i].matches(phonekeywordsReg)) // 包含固话的关键字
			{
                if(ss[i].matches(phone_reg1) || ss[i].matches(phone_reg2) || ss[i].matches(phone_reg3))
                {
                	filterReason="通过固话被过滤，包含的电话号码的句子为  "+ss[i];
                }
			}
			if(ss[i].matches(email_reg))
			{
				filterReason="通过email被过滤，包含的email的句子为   "+ss[i];
			}
		}
		// return filterReason;
	}
	@Test
	public void testRe() {
		String email_reg = ".*(\\w)+(\\.\\w+)*[@|#](\\w)+((\\.\\w{2,3}){1,3}).*";
		String email="我的 963669326 qq.com 邮箱";
		if(email.matches(email_reg))
		{
			System.out.println("匹配上");
		}else{
			System.out.println("没有匹配上");
		}
		
		
		String phone_reg1 = ".*0\\d{2,3}-\\d{7,8}([^\\d]|.*).*"; //0351-8302224
		String phone_reg2 = ".*\\d{7,8}[^\\d].*"; //8302224
		String phone_reg3 = ".*0\\d{2,3}-\\d{3,4}-\\d{3,4}([^\\d]|.*).*";//86-10-66778899 
//		String phone="我的电话 0086-10-66778899 嗯";
//		
//		if(phone.matches(phone_reg3))
//		{
//			System.out.println("匹配上");
//		}else{
//			System.out.println("没有匹配上");
//		}

		// String qq_reg=".*[1-9][0-9]{4,}.*";
		// String qq="我的 qq 909090909 号码";
		// if(qq.matches(qq_reg))
		// {
		// System.out.println("匹配上  ----");
		// }else{
		// System.out.println("没有匹配上  ----");
		// }
		// String
		// qqKeywordsReg=".*[qq|QQ|qQ|Qq|扣扣|联系|地址|号码|咨询|详情|专家|热线|咨询|预约|致电|Q群|q群].*";
		// String test="我的联系 是 ";
		// if(test.matches(qqKeywordsReg))
		// {
		// System.out.println("匹配上");
		// }else{
		// System.out.println("没有匹配上");
		// }
		// String
		// mobile_reg=".*((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}.*";
		// String s="我的 手机号码";
		// if(s.matches(mobile_reg))
		// {
		// System.out.println("匹配到了");
		// }else{
		// System.out.println("没有匹配到");
		// }

	}

}
