package model.photoCharge;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileChargeService {

    public List<FileCharge> findBy(FileCharge model){
        return FileCharge.findBy(model).findList();
    }

    public FileCharge save(FileCharge vm) {
        FileCharge model = FileCharge.findOrNewModel(FileCharge.class,vm.getId());
        model.setFileName(vm.getFileName());
        model.setFileSaveName(vm.getFileSaveName());
        model.setFileOwner(vm.getFileOwner());

        model.save();
        return model;
    }

}
