package com.example.appagricultura;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class AdicionarProdutosActivity extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText editTextId, editTextNome, editTextFornecedor, editTextQuantidade,editTextDescricao, editTextPreco;
    RatingBar rating;
    Spinner spinTipo;
    ProgressBar progressBar;
    ListView listView;
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
        editTextDescricao = (EditText) findViewById(R.id.editTextDescricao);
        editTextPreco = (EditText) findViewById(R.id.editTextPreco);
        rating = (RatingBar) findViewById(R.id.rating);
        spinTipo = (Spinner) findViewById(R.id.spinTipo);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        listView = (ListView) findViewById(R.id.listProdutos);

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
        String descricao = editTextDescricao.getText().toString().trim();
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
        if (TextUtils.isEmpty(descricao)) {
            editTextNome.setError("Por favor insira a descrição do Produto");
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
        params.put("descricao", descricao);
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
        String descricao = editTextDescricao.getText().toString().trim();
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
        if (TextUtils.isEmpty(descricao)) {
            editTextNome.setError("Por favor insira a descrição do Produto");
            editTextNome.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(preco)) {
            editTextNome.setError("Por favor insira o valor do Produto");
            editTextNome.requestFocus();
            return;
        }
        HashMap<String, String> params =  new HashMap<>();
        params.put("id", id);
        params.put("nome", nome);
        params.put("fornecedor", fornecedor);
        params.put("quantidade", quantidade);
        params.put("descricao", descricao);
        params.put("tipo", tipo);
        params.put("preco", preco);
        params.put("rating", String.valueOf(ratingBar));

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_PRODUTOS, params, CODE_POST_REQUEST);
        request.execute();

        buttonAdd.setText("Adicionar");
        editTextNome.setText("");
        editTextFornecedor.setText("");
        editTextQuantidade.setText("");
        editTextDescricao.setText("");
        spinTipo.setSelection(0);
        editTextPreco.setText("");
        rating.setRating(0);

        isUpdating = false;
    }

    private void deleteProduto(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_PRODUTOS + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshProdutoList(JSONArray Produto) throws JSONException {
        produtoList.clear();

        for (int i = 0; i <Produto.length(); i++) {
            JSONObject obj = Produto.getJSONObject(i);

             produtoList.add(new Produto(
                    obj.getInt("id"),
                    obj.getString("nome"),
                    obj.getString("fornecedor"),
                    obj.getString("quantidade"),
                    obj.getString("descricao"),
                    obj.getString("preco"),
                    obj.getString("tipo"),
                    obj.getInt("rating")

            ));
        }
        ProdutoAdapter adapter = new ProdutoAdapter(produtoList);
        listView.setAdapter(adapter);
    }
    private class PerformNetworkRequest extends
            AsyncTask<Void, Void, String> {
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
                    refreshProdutoList(object.getJSONArray("produto"));
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

    class ProdutoAdapter extends ArrayAdapter<Produto> {
        List<Produto> produtoList;

        public ProdutoAdapter(List<Produto> produtoList) {
            super(AdicionarProdutosActivity.this, R.layout.listaprodutos_layout, produtoList);
            this.produtoList = produtoList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.listaprodutos_layout, null, true);

            TextView textViewNome = listViewItem.findViewById(R.id.textViewNome);

            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final Produto produto = produtoList.get(position);

            textViewNome.setText(produto.getNome());

            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isUpdating = true;
                    editTextId.setText(String.valueOf(produto.getId()));
                    editTextNome.setText(produto.getNome());
                    editTextFornecedor.setText(produto.getFornecedor());
                    editTextPreco.setText(produto.getPreco());
                    editTextQuantidade.setText(produto.getQuantidade());
                    editTextDescricao.setText(produto.getDescricao());
                    rating.setRating(produto.getRating());
                    spinTipo.setSelection(((ArrayAdapter<String>) spinTipo.getAdapter()).getPosition(produto.getTipo()));
                    buttonAdd.setText("Alterar");
                }
            });

            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(AdicionarProdutosActivity.this);

                    builder.setTitle("Delete " + produto.getNome())
                            .setMessage("Tem certeza de que deseja excluí-lo?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteProduto(produto.getId());
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
