package com.bharathksunil.walksafe.util;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * <h>Utils</h>
 * This is a Java Class Which provides many generic utilities for functioning of the app
 * <br/>
 *
 * @author Bharath <email>bharathk.sunil.k@gmail.com</email>
 */
public class Utils {
    /**
     * This function is used to check if the App is connected to the Internet
     * <b>NOTE:</b> The App Must have a Permission :
     * <uses-permission android:name="android.permission.INTERNET" />
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     *
     * @return true if the app is connected to the internet
     */
    public static boolean isConnected(@NonNull Activity context) {
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager != null ? cManager.getActiveNetworkInfo() : null;
        //IF THE NETWORK IS AVAILABLE:
        return (nInfo != null && nInfo.isConnected());
    }

    /**
     * THIS IS TO SELECT A PICTURE FROM GALLERY TO UPLOAD THE PROFILE PIC
     */
    public static void showFileChooser(@NonNull String type, @NonNull String title, int requestCode, @NonNull Activity context) {
        Intent intent = new Intent();
        intent.setType(type);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(Intent.createChooser(intent, title), requestCode);
    }


    /*public static void refreshContactList(final Activity activityContext) {
        final List<String> contacts = new ArrayList<>();
        try {
            Cursor phones = activityContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (phones != null) {
                while (phones.moveToNext()) {
                    String phoneNumber = phones.getString(phones.getColumnIndex("data1")).trim();
                    if (!contacts.contains(phoneNumber) && phoneNumber.length() >= 10 && phoneNumber.length() < 14) {
                        if (phoneNumber.startsWith("+91")) {
                            phoneNumber = phoneNumber.substring(3, phoneNumber.length());
                        }
                        phoneNumber = phoneNumber.replaceAll("[\\-\\+\\ ]", phoneNumber);
                        if (phoneNumber.length() == 10 && !contacts.contains(phoneNumber)) {
                            contacts.add(phoneNumber);
                        }
                    }
                }
                phones.close();
            }
            Debug.Info("# Contacts Read: " + contacts.size());
            StringRequest contactsRequest = new StringRequest(Request.Method.POST, _ServerConnect.URL_REFRESH_CONTACT_LIST, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Debug.Info("Utils.refreshContactList().getContacts: Response from server: " + response);
                    try {
                        JSONObject data = new JSONObject(response);
                        String code=data.getString("code"), message=data.getString("message");
                        boolean status=data.getBoolean("status");
                        if (status && !data.isNull("data")) {
                            List<_UserHelper> contacts = new ArrayList<>();
                            JSONArray subArray = data.getJSONArray("data");
                            for (int i = 0; i < subArray.length(); i ++) {
                                _UserHelper helper = new _UserHelper();
                                JSONObject subObject = subArray.getJSONObject(i);
                                helper.setUID(subObject.getString("UID"));
                                helper.setName(subObject.getString("NAME"));
                                helper.setEmail(subObject.getString("EMAIL"));
                                helper.setNumber(subObject.getString("NUMBER"));
                                helper.setSex(subObject.getString("SEX"));
                                contacts.add(helper);
                            }
                            DBHandler handler = new DBHandler(activityContext);
                            handler.emptyContacts();
                            handler.insertContacts(contacts);
                        }else if (code.equals(_ServerConnect.ERR_NO_CONTACTS_REGISTERED)){
                            Toast.makeText(activityContext,message,Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(activityContext,message,Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Debug.Message("Utils.refreshContactList().getContacts:"+e.getMessage());
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Debug.Message(" Utils.refreshContactList().getContacts: Error in connection :" + error.getMessage());
                    error.printStackTrace();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    int i = 0;
                    for (String object : contacts) {
                        params.put("phone[" + (i++) + "]", object);
                    }
                    params.put("aid", AppVariables.getInstance().AID);
                    params.put("uid", AppVariables.getInstance().UID);
                    return params;
                }
            };
            StringRequest guardiansRequest = new StringRequest(Request.Method.POST, _ServerConnect.URL_GET_GUARDIANS, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Debug.Info("Utils.refreshContactList().getGuardians: Response from server: " + response);
                    try {
                        JSONObject data = new JSONObject(response);
                        boolean status=data.getBoolean("status");
                        String message=data.getString("message"), code=data.getString("code");
                        if (status && !data.isNull("data")) {
                            List<_UserHelper> guardians = new ArrayList<>();
                            JSONArray subArray = data.getJSONArray("data");
                            for (int i = 0; i < subArray.length(); i ++) {
                                _UserHelper helper = new _UserHelper();
                                JSONObject subObject = subArray.getJSONObject(i);
                                helper.setUID(subObject.getString("GID"));
                                helper.setName(subObject.getString("NAME"));
                                helper.setNumber(subObject.getString("NUMBER"));
                                guardians.add(helper);
                            }
                            DBHandler handler = new DBHandler(activityContext);
                            handler.emptyGuardians();
                            handler.insertGuardians(guardians);
                        }else if (code.equals(_ServerConnect.ERR_NO_GUARDIANS)){
                            Toast.makeText(activityContext,message,Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(activityContext,message,Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Debug.Message("Utils.refreshContactList().getGuardians: "+e.getMessage());
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Debug.Message(" Utils.refreshContactList().getGuardians: Error in connection :" + error.getMessage());
                    error.printStackTrace();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("aid",AppVariables.getInstance().AID);
                    params.put("uid", AppVariables.getInstance().UID);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(activityContext);
            requestQueue.add(contactsRequest);
            requestQueue.add(guardiansRequest);
        } catch (Exception e) {
            Debug.Message("Utils.refreshContactList(): " + e.getMessage());
            Debug.Toast("Exception in reading Contacts", activityContext);
            e.printStackTrace();
        }
    }*/

}
