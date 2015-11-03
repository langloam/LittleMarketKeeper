package samples.exoguru.materialtabs.common.Tabs;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListAdapter;

import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;
import samples.exoguru.materialtabs.DB.CDbManager;
import samples.exoguru.materialtabs.R;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Tab_Collect extends Fragment {

    public static String FBID;

    String ItemLongClick = null;
    CallbackManager callbackManager;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    List<String> business,stores;

    @Override
    public void onResume() {
        super.onResume();
        facebookInit();
        collectInit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.tab_collect,container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //HashKey();
        facebookInit();
        collectInit();
    }



    public void collectInit() {

        // get the listview
        expListView = (ExpandableListView) getActivity().findViewById(R.id.lvExp);


        // preparing list data
        prepareListData();

        listAdapter = new samples.exoguru.materialtabs.common.Adapter.ExpandableListAdapter(this.getActivity().getApplicationContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //標題開啟
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                /*
                Toast.makeText(getActivity(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
                */
            }
        });

        //標題收起
        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                /*
                Toast.makeText(getActivity(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();
                */
            }
        });


        //內容
        // Listview on child click listener

        expListView.setOnChildClickListener(new OnChildClickListener() {


            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                /*
                Toast.makeText(
                        getActivity(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                */
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding 主項目
        listDataHeader.add("商圈");
        listDataHeader.add("店家");

        // Adding 子項目(商圈)
        business = new ArrayList<String>();
        // Adding 子項目(店家)
        stores = new ArrayList<String>();

        //查詢SQLite的表格是否有收藏

        //查詢商圈
        Cursor table = (new CDbManager(this.getActivity())).QueryBySql("SELECT * FROM tCollectBusiness");
        if(table.getCount()>0){
            String[] datas=new String[table.getCount()];
            table.moveToFirst();

            for(int i=0;i<datas.length;i++){
                business.add(table.getString(4));
                table.moveToNext();
            }

        }else {
            business.add("尚未收藏商圈");
        }

        //查詢店家
        table = (new CDbManager(this.getActivity())).QueryBySql("SELECT * FROM tCollectStores");
        if(table.getCount()>0){
            String[] datas=new String[table.getCount()];
            table.moveToFirst();

            for(int i=0;i<datas.length;i++){
                stores.add(table.getString(4));
                table.moveToNext();
            }

        }else {
            stores.add("尚未收藏店家");
        }

        listDataChild.put(listDataHeader.get(0), business); // Header, Child data
        listDataChild.put(listDataHeader.get(1), stores);


        //長按事件
        expListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {

                    final int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    final int childPosition = ExpandableListView.getPackedPositionChild(id);

                    Log.d("GetListText",(parent.getItemAtPosition(position)).toString());
                    ItemLongClick = (parent.getItemAtPosition(position)).toString();
                    AlertDialog.Builder delete_Stores_Business = new AlertDialog.Builder(getActivity());

                    delete_Stores_Business.setTitle("刪除").setMessage("確認刪除?")
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //要有網路才可以刪除(避免同步問題
                                    switch (groupPosition) {

                                        case 0:
                                            Cursor table = (new CDbManager(getActivity())).QueryBySql("SELECT * FROM tCollectBusiness where fBusinessName = '" + ItemLongClick+"'");
                                            table.moveToFirst();

                                            (new CDbManager(getActivity())).Delete("tCollectBusiness", table.getInt(0));
                                            business.remove(childPosition);
                                            expListView.setAdapter(listAdapter);
                                            break;

                                        case 1:
                                            table = (new CDbManager(getActivity())).QueryBySql("SELECT * FROM tCollectStores where fStoresName = '" + ItemLongClick + "'");
                                            table.moveToFirst();

                                            (new CDbManager(getActivity())).Delete("tCollectStores", table.getInt(0));
                                            stores.remove(childPosition);
                                            expListView.setAdapter(listAdapter);
                                            break;
                                    }

                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create().show();

                    return true;
                }
                return false;
            }
        });

    }


    //查詢APP HashKey
    private void HashKey() {
        PackageInfo info;
        try {
            info = getActivity().getPackageManager().getPackageInfo("samples.exoguru.materialtabs", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String KeyResult = new String(Base64.encode(md.digest(), 0));//String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", KeyResult);
                Toast.makeText(getActivity(), "My FB Key is \n" + KeyResult, Toast.LENGTH_LONG).show();
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }

    private void facebookInit() {

        //宣告callback Manager
        callbackManager = CallbackManager.Factory.create();
        //找到login button

        loginButton = (LoginButton) getView().findViewById(R.id.login_button);
        loginButton.setFragment(this);

        //幫loginButton增加callback function

        //這邊為了方便 直接寫成inner class


        /*
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","user_photos","public_profile"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        System.out.println("Success");
                        GraphRequest.newMeRequest(
                                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject json, GraphResponse response) {
                                        if (response.getError() != null) {
                                            // handle error
                                            System.out.println("ERROR");
                                        } else {
                                            System.out.println("Success");
                                            try {

                                                String jsonresult = String.valueOf(json);
                                                System.out.println("JSON Result"+jsonresult);

                                                String str_email = json.getString("email");
                                                String str_id = json.getString("id");
                                                String str_firstname = json.getString("first_name");
                                                String str_lastname = json.getString("last_name");

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                }).executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        Log.d("TAG_CANCEL","On cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("TAG_ERROR",error.toString());
                    }
                });

        */


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            //登入成功
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(getActivity(), "登入成功", Toast.LENGTH_SHORT).show();
                //accessToken之後或許還會用到 先存起來
                AccessToken accessToken = loginResult.getAccessToken();


                //send request and call graph api
                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {

                    //當RESPONSE回來的時候
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        //讀出姓名 ID FB個人頁面連結

                        Log.d("FB", "complete");
                        Log.d("FB", object.optString("name"));
                        Log.d("FB", object.optString("link"));
                        Log.d("FB", object.optString("id"));
                        FBID = object.optString("id");

                        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("FBID", 0);
                        sharedPreferences.edit().putString("FBID",object.optString("id")).commit();

                        Toast.makeText(getActivity(), "資料讀取中", Toast.LENGTH_LONG).show();

                        CDbManager db = new CDbManager(getActivity());
                        db.Delete("tShop");
                        Log.d("appCREATE", "Shop_del");

                        StrictMode.ThreadPolicy l_policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(l_policy);

                        try {

                            URL url=new URL("http://mylittlemarket.azurewebsites.net/FindFBMarket.aspx?fbid=" + FBID);
                            URLConnection conn=url.openConnection();
                            InputStream streamIn=conn.getInputStream();

                            BufferedReader r = new BufferedReader(new InputStreamReader(streamIn));
                            JSONArray jsonArray= new JSONArray(r.readLine());
                            for(int i=0; i<jsonArray.length(); i++) {

                                ContentValues row =new ContentValues();
                                row.put("fId",Integer.valueOf(jsonArray.getJSONObject(i).get("id").toString()));
                                row.put("fFBID",jsonArray.getJSONObject(i).get("FBid").toString());
                                row.put("fBusinessID",jsonArray.getJSONObject(i).get("marketid").toString());
                                row.put("fBusinessName",jsonArray.getJSONObject(i).get("marketname").toString());
                                db = new CDbManager(getActivity());
                                db.Insert("tCollectBusiness", row);

                            }

                            url=new URL("http://mylittlemarket.azurewebsites.net/FindFBShop.aspx?fbid=" + FBID);
                            conn=url.openConnection();
                            streamIn=conn.getInputStream();

                            r = new BufferedReader(new InputStreamReader(streamIn));
                            jsonArray= new JSONArray(r.readLine());
                            for(int i=0; i<jsonArray.length(); i++) {

                                ContentValues row =new ContentValues();
                                row.put("fId",Integer.valueOf(jsonArray.getJSONObject(i).get("id").toString()));
                                row.put("fFBID",jsonArray.getJSONObject(i).get("FBid").toString());
                                row.put("fStoresID",jsonArray.getJSONObject(i).get("shopid").toString());
                                row.put("fStoresName",jsonArray.getJSONObject(i).get("shopname").toString());
                                db = new CDbManager(getActivity());
                                db.Insert("tCollectStores", row);

                            }
                        } catch (MalformedURLException e) {
                            Log.d("URLERROR", e.getMessage());
                            e.printStackTrace();
                        }catch (IOException e) {
                            Log.d("URLERROR",e.getMessage());
                            e.printStackTrace();
                        }catch (Exception e) {
                            Log.d("URLERROR",e.getMessage());
                            e.printStackTrace();
                        }


                        //NoBusinessCollect(business);
                        //NoStoresCollect(stores);
                        BusinessCollectDelete(business);
                        StoresCollectDelete(stores);
                        expListView.setAdapter(listAdapter);


                        //查詢商圈
                        Cursor table = (new CDbManager(getActivity())).QueryBySql("SELECT * FROM tCollectBusiness");
                        if(table.getCount()>0){
                            String[] datas=new String[table.getCount()];
                            table.moveToFirst();

                            for(int i=0;i<datas.length;i++){
                                business.add(table.getString(4));
                                table.moveToNext();
                            }

                        }else {
                            business.add("尚未收藏商圈");
                        }

                        //查詢店家
                        table = (new CDbManager(getActivity())).QueryBySql("SELECT * FROM tCollectStores");
                        if(table.getCount()>0){
                            String[] datas=new String[table.getCount()];
                            table.moveToFirst();

                            for(int i=0;i<datas.length;i++){
                                stores.add(table.getString(4));
                                table.moveToNext();
                            }

                        }else {
                            stores.add("尚未收藏店家");
                        }


                        expListView.setAdapter(listAdapter);
                        Log.d("FB", "收藏更新完成");

                    }
                });

                //包入你想要得到的資料 送出request
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link");
                request.setParameters(parameters);
                request.executeAsync();
            }

            //登入取消
            @Override
            public void onCancel() {
                // App code
                Log.d("FB", "CANCEL");
            }

            //登入失敗
            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("FB", exception.toString());
            }
        });

    }


    public static boolean isFBloggedIn() {
        //check login
        return (AccessToken.getCurrentAccessToken()) != null;
    }

    /*
    expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

        @Override
        public void onGroupExpand(int groupPosition) {
            Toast.makeText(getApplicationContext(),
                    listDataHeader.get(groupPosition) + " Expanded",
                    Toast.LENGTH_SHORT).show();
        }
    });


    expListView.setOnChildClickListener(new OnChildClickListener() {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v,
        int groupPosition, int childPosition, long id) {
            Toast.makeText(
                    getApplicationContext(),
                    listDataHeader.get(groupPosition)
                            + " : "
                            + listDataChild.get(
                            listDataHeader.get(groupPosition)).get(
                            childPosition), Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
    });

    */


    //新增商圈或店家時將原本的"尚未收藏"文字刪除
    private void NoBusinessCollect(List<String> business){
        business.remove(0);
    }

    private void NoStoresCollect(List<String> stores){
        stores.remove(0);
    }

    private void BusinessCollectDelete(List<String> business){
        for (int i = 0; i < business.size(); i++){
            business.remove(i);
            Log.d("FB", "BusinessCollectDelete");
            Log.d("FB", "size"+business.size());
        }


    }

    private void StoresCollectDelete(List<String> stores){
        for (int i = 0; i < stores.size(); i++){
            stores.remove(i);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    LoginButton loginButton;
}
