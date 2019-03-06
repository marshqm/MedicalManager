package model.sys;


import io.ebean.ExpressionList;
import io.ebean.Finder;
import model.base.IdModel;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="SY_USERS")
public class SyUser extends IdModel {
    public static Finder<Integer, SyUser> finder = new Finder<>(SyUser.class);

    public static ExpressionList<SyUser> findBy(SyUser vm) {
        ExpressionList<SyUser> el = SyUser.finder.query().where().raw("1=1");

        if (StringUtils.isNotBlank(vm.getLoginName())){
            el.eq("loginName", vm.getLoginName());
        }
        if (StringUtils.isNotBlank(vm.getRealName())){
            el.eq("realName", vm.getRealName());
        }
        if (vm.getUserType() != null && vm.getUserType() > 0){
            el.eq("userType", vm.getUserType());
        }
       // el.ne("state", CommonState.ACTIVE);
        return el;
    }

    String loginName;
    String passWord;
    String realName;
    Integer age;
    String bornPlace;
    String phoneNumber;
    Integer gender;
    Integer userType;

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return passWord;
    }

    public void setPassword(String password) {
        this.passWord = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBornPlace() {
        return bornPlace;
    }

    public void setBornPlace(String bornPlace) {
        this.bornPlace = bornPlace;
    }
}
