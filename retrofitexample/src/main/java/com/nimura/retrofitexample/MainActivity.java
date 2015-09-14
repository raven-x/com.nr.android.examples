package com.nimura.retrofitexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Converter;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private Button mButtonRetrofit;
    private EditText mResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultText = (EditText) findViewById(R.id.txtResult);
        mButtonRetrofit = (Button) findViewById(R.id.callRetrofit);
        mButtonRetrofit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverter(String.class, new ToStringConverter())
                        .baseUrl("https://ru.wikipedia.org")
                        .build();
                IWikiPageService service = retrofit.create(IWikiPageService.class);
                Call<String> call = service.getPage("Заглавная_страница");
                Callback<String> callback = new Callback<String>() {
                    @Override
                    public void onResponse(Response<String> response) {
                        if(response.isSuccess()) {
                            mResultText.setText(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(
                                MainActivity.this,
                                String.format("Error: %s", t.getLocalizedMessage()),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                };
                call.enqueue(callback);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class ToStringConverter implements Converter<String> {

        @Override
        public String fromBody(ResponseBody rb) throws IOException {
            return rb.string();
        }

        @Override
        public RequestBody toBody(String t) {
            return RequestBody.create(MediaType.parse("text/plain"), t);
        }

    }
}
