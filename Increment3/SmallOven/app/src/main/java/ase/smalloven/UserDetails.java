package ase.smalloven;

/**
 * Created by daras on 14-Oct-16.
 */
public class UserDetails {

    private String FirstName;
    private String LastName;
    private String EMailID;
    private String Password;
    private String Pincode;
    private String CalorieCount;
    private String[] favorites;



    public  String getEMailID() {
        return EMailID;
    }

    public  void setEMailID(String EMailID) {
        this.EMailID = EMailID;
        constants.UserName = EMailID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getCalorieCount() {
        return CalorieCount;
    }

    public void setCalorieCount(String calorieCount) {
        CalorieCount = calorieCount;
    }

    public String[] getFavorites() {
        return favorites;
    }

    public void setFavorites(String[] favorites) {
        this.favorites = favorites;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
