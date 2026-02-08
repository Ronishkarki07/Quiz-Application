package QUIZ;

/**
 * Interface defining the contract for person name representation
 */
public interface PersonName {
    String getFirstName();
    void setFirstName(String firstName);
    String getMiddleName();
    void setMiddleName(String middleName);
    String getLastName();
    void setLastName(String lastName);
    String getFullName();
    String getInitials();
}