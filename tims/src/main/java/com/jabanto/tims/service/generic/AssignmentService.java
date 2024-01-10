package com.jabanto.tims.service.generic;

import com.jabanto.tims.dao.models.Assignment;
import com.jabanto.tims.dao.models.Category;
import com.jabanto.tims.dao.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public List<Assignment> readAssignments(){
        return assignmentRepository.findAll();
    }

}
