package compile_io.mongo.models;

import java.sql.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection="Assignment")
public class Assignment {
	
	@Id
	private String id;
	
	private String name;
	
	@DBRef
	private Test test;
	
	@DBRef
	private List<Code> codes;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date dueDate;
	
	public Assignment(Date dueDate) {
		super();
		this.dueDate = dueDate;
	}
	
	public Assignment() {
		super();
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name=name;
	}

//	public List<Code> getCodes() {
//		return codes;
//	}
//
//	public void setCodes(List<Code> codes) {
//		this.codes = codes;
//	}

	@Override
	public String toString() {
		return "Assignment [dueDate=" + dueDate + "]";
	}

//	public Integer getTries() {
//		// TODO Auto-generated method stub
//		return this.tries;
//	}
//	
	
	
	
}
