package gubo.custom.autowired;

import org.springframework.beans.factory.annotation.Autowired;


public class Group {
	@Autowired
	private PersonA personA;
	
	public PersonA getPersonA() {
		return personA;
	}
	
	@Autowired
	private PersonA personA2;
	
	public PersonA getPersonA2() {
		return personA;
	}
	
	
	private PersonB personB;
	public PersonB getPersonB() {
		return personB;
	}
	@Autowired
	public void setPersonB(PersonB personB) {
		this.personB = personB;
	}
}
