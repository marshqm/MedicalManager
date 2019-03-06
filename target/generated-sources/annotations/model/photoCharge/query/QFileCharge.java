package model.photoCharge.query;

import io.ebean.EbeanServer;
import io.ebean.typequery.PEnum;
import io.ebean.typequery.PInstant;
import io.ebean.typequery.PInteger;
import io.ebean.typequery.PString;
import io.ebean.typequery.TQRootBean;
import io.ebean.typequery.TypeQueryBean;
import model.photoCharge.FileCharge;
import model.statics.CommonState;

/**
 * Query bean for FileCharge.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
public class QFileCharge extends TQRootBean<FileCharge,QFileCharge> {

  private static final QFileCharge _alias = new QFileCharge(true);

  /**
   * Return the shared 'Alias' instance used to provide properties to 
   * <code>select()</code> and <code>fetch()</code> 
   */
  public static QFileCharge alias() {
    return _alias;
  }

  public PInteger<QFileCharge> id;
  public PEnum<QFileCharge,CommonState> state;
  public PInstant<QFileCharge> createAt;
  public PInstant<QFileCharge> updateAt;
  public PString<QFileCharge> fileName;
  public PString<QFileCharge> fileSaveName;
  public PString<QFileCharge> fileOwner;


  /**
   * Construct with a given EbeanServer.
   */
  public QFileCharge(EbeanServer server) {
    super(FileCharge.class, server);
  }

  /**
   * Construct using the default EbeanServer.
   */
  public QFileCharge() {
    super(FileCharge.class);
  }

  /**
   * Construct for Alias.
   */
  private QFileCharge(boolean dummy) {
    super(dummy);
  }

  /**
   * Provides static properties to use in <em> select() and fetch() </em>
   * clauses of a query. Typically referenced via static imports. 
   */
  public static class Alias {
    public static PInteger<QFileCharge> id = _alias.id;
    public static PEnum<QFileCharge,CommonState> state = _alias.state;
    public static PInstant<QFileCharge> createAt = _alias.createAt;
    public static PInstant<QFileCharge> updateAt = _alias.updateAt;
    public static PString<QFileCharge> fileName = _alias.fileName;
    public static PString<QFileCharge> fileSaveName = _alias.fileSaveName;
    public static PString<QFileCharge> fileOwner = _alias.fileOwner;
  }
}
