package network;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by LENOVO on 4/14/2017.
 */

public class KajianNetwork extends BaseNetwork{

    public KajianNetwork(Context context) {
        super(context);
    }
    public void CreateKajian(JSONObject jsonObject, JsonCallback jsonCallback){
        connectionHandler.MakeConnection(ConnectionHandler.post_method,"",jsonObject,jsonCallback,"Processing","Please Wait");
    }
    public void GetKajian(JSONObject jsonObject, JsonCallback jsonCallback){
        connectionHandler.MakeConnection(ConnectionHandler.get_method,"",jsonObject,jsonCallback);
    }
}
