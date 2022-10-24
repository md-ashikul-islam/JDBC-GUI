public class Main {
    public static void main(String[] args) {
        Registration regForm = new Registration(null);
        Student student = regForm.student;
        if (student != null) {
            System.out.println("Successful registration of: " + student.name);
        }
        else {
            System.out.println("Registration canceled");
        }
    }
}