package cn.eccut.dormitoryms;


public class Student {
    //学生学号
    private String number;
    //学生姓名
    private String name;
    //性别
    private String gender;
    //年龄
    private int age;
    //所在院系
    private String college;
    //年级
    private String grade;
    //宿舍号
    private String dorNo;
    //电话
    private String tel;

    public Student() {
    }

    public Student(String number, String name, String gender, int age, String college, String grade, String dorNo, String tel) {
        this.number = number;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.college = college;
        this.grade = grade;
        this.dorNo = dorNo;
        this.tel = tel;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDorNo() {
        return dorNo;
    }

    public void setDorNo(String dorNo) {
        this.dorNo = dorNo;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Student{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", college='" + college + '\'' +
                ", grade='" + grade + '\'' +
                ", dorNo='" + dorNo + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
