package wjs.Validator;



import com.jfinal.core.Controller;
import com.jfinal.kit.StringKit;
import com.jfinal.validate.Validator;

import wjs.Model.Student;

/**
 * 验证学生信息添加
 * @author weijiashang
 *@date 2015年11月11日下午5:02:21
 */
public class StudentValidator extends Validator {


	@Override
	protected void validate(Controller c) {
		validateRequiredString("st.name", "studentNameMgs", "姓名不能为空！");
		validateRequiredString("st.sxe", "sexMgs", "性别不能为空！");
		validateRequiredString("st.age", "ageMgs", "年龄不能为空！");
		validateRequiredString("st.tel", "phoneMgs", "电话不能为空！");
		validateRequiredString("st.address", "addressMgs", "地址不能为空！");

	}

	//错误是的处理方法
	@Override
	protected void handleError(Controller c) {
		getActionKey();
		c.keepModel(Student.class);
		//保持当前的页面
		c.render("studentAdd.jsp");
		
	}

}
