
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Thread2 implements Runnable {
    List<Student> students;

    public Thread2(List<Student> students){
        this.students = students;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public void run() {
        for (Student student : students) {
            Calendar dob = Calendar.getInstance();
            try {
                dob.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(student.dateOfBirth));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int age = Calendar.getInstance().get(Calendar.YEAR) - dob.get(Calendar.YEAR);

            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte[] encodedAge = md.digest(Integer.toString(age).getBytes());

            student.age = age;

            student.sum = 0;
            String dobStr = student.dateOfBirth.replaceAll("[^0-9]", "");
            for (char c : dobStr.toCharArray()) {
                student.sum += Character.getNumericValue(c);
            }
        }
    }
}