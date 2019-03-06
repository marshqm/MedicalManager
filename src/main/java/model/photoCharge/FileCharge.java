package model.photoCharge;

import io.ebean.ExpressionList;
import io.ebean.Finder;
import model.base.IdModel;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "file_charge")
public class FileCharge extends IdModel{
    public static Finder<Integer, FileCharge> finder = new Finder<>(FileCharge.class);

    public static ExpressionList<FileCharge> findBy(FileCharge vm) {
        ExpressionList<FileCharge> el = FileCharge.finder.query().where().raw("1=1");
        if (StringUtils.isNotBlank(vm.getFileOwner())){
            el.eq("fileOwner",vm.getFileOwner());
        }
        return el;
    }

    String fileName;
    String fileSaveName;
    String fileOwner;

    public String getFileOwner() {
        return fileOwner;
    }

    public void setFileOwner(String fileOwner) {
        this.fileOwner = fileOwner;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSaveName() {
        return fileSaveName;
    }

    public void setFileSaveName(String fileSaveName) {
        this.fileSaveName = fileSaveName;
    }


}
