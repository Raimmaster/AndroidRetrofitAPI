package test.isa_raim.localapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static String url = "http://9611b196.ngrok.io/api";


    private TextView company;
    private TextView blog;
    private TextView htmlurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        company = (TextView) findViewById(R.id.txtApiCall);
        blog = (TextView) findViewById(R.id.txtBlog);
        htmlurl = (TextView) findViewById(R.id.txtUrl);

        Ruta newRuta = new Ruta();
        newRuta.setIdRuta(5001);
        newRuta.setNombre("Post-Ruta");
        newRuta.setDescripcion("La ruta para los posts; nada que ver, pero vamos.");
        newRuta.setCosto(new Integer(500));

        try{
        //Código de API - Retrofit 1
            RequestInterceptor requestInterceptor = new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("Accept", "application/json");
                    request.addHeader("Content-Type", "application/json");
                }
            };

            RestAdapter radapter = new RestAdapter.Builder()
                    .setRequestInterceptor(requestInterceptor)
                    .setEndpoint(url).
                            build();

            MInterface restInt = radapter.create(MInterface.class);

            restInt.createRuta(newRuta, new Callback<Ruta>() {
                @Override
                public void success(Ruta ruta, Response response) {
                    System.out.println(response.toString());
                    company.setText(response.toString());
                }

                @Override
                public void failure(RetrofitError error) {
                    System.out.println(error.getMessage());
                    company.setText(error.getMessage());
                }
            });

            restInt.getRutas(new Callback<List<Ruta>>() {
                @Override
                public void success(List<Ruta> model, Response response) {
                    for(Ruta rut : model){
                        System.out.println(rut.getIdRuta());
                        company.setText("Id:" + rut.getIdRuta());
                        blog.setText("Nombre:" + rut.getNombre());
                        htmlurl.setText("Descripción:" + rut.getDescripcion());
                    }
                }
                @Override
                public void failure(RetrofitError error) {

                    String err = error.getMessage();
                    System.out.println("Error: " + err);
                    company.setText("El error fue: " + err);
                }
            });
        }catch(Exception ex){
            company.setText(ex.getMessage());
        }
    }
}
