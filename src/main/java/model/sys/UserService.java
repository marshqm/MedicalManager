package model.sys;


import io.ebean.annotation.Transactional;
import model.base.IdModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    public SyUser findOneBy(Integer id) {
        return SyUser.finder.byId(id);
    }

    public void deleteBy(Integer id) {
        SyUser.finder.byId(id).delete();
    }

    @Transactional
    public SyUser saveUsers(SyUser user) {
        SyUser model = IdModel.findOrNewModel(SyUser.class,user.getId());

        if (StringUtils.isNotBlank(user.getLoginName())) {
            model.setLoginName(user.getLoginName());
        }
        if (StringUtils.isNotBlank(user.getPassword())) {
            model.setPassword(user.getPassword());
        }else if(StringUtils.isBlank(String.valueOf(user.getId()))) {
            model.setPassword("123456");
        }
        model.setAge(user.getAge());
        model.setBornPlace(user.getBornPlace());
        model.setRealName(user.getRealName());
        model.setGender(user.getGender());
        if (StringUtils.isNotBlank(user.getPhoneNumber())) {
            model.setPhoneNumber(user.getPhoneNumber());
        }
        model.setUserType(user.getUserType());

        model.save();
        return model;
    }

    public SyUser findOneByName(String loginName) {
        SyUser user = new SyUser();
        user.setLoginName(loginName);
        return SyUser.findBy(user).findOne();
    }



}
