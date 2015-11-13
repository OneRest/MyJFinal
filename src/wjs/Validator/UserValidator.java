package wjs.Validator;


import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class UserValidator extends Validator {


  protected void validate(Controller c) {
    validateRequiredString("userName", "loginNameMsg", "用户名不能为空!");
    validateRequiredString("password", "passwordMsg", "密码不能为空！");
  }
  

  @Override // 上边验证失败实行该方法
  protected void handleError(Controller c) {
    getActionKey();
    c.keepPara("userName");
    c.keepPara("password");

    c.render("login.jsp");

  }

}
