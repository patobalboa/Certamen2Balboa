/*
  Autor: Patricio Balboa
  Rut: 17.592.019-6
 */

package cl.codelab.certamen2balboa;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

public class getCientificoAPI extends AsyncTask<Void, Void, String> {
    String json_GetString;


    @Override
    protected String doInBackground(Void... voids) {
        URL url = null;

        try {
            url = new URL("http://www.codeplus.cl:8000/cientifico");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            assert url != null;
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer stringBuffer = new StringBuffer();
            while ((json_GetString = bufferedReader.readLine()) != null){
                stringBuffer.append(json_GetString).append("\n");
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuffer.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.i("JSON GET",result);
    }
}
