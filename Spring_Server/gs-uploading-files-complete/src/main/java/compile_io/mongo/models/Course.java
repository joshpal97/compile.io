package compile_io.mongo.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import compile_io.mongo.repositories.ProfessorRepository;

@Document(collection="Course")
public class Course {
	@Id
	private String id;
	private String courseName; 
	private List<String> professorUsernames;
	@DBRef
	private List<Section> sections;
	private String description;
	
	@Autowired 
	public ProfessorRepository professorRepository;
	
	public Course() {
		super();
		this.professorUsernames = new ArrayList<String>();
		this.sections = new ArrayList<Section>();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}


	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public void addProfessorUsername(String newProfessorUsername) {
		this.professorUsernames.add(newProfessorUsername);
		Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "createdAt");
		List<Professor> prof = this.professorRepository.findByuserName(newProfessorUsername, sortByCreatedAtDesc);
		if(!prof.isEmpty()) {
			prof.get(0).addCourse(this);
			this.professorRepository.save(prof.get(0));
		}
	}
	
	public void deleteProfessorUsername (String newProfessorUsername) {
		for(int i = 0; i < this.professorUsernames.size(); i++) {
			String professorUsername = this.professorUsernames.get(i);
			if(newProfessorUsername == professorUsername) {
				this.professorUsernames.remove(professorUsername);
				i--;
				Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "createdAt");
				List<Professor> prof = this.professorRepository.findByuserName(newProfessorUsername, sortByCreatedAtDesc);
				if(!prof.isEmpty()) {
					//might have to use id's
					prof.get(0).deleteCourse(this);
					this.professorRepository.save(prof.get(0));
				}
				
			}
		}
	}
	
	public List<String> getProfessors() {
		return professorUsernames;
	}

	public void setProfessors(List<String> professorUsernames) {
		this.professorUsernames = professorUsernames;
	}
	
	public void addSection(Section newSection) {
		this.sections.add(newSection);
	}
	
	public void deleteSection (Section newSection) {
		for(int i = 0; i < this.sections.size(); i++) {
			Section section = this.sections.get(i);
			if(newSection == section) {
				this.sections.remove(section);
				i--;
			}
		}
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", courseName=" + courseName + ", professorUsernames=" + professorUsernames + ", sections="
				+ sections + ", description=" + description + "]";
	}

	

	
	
	
	
	
	
	
	
}
