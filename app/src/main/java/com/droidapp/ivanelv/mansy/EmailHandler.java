package com.droidapp.ivanelv.mansy;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by if on 15/06/17.
 */

public class EmailHandler extends AsyncTask<Void, Void, Void>
{
    private Context mContext;
    private Session mSession;

    private ArrayList<Email> recipients;

    private ProgressDialog progressDialog;

    public EmailHandler(Context context, ArrayList<Email> recipients)
    {
        this.mContext = context;
        this.recipients = recipients;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        // Pop-up Progress Dialog
        progressDialog = ProgressDialog.show(
                mContext,
                "Sending Message",
                "Please wait...", false, false);
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);

        // Dismiss Progress Dialog
        progressDialog.dismiss();

        // Notify User That Email Has Been Sent Successfully!
        Toast.makeText(mContext, "Email Sent Successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... params)
    {
        Properties props = new Properties();

        // 465 = SSL , 587 = TLS
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        mSession = Session.getDefaultInstance(props, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                // Your Email and Your Email Password Authentication
                return new PasswordAuthentication(EmailConfig.EMAIL, EmailConfig.PASSWORD);
            }
        });

        // Send To Each Participants
        for (int i = 0; i < recipients.size(); i++)
        {
            try
            {
                MimeMessage mimeMessage = new MimeMessage(mSession);
                mimeMessage.setFrom(new InternetAddress(EmailConfig.EMAIL));

                mimeMessage.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(recipients.get(i).getRecipientAddress()));

                mimeMessage.setSubject(recipients.get(i).getEmailSubject());
                mimeMessage.setText(recipients.get(i).getEmailMessage());

                Transport.send(mimeMessage);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }
}
