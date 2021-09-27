package com.example.donationproject

import android.os.Bundle
import android.view.Gravity
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.donationproject.databinding.ActivityDonorBinding
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_donor.*
import kotlinx.android.synthetic.main.app_bar_donor.*
import kotlinx.android.synthetic.main.content_donor.*

class DonorActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private var mDbAdapter:MyDBAdapter? = null
    private val mAllBloodGroups = arrayOf("Select your blood group:","A+","A-","B+","B-","AB+","AB-","O+","O-")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_donor)
        initializeviews()
        initializeDatabase()
        loadList()

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer((GravityCompat.START))
        }else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.donor,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_settings -> return true
            else ->return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
    private fun initializeviews(){
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this,
        drawer_layout,
        toolbar,
        R.string.navigation_drawer_open,
        R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        faculties_spinner.adapter = ArrayAdapter(this@DonorActivity,
            android.R.layout.simple_list_item_1,
        mAllBloodGroups)
        adddonor.setOnClickListener {
            mDbAdapter?.insertDonors(hospital.text.toString(),
                faculties_spinner.selectedItemPosition + 1)
            loadList()
        }
        deletedonors.setOnClickListener() {
            mDbAdapter?.deletealldonors()
            loadList()
        }
    }
    private fun initializeDatabase(){
        mDbAdapter = MyDBAdapter(this@DonorActivity)
        mDbAdapter?.open()

    }
    private fun loadList(){
        val allDonors: List<String> = mDbAdapter?.selectalldonors()!!
        val adapter = ArrayAdapter(
            this@DonorActivity,
            android.R.layout.simple_list_item_1,
            allDonors)
        donorlist.adapter = adapter
    }
}