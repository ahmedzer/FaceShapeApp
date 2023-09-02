package com.example.faceshape.fragments

import android.app.Dialog
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.faceshape.R
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


@OptIn(DelicateCoroutinesApi::class)
class FaceShapeFrag : Fragment() {
    lateinit var faceImage:ImageView

    private val dialog by lazy {//dialog de confimation de commande
        Dialog(requireContext()).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.scan_dialog)
            setCancelable(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_face_shape, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        faceImage = requireActivity().findViewById(R.id.imageView3)
        val predictBtn = requireActivity().findViewById<Button>(R.id.button)

        val faceShapeTxt = requireActivity().findViewById<TextView>(R.id.textView4)

        val imgPath = arguments?.getString("imgPath")

        setImageViewWithImage(imgPath,faceImage)

        /**** Dialog set up ****************************/


        predictBtn.setOnClickListener {

            startAnimDialog()
            GlobalScope.launch(Dispatchers.IO) {
                if(!Python.isStarted()) {
                    Python.start(AndroidPlatform(requireContext()))
                }
                try {
                    val py = Python.getInstance()
                    val PyImgToDf = py.getModule("ImgToDf")
                    val res = PyImgToDf.callAttr("make_face_df",imgPath)
                    val predictModel = py.getModule("predict")
                    val assetFinderPath = context!!.filesDir!!.parentFile?.absolutePath + "/files/chaquopy/AssetFinder/app"
                    val faceShape = predictModel.callAttr("predict",assetFinderPath,res)
                    launch(Dispatchers.Main) {
                        val newImg = imgPath+"_NEW_rotated_pts.jpg"
                        setImageViewWithImage(newImg,faceImage)
                        faceShapeTxt.setText("Your face shape is : ${faceShape.toString()}")
                        Log.d("Error",faceShape.toString())
                        closeDialog()
                    }
                }
                catch (e:java.lang.Exception){
                    launch(Dispatchers.Main) {

                        Toast.makeText(requireContext(),e.message, Toast.LENGTH_LONG).show()
                        Log.d("Error2",e.stackTraceToString())
                        closeDialog()
                    }
                }
            }

        }
    }

    private fun setImageViewWithImage(imagePath: String?, imageView: ImageView) {
        val imageFile = File(imagePath!!)

        // Check if the file exists
        if (imageFile.exists()) {
            // Decode the image file into a Bitmap
            val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)

            // Set the Bitmap to the ImageView
            imageView.setImageBitmap(bitmap)
        }
        else {
            Log.d("ImgError","Not found")
        }
    }

    private fun startAnimDialog() {
        dialog.show()
        val img = dialog.findViewById<ImageView>(R.id.imageView4)
        val animation = AnimationUtils.loadAnimation(requireContext(), androidx.appcompat.R.anim.abc_fade_out)
        animation.duration = 2000
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                img.startAnimation(animation)
            }
        })
        img.startAnimation(animation)
    }
    private fun closeDialog() {
        dialog.dismiss()
    }

    private fun showFaceShapeImg(type:String) {

    }
}