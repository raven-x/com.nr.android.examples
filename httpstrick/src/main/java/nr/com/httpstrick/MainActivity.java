package nr.com.httpstrick;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.params.HttpParams;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectTask ct = new ConnectTask();
        ct.execute();
    }

    private class ConnectTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
//                URL url = new URL("https://www.vs.ch/cari-online/rechDet");
//                URLConnection urlConnection = url.openConnection();
//                InputStream in = urlConnection.getInputStream();
//                BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
//                String line = null;
//                while ((line = br.readLine()) != null) {
//                    Log.d("tricklog", line);
//                }

                HttpClient httpclient = new DefaultHttpClient();
            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }
}
