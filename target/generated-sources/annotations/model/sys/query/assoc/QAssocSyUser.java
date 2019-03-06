package model.sys.query.assoc;

import io.ebean.typequery.PEnum;
import io.ebean.typequery.PInstant;
import io.ebean.typequery.PInteger;
import io.ebean.typequery.PString;
import io.ebean.typequery.TQAssocBean;
import io.ebean.typequery.TQProperty;
import io.ebean.typequery.TypeQueryBean;
import model.statics.CommonState;
import model.sys.SyUser;
import model.sys.query.QSyUser;

/**
 * Association query bean for AssocSyUser.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
public class QAssocSyUser<R> extends TQAssocBean<SyUser,R> {

  public PInteger<R> id;
  public PEnum<R,CommonState> state;
  public PInstant<R> createAt;
  public PInstant<R> updateAt;
  public PString<R> loginName;
  public PString<R> passWord;
  public PString<R> realName;
  public PInteger<R> age;
  public PString<R> bornPlace;
  public PString<R> phoneNumber;
  public PInteger<R> gender;
  public PInteger<R> userType;

  /**
   * Eagerly fetch this association loading the specified properties.
   */
  @SafeVarargs
  public final R fetch(TQProperty<QSyUser>... properties) {
    return fetchProperties(properties);
  }

  /**
   * Eagerly fetch this association using a 'query join' loading the specified properties.
   */
  @SafeVarargs
  public final R fetchQuery(TQProperty<QSyUser>... properties) {
    return fetchQueryProperties(properties);
  }

  /**
   * Use lazy loading for this association loading the specified properties.
   */
  @SafeVarargs
  public final R fetchLazy(TQProperty<QSyUser>... properties) {
    return fetchLazyProperties(properties);
  }

  public QAssocSyUser(String name, R root) {
    super(name, root);
  }
}
