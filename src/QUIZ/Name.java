package QUIZ;

public class Name {
    private String firstName;
    private String middleName;
    private String lastName;

    Name (String firstName, String middleName, String lastName){
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public Name(String firstName, String lastName){
    this.firstName = firstName;
    this.middleName = "";
    this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

   public String getFullName(){
        if (middleName == null || middleName.isEmpty()) {
            return firstName + " " + lastName;
        } else {
            return firstName + " " + middleName + " " + lastName;
        }
    }

    public String getInitials() {
        String initials = "" + firstName.charAt(0);
        if (middleName != null && !middleName.isEmpty()) {
            initials += middleName.charAt(0);
        }
        initials += lastName.charAt(0);
        return initials.toUpperCase();
    }
}
