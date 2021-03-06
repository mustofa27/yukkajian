package network;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by LENOVO on 4/14/2017.
 */

public class PertanyaanNetwork extends BaseNetwork{

    public PertanyaanNetwork(Context context) {
        super(context);
    }
    public void CreatePertanyaan(JSONObject jsonObject, JsonCallback jsonCallback){
        connectionHandler.MakeConnection(ConnectionHandler.post_method,"pertanyaan/post",jsonObject,jsonCallback,"Processing","Please Wait");
    }
    public void GetPertanyaan(int id, JsonCallback jsonCallback){
        connectionHandler.MakeConnection(ConnectionHandler.get_method,"pertanyaan/get/"+id,null,jsonCallback,"Processing","Please Wait");
    }
}
