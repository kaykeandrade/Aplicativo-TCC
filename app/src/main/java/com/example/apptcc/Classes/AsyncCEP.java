package com.example.apptcc.Classes;

import android.os.AsyncTask;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class AsyncCEP extends AsyncTask {

    EditText etLogradouro, etCidade, etBairro, etUF;
        String cep;

        public AsyncCEP(EditText etLogradouro, EditText etCidade, EditText etUF, EditText etBairro, String cep) {
            this.cep = cep;
            this.etLogradouro = etLogradouro;
            this.etCidade = etCidade;
            this.etUF = etUF;
            this.etBairro = etBairro;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            return JsonHandler.getJson("https://cep.awesomeapi.com.br/json/"+cep);
        }

        @Override
        protected void onPostExecute(Object endereco) {
            super.onPostExecute(endereco);
            try {
                JSONObject jsonObject = new JSONObject((String) endereco);
                String log = jsonObject.getString("address_type");
                String nome = jsonObject.getString("address_name");
                etLogradouro.setText(log+" "+nome);
                etCidade.setText(jsonObject.getString("city"));
                etUF.setText(jsonObject.getString("state"));
                etBairro.setText(jsonObject.getString("district"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

