package gubo.custom.autowired;

import org.springframework.beans.factory.annotation.Autowired;

public class Group2 {

	private PersonA personA;
	private PersonB personB;
	
	
	private PersonB personB2;
	
	@Autowired
	private PersonB personB3;
	
	public PersonB getPersonB3() {
		return personB3;
	}


	@Autowired
	public void setPersonB2(PersonB personB2) {
		this.personB2 = personB2;
	}

	public Group2() {
		System.out.println("Group2.ctor");
	}
	
	@Autowired
	public Group2(PersonA pA, PersonB pB) {
		System.out.println("Group2.ctor(PersonA pA, PersonB pB)");
		this.personA = pA;
		this.personB = pB;
	}

	public PersonA getPersonA() {
		return personA;
	}

	public PersonB getPersonB() {
		return personB;
	}
	
	public PersonB getPersonB2() {
		return personB2;
	}
	
	@Autowired
	public void mehtodWithMuiltiParams(PersonA pA, PersonB pB) {
		return;
	}
}
