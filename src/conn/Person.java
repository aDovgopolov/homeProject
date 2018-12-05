package conn;

public class Person {
    private Long id;
    private int index;
    private String fname;
    private String lname;
    private Long age;


    public Person() {
    }

    public Person(int index, String fname, String lname, long age) {
        this.index = index;
        this.fname = fname;
        this.lname = lname;
        this.age = age;
    }

    public Person(String fname, String lname, long age) {
        this.fname = fname;
        this.lname = lname;
        this.age = age;
    }

    public Person(Person person){
        this.id = person.getId();
        this.index = person.getIndex();
        this.fname = person.getFname();
        this.lname = person.getLname();
        this.age = person.getAge();
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", index=" + index +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", age=" + age +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }
}
