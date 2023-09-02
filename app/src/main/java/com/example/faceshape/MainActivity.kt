package com.example.faceshape

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.faceshape.Adapters.GridAdapter
import com.example.faceshape.Adapters.SliderAdapter
import com.example.faceshape.Entities.ItemSlide
import com.example.faceshape.utils.ZommCenterLayoutManager


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        // Find the "Home" menu item by its ID and set it as checked
        val homeMenuItem: MenuItem? = menu!!.findItem(R.id.mainFrag)
        homeMenuItem?.isChecked = true
        return true
    }
}