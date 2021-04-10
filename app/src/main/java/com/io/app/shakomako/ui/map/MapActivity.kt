package com.io.app.shakomako.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.io.app.shakomako.R
import com.io.app.shakomako.databinding.ActivityMapBinding
import com.io.app.shakomako.helper.callback.DataItemCallBack
import com.io.app.shakomako.helper.callback.ViewClickCallback
import com.io.app.shakomako.ui.base.DataBindingActivity
import com.io.app.shakomako.utils.constants.AppConstant
import java.util.*

class MapActivity : DataBindingActivity<ActivityMapBinding>(), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    OnMapClickListener, ViewClickCallback {

    lateinit var map: GoogleMap
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var latLngValue: LatLng

    override fun layoutRes(): Int {
        return R.layout.activity_map
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        dataBinding.viewHandler = this

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_location_showing) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val geocoder = Geocoder(this, Locale.getDefault())

        val locationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val latLng = LatLng(
                    locationResult.lastLocation.latitude,
                    locationResult.lastLocation.longitude
                )
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11f))
            }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        this.map = p0
        this.map.uiSettings.isZoomControlsEnabled = true
        this.map.setOnMyLocationButtonClickListener(this)
        this.map.setOnMapClickListener(this)

        enableCurrentLocation()
    }

    private fun enableCurrentLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkThePermission(
                resources.getString(R.string.msg_allow_us_location_permission),
                resources.getString(R.string.msg_permission_denied_not_able_detect_location),
                object : DataItemCallBack<Int, Int> {
                    @SuppressLint("MissingPermission")
                    fun onItemData(result: Int, integer2: Int) {

                    }

                    override fun onItemData(t: Int?, r: Int?) {
                        if (t == AppConstant.PERMISSION_ACCEPTED) {
                            getLastLocation()
                        }
                    }
                }, Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            getLastLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val latLng =
                        LatLng(location.latitude, location.longitude)
                    latLngValue = latLng
                    val markerOptions = MarkerOptions()
                    markerOptions.position(latLng)
                    markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude)
                    map.clear()
                    map.animateCamera(
                        CameraUpdateFactory.newCameraPosition(
                            CameraPosition.builder().target(latLng).zoom(15f).build()
                        )
                    )
                    map.addMarker(markerOptions)
                }
            }

    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

    override fun onMapClick(p0: LatLng) {
        latLngValue = p0
        val markerOptions = MarkerOptions()
        markerOptions.position(p0)

        markerOptions.title(p0.latitude.toString() + " : " + p0.longitude)
        map.clear()
        map.animateCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.builder().target(p0).zoom(15f).build()
            )
        )
        map.addMarker(markerOptions)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_select_location -> {
                val returnIntent = Intent()
                returnIntent.putExtra("result", "${latLngValue.latitude},${latLngValue.longitude}")
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
    }
}