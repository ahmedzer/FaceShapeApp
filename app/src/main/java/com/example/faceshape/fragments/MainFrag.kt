package com.example.faceshape.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.faceshape.Adapters.GridAdapter
import com.example.faceshape.Adapters.SliderAdapter
import com.example.faceshape.Entities.ItemSlide
import com.example.faceshape.R
import com.example.faceshape.utils.OnItemClickListener
import com.example.faceshape.utils.ZommCenterLayoutManager

class MainFrag : Fragment(),OnItemClickListener {

    lateinit var viewPager:ViewPager2
    lateinit var imgV1:ImageView
    lateinit var imgV2:ImageView
    lateinit var imgV3:ImageView
    lateinit var gridRV : RecyclerView
     var YOUR_REQUEST_CODE=1

    lateinit var VPAdapter : SliderAdapter
    private var currentPage = 0
    private val DELAY_MS: Long = 5000 // Delay between page changes (3 seconds in this example)
    private val PERIOD_MS: Long = 3000 // Time period for the task

    private val dialog by lazy {//dialog pour selectioner l'image
        Dialog(requireContext()).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.img_select)
            setCancelable(true)
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }

    private val handler = Handler()
    private val runnable = object : Runnable {
        override fun run() {
            if (currentPage == 2) {
                currentPage = 0
            } else {
                currentPage++
            }
            viewPager.setCurrentItem(currentPage, true)
            handler.postDelayed(this, DELAY_MS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager2)
        imgV1 = requireActivity().findViewById<ImageView>(R.id.img1)
        imgV2 = requireActivity().findViewById(R.id.img2)
        imgV3 = requireActivity().findViewById(R.id.img3)
        gridRV = requireActivity().findViewById(R.id.gridRV)



        val slideList = ArrayList<ItemSlide>()
        val sld1 = ItemSlide(R.drawable.sld1,"Discover Your Perfect Glasses: Match Them to Your Face Shape")
        val sld2 = ItemSlide(R.drawable.sld2,"Unlock Your Best Hairstyle: Matching Haircuts to Your Face Shape")
        val sld3 = ItemSlide(R.drawable.sld3,"Discover Your Face Shape with AI: Effortless Self-Analysis for Personalized Style")

        slideList.add(sld1)
        slideList.add(sld2)
        slideList.add(sld3)

        VPAdapter = SliderAdapter(requireContext(),slideList)
        viewPager.adapter = VPAdapter
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                changeIndicator()
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                changeIndicator()
            }

        })

        val gridItemList = ArrayList<ItemSlide>()
        gridItemList.add(ItemSlide(R.drawable.itm1,"Best glasses for you"))
        gridItemList.add(ItemSlide(R.drawable.itm2,"Best hat for you"))
        gridItemList.add(ItemSlide(R.drawable.itm3,"Best hairstyle for you"))
        gridItemList.add(ItemSlide(R.drawable.itm4,"Find your face shape"))


        val gridAdapter = GridAdapter(requireContext(),gridItemList,this)
        gridRV.adapter = gridAdapter

        gridRV.layoutManager = ZommCenterLayoutManager(requireContext())





        /*if(!Python.isStarted()) {
            Python.start(AndroidPlatform(requireContext()))
        }
        try {
            val py = Python.getInstance()
            val PyImgToDf = py.getModule("ImgToDf")
            val imageResourceId: Int = R.drawable.sqr
            val imagePath: String = copyImageResourceToFile(requireContext(), imageResourceId)
            val res = PyImgToDf.callAttr("make_face_df",imagePath)
            Toast.makeText(requireContext(),res.toString(), Toast.LENGTH_LONG).show()
        }
        catch (e:java.lang.Exception){
            Toast.makeText(requireContext(),e.message, Toast.LENGTH_SHORT).show()
        }*/
    }
    private fun copyImageResourceToFile(context: Context, resourceId: Int): String {
        val outputStream = context.openFileOutput("temp_image.jpg", Context.MODE_PRIVATE)
        val inputStream = context.resources.openRawResource(resourceId)
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }
        inputStream.close()
        outputStream.close()
        return context.getFileStreamPath("temp_image.jpg")?.absolutePath ?: ""
    }

    fun changeIndicator() {
        when(viewPager.currentItem) {
            0->{
                imgV1.setBackgroundColor(requireActivity().resources.getColor(R.color.pinky))
                imgV2.setBackgroundColor(requireActivity().resources.getColor(R.color.gray))
                imgV3.setBackgroundColor(requireActivity().resources.getColor(R.color.gray))
            }
            1->{
                imgV2.setBackgroundColor(requireActivity().resources.getColor(R.color.pinky))
                imgV1.setBackgroundColor(requireActivity().resources.getColor(R.color.gray))
                imgV3.setBackgroundColor(requireActivity().resources.getColor(R.color.gray))
            }
            2->{
                imgV3.setBackgroundColor(requireActivity().resources.getColor(R.color.pinky))
                imgV1.setBackgroundColor(requireActivity().resources.getColor(R.color.gray))
                imgV2.setBackgroundColor(requireActivity().resources.getColor(R.color.gray))
            }
        }
    }

    override fun onItemClick(position: Int) {
        when(position) {
            3-> {
                dialog.show()
                val okBtn = dialog.findViewById<Button>(R.id.button2)
                okBtn.setOnClickListener {
                    openGallery()
                    dialog.dismiss()
                }
            }
        }
    }

    private fun openGallery() {
        // Code to open the gallery and handle image selection
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, YOUR_REQUEST_CODE)

    }

    // Handle the image selection result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == YOUR_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            val selectedImagePath = getImagePathFromUri(selectedImageUri!!)
            val args = Bundle()
            args.putString("imgPath",selectedImagePath)
            findNavController().navigate(R.id.action_mainFrag_to_faceShapeFrag,args)
            /**/


        }
    }
    override fun onResume() {
        super.onResume()
        // Start the auto-sliding when your activity is resumed
        handler.postDelayed(runnable, DELAY_MS)
    }

    override fun onPause() {
        super.onPause()
        // Stop the auto-sliding when your activity is paused
        handler.removeCallbacks(runnable)
    }

    private fun getImagePathFromUri(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = activity?.contentResolver?.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            return it.getString(columnIndex)
        }
        return null
    }
}
