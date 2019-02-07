package compile_io.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import compile_io.mongo.models.Assignment;
import compile_io.mongo.repositories.AssignmentRepository;
import compile_io.storage.StorageFileNotFoundException;
import compile_io.storage.StorageService;

@RestController
@RequestMapping("/Assignment")
public class AssignmentController {
	
	@Autowired
    AssignmentRepository assignmentRepository;
	

    @GetMapping("/Assignment")
    public List<Assignment> getAllAssignments() {
        Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "createdAt");
        return assignmentRepository.findAll(sortByCreatedAtDesc);
    }
    
    @PostMapping("/Assignment")
    public Assignment createassignment(@Valid @RequestBody Assignment assignment) {
        return assignmentRepository.save(assignment);
    }
    
    //check all mappings with front end
    
    @GetMapping(value="/Assignment/{id}")
    public ResponseEntity<Assignment> getassignmentById(@PathVariable("id") String id) {
        return assignmentRepository.findById(id)
                .map(assignment -> ResponseEntity.ok().body(assignment))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value="/Assignment/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable("id") String id,
                                           @Valid @RequestBody Assignment assignment) {
    	Assignment assignmentGot = assignmentRepository.findById(id).get();
    	assignmentGot.setName(assignment.getName());
    	
    	return ResponseEntity.ok().body(assignmentGot);
//                .flatMap(assignmentData -> {
//                    assignmentData.setName(assignment.getName());
////                    assignmentData.setDueDate(assignment.getDueDate());
//                    assignmentData.setTest(assignment.getTest());
////                    assignmentData.setTries(assignment.getTries());
//                    Assignment updatedAssignment = assignmentRepository.save(assignmentData);
//                    return ResponseEntity.ok().body(updatedAssignment);
//                }).orElse(ResponseEntity.notFound().build());							
     
    }

    @DeleteMapping(value="/Assignment/{id}")
    public ResponseEntity<?> deleteAssignment(@PathVariable("id") String id) {
        return assignmentRepository.findById(id)
                .map(assignment -> {
                    assignmentRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
	
	
	
	
	

//	private StorageService storageService;
//	private String fileName;
//
//	@Autowired
//	AssignmentRepository assignmentRepo;
//
//	@Autowired
//	public AssignmentController(StorageService storageService) {
//		this.storageService = storageService;
//	}
//
////	
////	@GetMapping("/{className}")
////	public String[] getAssignments(@PathVariable String className) {
////		String[] temp = { "Hwk1", "Hwk2", "Hwk3", "Hwk4" };
////		return temp;
////	}
////	
//	@GetMapping("/Assignment")
//	public List<Assignment> getAllAssignments() {
//
//		Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "createdAt");
//		return AssignmentRepository.findAll(sortByCreatedAtDesc);
//	}
//
//	@GetMapping(value="/Assignment/{Course}/{oldAssignmentName}")
//	    public ResponseEntity<Assignment> getassignmentById(String name) {
//	        return AssignmentRepository.findByName(name)
//	                .map(assignment -> ResponseEntity.ok().body(assignment))
//	                .orElse(ResponseEntity.notFound().build());
//	    }}}
//}
//
//@PostMapping("Assignment/{AssignmentName}/addAssignment")
//	public Assignment addAssigment(@RequestBody Assignment assignment) {
//		return assignment;}
//		
//		
//		assignment.file = RequestBody.getFile("file");
//		assignment.newAssignmentName = request.getParameter("oldAssignmentName");
//		assignment.timeout = request.getParameter("timeout");
//		assignment.language = request.getParameter("language");
//		assignment.size = request.getParameter("size");
//		assignment.tries = request.getParameter("tries");
//		assignment.coursename = request.getParameter("coursename");
//		
//		
//		storageService.store(assignment.file);                
//		this.fileName = assignment.file.getName();
//		this.fileName = assignment.file.getName();
//		
//		String workingDir = System.getProperty("user.dir") + "/upload-dir/" + fileName;
//		workingDir = workingDir.substring(2);
//		System.out.println("Working Directory = " + workingDir);
//		
//		
//		
//		
//		
//		
//		Assignment newAssignment = new Assignment();
//		
//		
//		
//		
//		
//		assignmentRepo.save(newAssignment);
//
//		return true;
//	}
//	
//	@PutMapping("Assignment/{AssignmentName}/updateAssignment")
//	public boolean updateAssigment(MultipartHttpServletRequest request) {
//		MultipartFile file = request.getFile("file");
//		String oldAssignmentName = request.getParameter("oldAssignmentName");
//		String newAssignmentName = request.getParameter("newAssignmentName");
//		String timeout = request.getParameter("timeout");
//		String language = request.getParameter("language");
//		String size = request.getParameter("size");
//		String tries = request.getParameter("tries");
//		String coursename = request.getParameter("coursename");
//		
//		
//		
//		return false;
//	}
//	
//
//	@ExceptionHandler(StorageFileNotFoundException.class)
//	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
//		return ResponseEntity.notFound().build();
//	}
//	
//	public class AddAssignmentJson {
//		protected MultipartFile file;
//		protected String newAssignmentName;
//		protected String timeout; 
//		protected String language; 
//		protected String size; 
//		protected String tries; 
//		protected String coursename;
//	}
}
