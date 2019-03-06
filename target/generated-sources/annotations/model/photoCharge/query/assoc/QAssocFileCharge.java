package model.photoCharge.query.assoc;

import io.ebean.typequery.PEnum;
import io.ebean.typequery.PInstant;
import io.ebean.typequery.PInteger;
import io.ebean.typequery.PString;
import io.ebean.typequery.TQAssocBean;
import io.ebean.typequery.TQProperty;
import io.ebean.typequery.TypeQueryBean;
import model.photoCharge.FileCharge;
import model.photoCharge.query.QFileCharge;
import model.statics.CommonState;

/**
 * Association query bean for AssocFileCharge.
 * 
 * THIS IS A GENERATED OBJECT, DO NOT MODIFY THIS CLASS.
 */
@TypeQueryBean
public class QAssocFileCharge<R> extends TQAssocBean<FileCharge,R> {

  public PInteger<R> id;
  public PEnum<R,CommonState> state;
  public PInstant<R> createAt;
  public PInstant<R> updateAt;
  public PString<R> fileName;
  public PString<R> fileSaveName;
  public PString<R> fileOwner;

  /**
   * Eagerly fetch this association loading the specified properties.
   */
  @SafeVarargs
  public final R fetch(TQProperty<QFileCharge>... properties) {
    return fetchProperties(properties);
  }

  /**
   * Eagerly fetch this association using a 'query join' loading the specified properties.
   */
  @SafeVarargs
  public final R fetchQuery(TQProperty<QFileCharge>... properties) {
    return fetchQueryProperties(properties);
  }

  /**
   * Use lazy loading for this association loading the specified properties.
   */
  @SafeVarargs
  public final R fetchLazy(TQProperty<QFileCharge>... properties) {
    return fetchLazyProperties(properties);
  }

  public QAssocFileCharge(String name, R root) {
    super(name, root);
  }
}
