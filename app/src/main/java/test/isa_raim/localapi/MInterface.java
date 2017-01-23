package test.isa_raim.localapi;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface MInterface {
    @GET("/Rutas")
    void getRutas(Callback<List<Ruta>> rutaCb);

    @POST("/Rutas")
    void createRuta(@Body Ruta ruta, Callback<Ruta> rutaCb);

}
