package model.sys.query;

import io.ebean.EbeanServer;
import io.ebean.typequery.PEnum;
import io.ebean.typequery.PInstant;
import io.ebean.typequery.PInteger;
import io.ebean.typequery.PString;
import io.ebean.typequery.TQRootBean;
import io.ebean.typequery.TypeQueryBean;
import model.statics.CommonState;
import model.sys.SyUser;

/**
 * Query bean for SyUser.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
public class QSyUser extends TQRootBean<SyUser,QSyUser> {

  private static final QSyUser _alias = new QSyUser(true);

  /**
   * Return the shared 'Alias' instance used to provide properties to 
   * <code>select()</code> and <code>fetch()</code> 
   */
  public static QSyUser alias() {
    return _alias;
  }

  public PInteger<QSyUser> id;
  public PEnum<QSyUser,CommonState> state;
  public PInstant<QSyUser> createAt;
  public PInstant<QSyUser> updateAt;
  public PString<QSyUser> loginName;
  public PString<QSyUser> passWord;
  public PString<QSyUser> realName;
  public PInteger<QSyUser> age;
  public PString<QSyUser> bornPlace;
  public PString<QSyUser> phoneNumber;
  public PInteger<QSyUser> gender;
  public PInteger<QSyUser> userType;


  /**
   * Construct with a given EbeanServer.
   */
  public QSyUser(EbeanServer server) {
    super(SyUser.class, server);
  }

  /**
   * Construct using the default EbeanServer.
   */
  public QSyUser() {
    super(SyUser.class);
  }

  /**
   * Construct for Alias.
   */
  private QSyUser(boolean dummy) {
    super(dummy);
  }

  /**
   * Provides static properties to use in <em> select() and fetch() </em>
   * clauses of a query. Typically referenced via static imports. 
   */
  public static class Alias {
    public static PInteger<QSyUser> id = _alias.id;
    public static PEnum<QSyUser,CommonState> state = _alias.state;
    public static PInstant<QSyUser> createAt = _alias.createAt;
    public static PInstant<QSyUser> updateAt = _alias.updateAt;
    public static PString<QSyUser> loginName = _alias.loginName;
    public static PString<QSyUser> passWord = _alias.passWord;
    public static PString<QSyUser> realName = _alias.realName;
    public static PInteger<QSyUser> age = _alias.age;
    public static PString<QSyUser> bornPlace = _alias.bornPlace;
    public static PString<QSyUser> phoneNumber = _alias.phoneNumber;
    public static PInteger<QSyUser> gender = _alias.gender;
    public static PInteger<QSyUser> userType = _alias.userType;
  }
}
