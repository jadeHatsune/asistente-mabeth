package com.tonala.mabeth.ui.Maps;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.maps.android.PolyUtil;
import com.tonala.mabeth.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MapsFragment extends Fragment implements OnMapReadyCallback{

    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private LatLng direccioncoord;
    private String mode = "driving";
    private Button btnDireccion, Caminata, Auto, Bici;
    private TextView Direccionlbl, Distancialbl, tiempolbl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        btnDireccion = view.findViewById(R.id.btnDireccionMap);
        Direccionlbl = view.findViewById(R.id.lblDireccionMap);
        Distancialbl = view.findViewById(R.id.lblDistanciaMap);
        tiempolbl = view.findViewById(R.id.lblTiempoMap);
        Caminata = view.findViewById(R.id.btnWalking);
        Auto = view.findViewById(R.id.btnDriving);
        Bici = view.findViewById(R.id.btnBic);


        Caminata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "walking";
                if(marcador != null){
                    obtenerJson();
                }
                Caminata.setEnabled(false);
                Auto.setEnabled(true);
                Bici.setEnabled(true);
            }
        });

        Auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "driving";
                if(marcador != null){
                    obtenerJson();
                }
                Auto.setEnabled(false);
                Caminata.setEnabled(true);
                Bici.setEnabled(true);
            }
        });

        Bici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "bicycling";
                if(marcador != null){
                    obtenerJson();
                }
                Bici.setEnabled(false);
                Auto.setEnabled(true);
                Caminata.setEnabled(true);
            }
        });

        Places.initialize(getActivity(), getString(R.string.api_maps));
        PlacesClient placesClient = Places.createClient(this.getActivity());
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);

        btnDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                        .build(requireContext());
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Direccionlbl.setText(place.getName());
                direccioncoord = place.getLatLng();
                crearMarcador(direccioncoord);
                obtenerJson();
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {

            } else if (resultCode == RESULT_CANCELED) {

            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    GoogleMap map;
    Marker marcador = null;
    Boolean centrar = true;
    Boolean notificarCerca = true;
    Boolean notificarLlegada = true;
    List<Polyline> polyline2 = null;
    JSONObject jso;
    Double longitudOrigen, latitudOrigen;
    Double longitudDestino = null, latitudDestino = null;

    public void crearMarcador(LatLng latLng){
        if(marcador != null){
            marcador.remove();
            marcador = null;
            longitudDestino = null;
            latitudDestino = null;
            notificarLlegada = true;
            notificarCerca = true;
        }
        if(marcador == null){
            marcador = map.addMarker(new MarkerOptions().position(latLng).title("Destino").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
            longitudDestino = latLng.longitude;
            latitudDestino = latLng.latitude;
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        map = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)){

            }else{
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            return;
        }

        map.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latitudOrigen = location.getLatitude();
                longitudOrigen = location.getLongitude();

                LatLng miPosicion = new LatLng(latitudOrigen, longitudOrigen);

                if(centrar){
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(miPosicion)
                            .zoom(14)
                            .build();
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    centrar = false;
                }
                if(marcador != null){
                    obtenerJson();
                }
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras){

            }
            @Override
            public void onProviderEnabled(String provider){

            }
            @Override
            public void onProviderDisabled(String provider){

            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    private void trazarRuta(JSONObject jso) {
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;
        polyline2 = new ArrayList<Polyline>();
        try{
            jRoutes = jso.getJSONArray("routes");
            for(int i=0; i<jRoutes.length(); i++){
                jLegs = ((JSONObject) (jRoutes.get(i))).getJSONArray("legs");
                for(int j=0; j<jLegs.length(); j++){
                    String distancia = ""+((JSONObject) ((JSONObject)jLegs.get(0)).get("distance")).get("text");
                    String time = ""+((JSONObject) ((JSONObject)jLegs.get(0)).get("duration")).get("text");
                    int dist = (int) ((JSONObject) ((JSONObject)jLegs.get(0)).get("distance")).get("value");
                    Distancialbl.setText("Distancia: " + distancia);
                    tiempolbl.setText("Tiempo aproximado: " + time);

                    notificar(dist);

                    jSteps = ((JSONObject)jLegs.get(j)).getJSONArray("steps");
                    for(int k = 0; k<jSteps.length(); k++){
                        String polyline = ""+((JSONObject) ((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        List<LatLng> list = PolyUtil.decode(polyline);
                        if(mode.equals("walking")){
                            polyline2.add(map.addPolyline(new PolylineOptions().addAll(list).color(Color.MAGENTA).width(5)));
                        }
                        if(mode.equals("driving")){
                            polyline2.add(map.addPolyline(new PolylineOptions().addAll(list).color(Color.GREEN).width(5)));
                        }
                        if(mode.equals("bicycling")){
                            polyline2.add(map.addPolyline(new PolylineOptions().addAll(list).color(Color.BLUE).width(5)));
                        }
                    }
                }
            }
        }catch (JSONException e){

        }
    }

    public void obtenerJson(){
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+latitudOrigen+",%20"+longitudOrigen+"&destination="+latitudDestino+",%20"+longitudDestino+"&mode="+mode+"&key="+getString(R.string.api_maps);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(polyline2 != null){
                        for(Polyline line : polyline2)
                        {
                            line.remove();
                        }
                        polyline2.clear();
                        polyline2 = null;
                    }
                    if(polyline2 == null){
                        jso = new JSONObject(response);
                        trazarRuta(jso);
                    }
                    Log.i("jsonRuta", ""+response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);
    }

    public void notificar(int dist){
        if(notificarLlegada && notificarCerca){
            if(dist < 200){
                NotificationManager nm = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                Random random = new Random();
                int id = random.nextInt(8000);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), String.valueOf(id));
                if(dist < 50){
                    if(notificarLlegada){
                        builder.setAutoCancel(true)
                                .setWhen(System.currentTimeMillis())
                                .setContentTitle("Haz llegado")
                                .setTicker("Nueva notificacion")
                                .setSmallIcon(R.drawable.logo3)
                                .setContentText("Tu destino está aquí")
                                .setContentInfo("nuevo");

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                            NotificationChannel nc = new NotificationChannel(String.valueOf(id), "nuevo", NotificationManager.IMPORTANCE_HIGH);
                            nc.setDescription("Notification FCM");
                            nc.setShowBadge(true);
                            assert nm != null;
                            nm.createNotificationChannel(nc);
                        }
                        assert nm != null;
                        nm.notify(id, builder.build());
                    }
                    notificarLlegada = false;
                }else{
                    if(notificarCerca){
                        builder.setAutoCancel(true)
                                .setWhen(System.currentTimeMillis())
                                .setContentTitle("Ya casi!")
                                .setTicker("Nueva notificacion")
                                .setSmallIcon(R.drawable.logo3)
                                .setContentText("Tu destino está cerca")
                                .setContentInfo("nuevo");
                        notificarCerca = false;

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                            NotificationChannel nc = new NotificationChannel(String.valueOf(id), "nuevo", NotificationManager.IMPORTANCE_HIGH);
                            nc.setDescription("Notification FCM");
                            nc.setShowBadge(true);
                            assert nm != null;
                            nm.createNotificationChannel(nc);
                        }
                        assert nm != null;
                        nm.notify(id, builder.build());
                    }
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                }else{

                }
                return;
        }
    }
}