package com.droidapp.ivanelv.mansy;

/**
 * Created by if on 15/06/17.
 */

// Model Class
public class Email
{
    public String recipientAddress, emailSubject, emailMessage;

    public Email(String recipientAddress, String emailSubject, String emailMessage)
    {
        this.recipientAddress = recipientAddress;
        this.emailSubject = emailSubject;
        this.emailMessage = emailMessage;
    }

    public String getRecipientAddress()
    {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress)
    {
        this.recipientAddress = recipientAddress;
    }

    public String getEmailSubject()
    {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject)
    {
        this.emailSubject = emailSubject;
    }

    public String getEmailMessage()
    {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage)
    {
        this.emailMessage = emailMessage;
    }
}
