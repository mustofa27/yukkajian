package network;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by LENOVO on 4/14/2017.
 */

public class UserNetwork extends BaseNetwork{

    public UserNetwork(Context context) {
        super(context);
    }
    public void Login(JSONObject jsonObject, JsonCallback jsonCallback){
        connectionHandler.MakeConnection(ConnectionHandler.post_method,"user/login",jsonObject,jsonCallback,"Processing","Please Wait");
    }
    public void UpdateProfil(JSONObject jsonObject, JsonCallback jsonCallback){
        connectionHandler.MakeConnection(ConnectionHandler.post_method,"",jsonObject,jsonCallback,"Processing","Please Wait");
    }
    public void Register(JSONObject jsonObject, JsonCallback jsonCallback){
        connectionHandler.MakeConnection(ConnectionHandler.post_method,"user/register",jsonObject,jsonCallback,"Processing","Please Wait");
    }
}
