package model.base;

import io.ebean.Ebean;
import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import model.statics.CommonState;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.Instant;


/**
 * model 基类，提供公用字段: id、state、create_at、update_at
 */
@MappedSuperclass
public abstract class IdModel extends Model {
    public static <T> T findOrNewModel(Class<T> clazz, Object id) {
        T model;
        if(id == null) {
            try {
                model = clazz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(clazz.getSimpleName() + " new instance fail");
            }
        } else {
            model = Ebean.find(clazz, id);
            if(model == null) {
                throw new RuntimeException(clazz.getSimpleName() + " not found: " + id);
            }
        }
        return model;
    }

    @Id
    Integer id;


    CommonState state;

    @WhenCreated
    @Column(name = "create_at")
    Instant createAt;

    @WhenModified
    @Column(name = "update_at")
    Instant  updateAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CommonState getState() {
        return state;
    }

    public void setState(CommonState state) {
        this.state = state;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Instant createAt) {
        this.createAt = createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }
}