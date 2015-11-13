package wjs.Controller;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

import wjs.Config.MongoKit;
import wjs.Validator.UserValidator;

/**
 * 用户
 * @author weijiashang
 *@date 2015年11月11日下午4:36:30
 */
public class UserController extends Controller {

	//用户登录
	@Before(UserValidator.class)
	public void Login(){
		
		String username=getPara("userName");
		String paw=getPara("password");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("username", username) ; 
		filter.put("password", paw) ;
		
		Record result= MongoKit.findOne("t_user", filter);
		if(result!=null){
			render("index.jsp");
		}else{
			renderText("密码或用户名错误");
		}
		
	}
}
