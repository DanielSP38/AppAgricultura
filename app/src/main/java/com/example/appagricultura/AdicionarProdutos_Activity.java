package com.example.appagricultura;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class AdicionarProdutos_Activity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText editTextId, editTextNome, editTextFornecedor, editTextQuantidade, editTextPreco;
    RatingBar rating;
    Spinner spinTipo;
    ProgressBar progressBar;
    ListView listProdutos;
    Button buttonAdd;

    List<Produto>produtoList;

    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adicionar_produtos_layout);

        editTextId = (EditText) findViewById(R.id.editTextId);
        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextFornecedor = (EditText) findViewById(R.id.editTextFornecedor);
        editTextQuantidade = (EditText) findViewById(R.id.editTextQuantidade);
        editTextPreco = (EditText) findViewById(R.id.editTextPreco);
        rating = (RatingBar) findViewById(R.id.rating);
        spinTipo = (Spinner) findViewById(R.id.spinTipo);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        listProdutos = (ListView) findViewById(R.id.listProdutos);

        produtoList = new ArrayList<>();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isUpdating) {
                    updateProduto();
                } else {
                    createProduto();
                }
            }
        });
        readProduto();
    }

    private void createProduto() {
        String nome = editTextNome.getText().toString().trim();
        String fornecedor = editTextFornecedor.getText().toString().trim();
        String quantidade = editTextNome.getText().toString().trim();
        String preco = editTextFornecedor.getText().toString().trim();
         int  ratingBar = (int) rating.getRating();
        String tipo = spinTipo.getSelectedItem().toString();

        if (TextUtils.isEmpty(nome)) {
            editTextNome.setError("Por favor insira o nome do Produto");
            editTextNome.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(fornecedor)) {
            editTextNome.setError("Por favor insira o nome do Fornecedor");
            editTextNome.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(quantidade)) {
            editTextNome.setError("Por favor insira a quantidade/peso do Produto");
            editTextNome.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(preco)) {
            editTextNome.setError("Por favor insira o valor do Produto");
            editTextNome.requestFocus();
            return;
        }

        HashMap<String, String> params =  new HashMap<>();
        params.put("nome", nome);
        params.put("fornecedor", fornecedor);
        params.put("quantidade", quantidade);
        params.put("tipo", tipo);
        params.put("preco", preco);
        params.put("rating", String.valueOf(ratingBar));

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_PRODUTOS, params, CODE_POST_REQUEST);
        request.execute();


    }
    private void readProduto() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_PRODUTOS, null,CODE_GET_REQUEST);
        request.execute();
    }

    private void updateProduto() {
        String id = editTextId.getText().toString();
        String nome = editTextNome.getText().toString().trim();
        String fornecedor = editTextFornecedor.getText().toString().trim();
        String quantidade = editTextQuantidade.getText().toString().trim();
        String preco = editTextPreco.getText().toString().trim();

        int ratingBar = (int) rating.getRating();
        String tipo = spinTipo.getSelectedItem().toString();

        if (TextUtils.isEmpty(nome)) {
            editTextNome.setError("Por favor insira o nome do Produto");
            editTextNome.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(fornecedor)) {
            editTextNome.setError("Por favor insira o nome do Fornecedor");
            editTextNome.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(quantidade)) {
            editTextNome.setError("Por favor insira a quantidade/peso do Produto");
            editTextNome.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(preco)) {
            editTextNome.setError("Por favor insira o valor do Produto");
            editTextNome.requestFocus();
            return;
        }
        HashMap<String, String> params =  new HashMap<>();
        params.put("nome", nome);
        params.put("fornecedor", fornecedor);
        params.put("quantidade", quantidade);
        params.put("tipo", tipo);
        params.put("preco", preco);
        params.put("rating", String.valueOf(ratingBar));

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_PRODUTOS, params, CODE_POST_REQUEST);
        request.execute();

        buttonAdd.setText("Adcionar");
    }


















    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshHeroList(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

    class HeroAdapter extends ArrayAdapter<Hero> {
        List<Hero> heroList;

        public HeroAdapter(List<Hero> heroList) {
            super(MainActivity.this, R.layout.layout_hero_list, heroList);
            this.heroList = heroList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_hero_list, null, true);

            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final Hero hero = heroList.get(position);

            textViewName.setText(hero.getName());

            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isUpdating = true;
                    editTextHeroId.setText(String.valueOf(hero.getId()));
                    editTextName.setText(hero.getName());
                    editTextRealname.setText(hero.getRealname());
                    ratingBar.setRating(hero.getRating());
                    spinnerTeam.setSelection(((ArrayAdapter<String>) spinnerTeam.getAdapter()).getPosition(hero.getTeamaffiliation()));
                    buttonAddUpdate.setText("Alterar");
                }
            });

            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("Delete " + hero.getName())
                            .setMessage("Tem certeza de que deseja excluí-lo?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteHero(hero.getId());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });

            return listViewItem;
        }
    }

}
