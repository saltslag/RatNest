package util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

/**
 * Created by Bram on 8/23/2015.
 */
public class ConnectionControler extends AsyncTask<Void, Void, Void>{
    private Context context;
    private ConnectivityManager cm;
    NetworkInfo ni;
    boolean isConnected;

    public ConnectionControler(Context context){
        this.context = context;
    }

    @Override
    protected  void onPreExecute(){
        super.onPreExecute();
        cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
        if(ni != null && ni.isConnected()){
            isConnected = true;
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Void result){
        super.onPostExecute(result);

        //free resources
        cm = null;
        ni = null;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ConnectivityManager getCm() {
        return cm;
    }

    public void setCm(ConnectivityManager cm) {
        this.cm = cm;
    }

    public NetworkInfo getNi() {
        return ni;
    }

    public void setNi(NetworkInfo ni) {
        this.ni = ni;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }
}
