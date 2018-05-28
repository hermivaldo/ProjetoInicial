package com.example.hermivaldo.projetoinicial.fragments


import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.hermivaldo.projetoinicial.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.BitmapDescriptorFactory


class MapFragment : Fragment(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private lateinit var mMap: GoogleMap
    private lateinit var mGoogleApiCliente: GoogleApiClient
    private val REQUEST_GPS: Int = 0

    override fun onConnected(p0: Bundle?) {
        try {
            checkPermission()

            val minhaLocalizacao = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiCliente)
            if (minhaLocalizacao != null) {
                adicionarMarcador(minhaLocalizacao.latitude, minhaLocalizacao.longitude, "Não sou Shakira, mas estou aqui")

            }
        }catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun adicionarMarcador(latitude: Double, longitude: Double, descrition: String){
        val sydney = LatLng(latitude, longitude)
        mMap.clear()
        mMap.addMarker(MarkerOptions()
                .position(sydney)
                .title(descrition)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_facebook)))

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15F))
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocationChanged(location: Location?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_map, container, false)

        var mapView = view.findViewById<MapView>(R.id.map)
        mapView.onCreate(savedInstanceState);

        mapView.onResume();
        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mapView.getMapAsync(this)
        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        try {
            callConnection()
        }catch (e: Exception){
            e.printStackTrace()
        }

    }



    @Synchronized fun callConnection (){
        mGoogleApiCliente = GoogleApiClient.Builder(context!!)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API).build()
        mGoogleApiCliente.connect()
    }


    private fun checkPermission() {
        try {
            val permission = ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)

            if (permission != PackageManager.PERMISSION_GRANTED) {
                Log.i("", "Permissão para gravar negada")

                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
                                Manifest.permission.ACCESS_FINE_LOCATION)) {

                    val builder = AlertDialog.Builder(context!!)

                    builder.setMessage("Necessária a permissao para GPS")
                            .setTitle("Permissao Requerida")

                    builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                        requestPermission()
                    })

                    val dialog = builder.create()
                    dialog.show()

                } else {
                    requestPermission()
                }
            }
        }catch (e: Exception) {
            e.printStackTrace()
        }

    }



    fun requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_GPS)
    }
}
