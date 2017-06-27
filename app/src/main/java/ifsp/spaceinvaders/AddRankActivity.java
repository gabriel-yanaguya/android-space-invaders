package ifsp.spaceinvaders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class AddRankActivity extends AppCompatActivity  {

    private boolean ganhou;
    private int pontos = 0;
    private TextView txtMsg, txtPontos;
    private EditText edtNome;
    private Button btnSalvar, btnSair;

    private String nome, pontuacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rank);

        txtMsg = (TextView)findViewById(R.id.txtMsg);
        txtPontos = (TextView)findViewById(R.id.txtPontos);
        edtNome = (EditText) findViewById(R.id.edtNome);

        btnSalvar = (Button)findViewById(R.id.btnSalvar);
        btnSair = (Button)findViewById(R.id.btnSair);

        Intent it = getIntent();
        ganhou = it.getBooleanExtra("ganhou",true);
        pontos = it.getIntExtra("pontos", -1);

        if(!ganhou){
            txtMsg.setText("PERDEU!");
        }

        txtPontos.setText(String.valueOf(pontos));

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            nome = edtNome.getText().toString();
            pontuacao = String.valueOf(pontos);

            new WSCall().execute();

                Intent rankingIntent = new Intent(AddRankActivity.this, RankingActivity.class);

                AddRankActivity.this.startActivity(rankingIntent);
                AddRankActivity.this.finish();

            }
        });

    }



    public class WSCall extends AsyncTask<Void, Void, Void> {
        String lista;

        @Override
        protected Void doInBackground(Void... params) {
            Log.e("WS", "RUNNING WS INSERT");
            try {
                URL url = new URL("http://192.168.43.43:8080/SpaceInvadersWS/webresources/spaceinvaders");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestMethod("PUT");

                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("nome", nome);
                jsonObject.put("pontuacao", pontuacao);

                out.write(jsonObject.toString());
                out.close();
                urlConnection.connect();

                urlConnection.getInputStream();


            } catch (MalformedURLException | ProtocolException e) {
                Log.e("WSINSERT", "MalformedURLException - "+e.getMessage());
            } catch (IOException e){
                Log.e("WSINSERT", "IOException - "+e.getMessage());
                e.printStackTrace();
            } catch (JSONException e){
                Log.e("WSINSERT", "JSONException - "+e.getMessage());
            }
            return null;
        }
    }
}
