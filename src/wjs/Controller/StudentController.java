package wjs.Controller;

import java.util.HashMap;
import java.util.Map;


import org.bson.types.ObjectId;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

import wjs.Config.MongoKit;
import wjs.Validator.StudentValidator;



/**
 * 学生信息管理
 * @author weijiashang
 *@date 2015年11月11日下午3:07:35
 */
public class StudentController extends Controller {
	
	/**
	 * 条件查询
	 */
	public void QueryStudent(){
		//获取参数
		String Keyword=getPara("Keyword");
		if(Keyword==null||Keyword==""){
			GetStudent();//全查询
		}else {//条件查询
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put("age", "22") ;  //精确过滤
			
			Map<String, Object> like = new HashMap<String, Object>();
			like.put("name",Keyword);  //模糊匹配，相当于sql 的 like %zhang%
			
			Map<String, Object> sort = new HashMap<String, Object>();
			sort.put("_id","desc");     //排序

			int	pageNumber=getParaToInt(0, 1);
			int pageSize=5;
			
			Page<Record> reslut = MongoKit.paginate("t_student", pageNumber, pageSize,like);
			setAttr("reslut", reslut);
			render("studentList.jsp");//返回视图
		}	
	}
	
	//获取学生信息列表
	public void GetStudent(){
		int	pageNumber=getParaToInt(0, 1);
		int pageSize=20;
		Page<Record> result=MongoKit.paginate("t_student", pageNumber, pageSize);
		setAttr("reslut", result);
		render("studentList.jsp");
	}
	
	//跳转添加页面
	public void AddToStudent(){
		render("studentAdd.jsp");
	}
	
	//添加
	@Before(StudentValidator.class)
	public void AddStudent(){
		//Student student =getModel(Student.class,"st");
		Record result =new Record();
		//result.set("_id", getPara("st._id"));
		result.set("name", getPara("st.name"));
		result.set("sxe", getPara("st.sxe"));
		result.set("age", getPara("st.age"));
		result.set("tel", getPara("st.tel"));
		result.set("address", getPara("st.address"));
		
		MongoKit.save("t_student",result);
		redirect("/student/GetStudent");
		/*if(i!=-1){
			redirect("/student/GetStudent");
		}else {
			render("添加失败");
		}*/
		
	}
	
	//获取更新的数据
	public void QueryStudentById(){
	
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("_id",new ObjectId(getPara())); 	
		Record result=MongoKit.findOne("t_student", filter);
		if(result!=null){
			setAttr("result",result);//把结果返回文本框	
			render("studentEdit.jsp");
		}
	}
	
	
	/**
	 * 更新
	 */
	@Before(StudentValidator.class)
	public void UpdateStudent(){
		//条件
		Map<String, Object> Conditons = new HashMap<String, Object>();
		Conditons.put("_id",new ObjectId(getPara("st._id")));//注意id的类型为Object，注意转换
		//获取的新数据
		Map<String, Object> NewValue = new HashMap<String, Object>();
		NewValue.put("name", getPara("st.name"));
		NewValue.put("sxe", getPara("st.sxe"));
		NewValue.put("age", getPara("st.age"));
		NewValue.put("tel", getPara("st.tel"));
		NewValue.put("address", getPara("st.address"));
		
		MongoKit.updateFirst("t_student", Conditons, NewValue);
		redirect("/student/GetStudent");
		
	}
	
	
	/**
	 * 删除 
	 */
	public void DeleteStudent(){
		String id=getPara();
		Map<String, Object> filter  = new HashMap<String,Object>();
		filter.put("_id", new ObjectId(id));
		MongoKit.remove("t_student", filter); 
		redirect("/student/GetStudent");
	}
	
}
