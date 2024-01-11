package com.jabanto.tims.service.generic;

import com.jabanto.tims.configuration.StageInitializer;
import com.jabanto.tims.dao.models.Category;
import com.jabanto.tims.dao.models.Status;
import com.jabanto.tims.dao.models.UserGroup;
import com.jabanto.tims.dao.repositories.ItemStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ItemStatusService {

    private static final int UPDATE_SUCCESS = 1;
    private static final int STATUS_DATABASE_ERROR = 2;
    @Autowired
    ItemStatusRepository itemStatusRepository;

    public List<Status> readAllItemStatus(){ return itemStatusRepository.findAll();}

    public List<String> getStatusNames() {
        List<Status> roleList =  readAllItemStatus();
        List<String> statusNames = roleList.stream()
                .map(Status::getName)
                .collect(Collectors.toList());
        return statusNames;
    }

    public Optional<Status> getStatus(String statusName) {
        return itemStatusRepository.findByName(statusName);
    }

    public int saveStatus(Status newStatus) {
        try {
            itemStatusRepository.save(newStatus);
            return UPDATE_SUCCESS; 
        }catch (Exception e){
            e.printStackTrace();
            return STATUS_DATABASE_ERROR;
        }
    }
}
