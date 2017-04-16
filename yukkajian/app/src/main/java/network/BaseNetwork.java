package network;

import android.content.Context;

import model.UserModel;

/**
 * Created by LENOVO on 3/12/2017.
 */

public class BaseNetwork {
    protected ConnectionHandler connectionHandler;
    protected UserModel userModel;
    protected Context context;
    public BaseNetwork(Context context){
        connectionHandler = new ConnectionHandler(context);
        userModel = new UserModel(context);
    }
}
