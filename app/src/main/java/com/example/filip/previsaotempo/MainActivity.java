package com.example.filip.previsaotempo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    PrevisoesTask mTask;
    ArrayList<Previsao> mPrevisoes;
    ListView mListPrevisoes;
    ArrayAdapter<Previsao> mAdapter;

    EditText cityToSearch;
    Button btnProcurar;
    public static String city;
    public static String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListPrevisoes = findViewById(R.id.listPrevisoes);

        cityToSearch = findViewById(R.id.pCity);
        btnProcurar = findViewById(R.id.btnProcurar);

        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = cityToSearch.getText().toString();
                PrevisoesHttp.setUrl(city);
                mListPrevisoes = findViewById(R.id.listPrevisoes);
                search();
            }
        });

    }




    private void search() {
        if (mPrevisoes == null) {
            mPrevisoes = new ArrayList<Previsao>();
        }

        mAdapter = new PrevisaoAdapter(getApplicationContext(), mPrevisoes);
        mListPrevisoes.setAdapter(mAdapter);
        startDownload();
        if (mTask == null) {
            if (PrevisoesHttp.hasConnected(this)) {
                startDownload();
            } else {
                Toast.makeText(getApplicationContext(), "Sem conexão...", Toast.LENGTH_LONG).show();
            }
        } else if (mTask.getStatus() == AsyncTask.Status.RUNNING) {
            Toast.makeText(getApplicationContext(), "......", Toast.LENGTH_LONG).show();
        }
    }

    public void startDownload() {
        if (mTask == null || mTask.getStatus() != AsyncTask.Status.RUNNING) {
            mTask = new PrevisoesTask();
            mTask.execute();
        }
    }

    //INNER CLASS ASICRONA
    class PrevisoesTask extends AsyncTask<Void, Void, ArrayList<Previsao>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //showProgress(true);
            Toast.makeText(getApplicationContext(), "Pronto...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected ArrayList<Previsao> doInBackground(Void... strings) {
            ArrayList<Previsao> previsaoList = PrevisoesHttp.loadPrevisoes();
            return previsaoList;
        }
        @Override
        protected void onPostExecute(ArrayList<Previsao> previsoes) {
            super.onPostExecute(previsoes);
            //     showProgress(false);
            if (previsoes != null) {
                mPrevisoes.clear();
                mPrevisoes.addAll(previsoes);
                mAdapter.notifyDataSetChanged();
            } else {

                Toast.makeText(getApplicationContext(), "Buscando...", Toast.LENGTH_LONG).show();
            }
        }
    }
}
