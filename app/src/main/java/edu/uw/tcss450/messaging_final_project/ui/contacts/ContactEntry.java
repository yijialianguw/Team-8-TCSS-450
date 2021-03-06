package edu.uw.tcss450.messaging_final_project.ui.contacts;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ContactEntry implements Serializable {
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

    public static ContactEntry createFromJSONString(final String json) throws JSONException {
        final JSONObject JSON = new JSONObject(json);
        String memberid = JSON.getString("memberid");
        String email = JSON.getString("email");
        String first = JSON.getString("firstname");
        String last = JSON.getString("lastname");
        return new ContactEntry(memberid,email,first,last);
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
