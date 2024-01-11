package com.jabanto.tims.service.generic;

import com.jabanto.tims.dao.models.Assignment;
import com.jabanto.tims.dao.models.Category;
import com.jabanto.tims.dao.models.Item;
import com.jabanto.tims.dao.repositories.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public static final int ASSIGNMENT_SUCCESS = 1;
    public static final int CHECK_IN_SUCCESS = 1;
    public static final int DATABASE_ERROR = 2;

    public final static String ASSIGNMENT_CHECKUP_ERROR = "Error by check-out.";
    public final static String ASSIGNMENT_CHECKIN_SUCCESS = "Assignment Check-in Success.";
    public final static String ASSIGNMENT_CREATED = "Assignment success Item.";
    public final static String ASSIGMENT_DATABASE_ERROR = "Error creating Assignment.";

    public List<Assignment> readAssignments(){
        return assignmentRepository.findAll();
    }

    public int checkOutItem(Assignment checkOutAssignment) {

        try{
            assignmentRepository.save(checkOutAssignment);
            return ASSIGNMENT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return DATABASE_ERROR;
        }
    }

    public Assignment getAssignmentById(int checkInAssigmentID) {
        Assignment assignment = assignmentRepository.findById(checkInAssigmentID).get();
        return assignment;
    }

    public int checkInItem(Assignment checkInAssigment) {
        try{
            assignmentRepository.save(checkInAssigment);
            return CHECK_IN_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return DATABASE_ERROR;
        }
    }
}
