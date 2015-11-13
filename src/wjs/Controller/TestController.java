package wjs.Controller;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import wjs.Config.MongoKit;

public class TestController extends Controller {
	
	public void index(){
		this.render("/index.jsp");
		}

	public void sayHello(){
		//getArrt("userName")方法取到的是HttpServletRequest中的属性
		//取参数的getPara("userName")方法
		String userName=this.getPara("userName");
		
		String str = "Hello " + userName + "，welcome to JFinal world.";
		this.setAttr("sayHello", str);
		this.render("/hello.jsp");
		} 
	
	
	
	//测试
	public void get(){
		int	pageNumber=getParaToInt(0, 1);
		int pageSize=5;
		Page<Record> result=MongoKit.paginate("user02", pageNumber, pageSize);
		setAttr("reslut",result);
		render("user.jsp");
	}
	
}
