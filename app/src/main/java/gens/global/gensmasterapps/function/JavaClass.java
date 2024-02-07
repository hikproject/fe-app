package gens.global.gensmasterapps.function;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Base64;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class JavaClass {
    static char[] NUMBERS = "0123456789".toCharArray();
    static Random rand = new SecureRandom();
    public JavaClass(){

    }
    public static String BaseUrl(){
        //return "http://192.168.1.191:3000/";
        return "https://api.gensglobal.biz.id/";
    }
    public static String Url(){
        return BaseUrl()+"api/";
    }
    public static String device(){
        return Build.MODEL+""+Build.MANUFACTURER+""+Build.VERSION.SDK_INT+""+Build.ID;
    }
    public static String room(){
        byte[] data = device().getBytes();
        byte[] encodedBytes = Base64.encode(data, Base64.DEFAULT);
        return new String(encodedBytes).trim();
    }
    public static String Rupiah(int angka){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String yourFormattedString = formatter.format(angka);
        return "Rp. "+yourFormattedString.replace(",", ".");
    }
    public static String generateInvoiceCode() {
        // Mendapatkan tanggal saat ini
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String dateString = dateFormat.format(currentDate);
        String uniqueNumber = generateRandomNumber();
        String invoiceCode = dateString + uniqueNumber;

        return invoiceCode;
    }

    private static String generateRandomNumber() {
        // Menghasilkan angka unik dengan panjang 4 digit
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        return String.format(Locale.getDefault(), "%04d", randomNumber);
    }
    public static boolean Validate(EditText editText, String pesan, Context context) {
        MyMessage myMessage = new MyMessage(context);
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        myMessage.Error(pesan);
        editText.requestFocus();
        return false;
    }
    public static void HideButton(Context mContext, Activity mActivity){
        try {
            InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            //Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public static String Invoice() {
        char[] password = new char[12];
        password[0] = NUMBERS[rand.nextInt(NUMBERS.length)];
        for (int i = 0; i < 12; i++) {
            password[i] = NUMBERS[rand.nextInt(NUMBERS.length)];
        }
        for (int i = 0; i < password.length; i++) {
            int randomPosition = rand.nextInt(password.length);
            char temp = password[i];
            password[i] = password[randomPosition];
            password[randomPosition] = temp;
        }
        return new String(password).trim();
    }
    public static String KoduUnik() {
        char[] password = new char[3];
        password[0] = NUMBERS[rand.nextInt(NUMBERS.length)];
        for (int i = 0; i < 3; i++) {
            password[i] = NUMBERS[rand.nextInt(NUMBERS.length)];
        }
        for (int i = 0; i < password.length; i++) {
            int randomPosition = rand.nextInt(password.length);
            char temp = password[i];
            password[i] = password[randomPosition];
            password[randomPosition] = temp;
        }
        return new String(password).trim();
    }
    public static int number(String numbers){
        String cleanUang = numbers.replaceAll("[^0-9]", "");
        return  Integer.parseInt(cleanUang);
    }
    public static String ApiKey(){
        return "31c84acdf24da08c2e60fcf28ee08a64792d38692182533905dc62c04776f8d4";
    }
}
