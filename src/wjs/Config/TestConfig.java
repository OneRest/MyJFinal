package wjs.Config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.render.ViewType;

import wjs.Controller.StudentController;
import wjs.Controller.TestController;
import wjs.Controller.UserController;

/**
 * 配置类
 * @author weijiashang
 *@date 2015年11月11日下午3:31:47
 */
public class TestConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {

		me.setDevMode(true);
		me.setViewType(ViewType.JSP);
		me.setEncoding("utf-8"); 
		
	}

	@Override
	public void configRoute(Routes me) {
		//第一个参数"/"就是指的根。当用户访问根URL时，依据约定JFinal会将其路由到TestController.index()方法。
		//me.add("/", TestController.class);
		me.add("/student", StudentController.class);
		me.add("/", UserController.class);
		
	}

	@Override
	public void configPlugin(Plugins me) {
		//mongodb数据库连接池的配置
		MongodbPlugin mp =new MongodbPlugin("ges9");//数据库名字
		me.add(mp);
		
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configHandler(Handlers me) {
		//配置了一个ContextPathHandler，构造参数是"basePath"
		me.add(new ContextPathHandler("basePath")); 
		
	}

}
