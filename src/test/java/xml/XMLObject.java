package xml;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "TestDoc")
@XmlAccessorType(XmlAccessType.FIELD)
public class XMLObject {

    @XmlElement(name = "age")
    private int age;
    @XmlElement(name = "subject")
    private String subject;
    @XmlElement(name = "name")
    private String name;

    public void setAge(int age) {
        this.age = age;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getSubject() {
        return subject;
    }

    public String getName() {
        return name;
    }
}
