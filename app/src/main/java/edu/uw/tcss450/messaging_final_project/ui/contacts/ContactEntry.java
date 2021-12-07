package edu.uw.tcss450.messaging_final_project.ui.contacts;

public class ContactEntry {
    // variables for our user name
    // and contact number.
    private String userName;
    private String memberId;
    private String firstName;
    private String lastName;
    private boolean isInvite = false;

    // constructor
    public ContactEntry(final String memberId,
                        final String userName,
                        final String firstName,
                        final String lastName) {
        this.memberId = memberId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean isInvite(){
        return isInvite;
    }

    public void setInvite(final boolean invite){
        isInvite = invite;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMemberId() {
        return memberId;
    }
}
