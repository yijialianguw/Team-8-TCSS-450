package edu.uw.tcss450.messaging_final_project.ui.contacts;

public class ContactEntry {
    // variables for our user name
    // and contact number.
    private String userName;

    // constructor
    public ContactEntry(String userName) {
        this.userName = userName;
    }

    // on below line we have
    // created getter and setter
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
