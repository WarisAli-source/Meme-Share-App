package com.waris.memeshareapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.BaseRequestOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.waris.memeshareapp.R.id.memeImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var currentImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    private fun loadMeme(){

        progressBar.visibility = View.VISIBLE


        val queue = Volley.newRequestQueue(this)
        var url = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(
                Method.GET, url, null,
                {
                    currentImageUrl = it.getString("url")
                    Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                                e: GlideException?, model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                        ): Boolean {
                              progressBar.visibility=View.GONE
                            return false
                        }

                        override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?, target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }
                    }).into(memeImageView)

                },
                {
                    Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_LONG).show()
                })


        queue.add(jsonObjectRequest)

    }




    fun shareMeme(view: View) {
    val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, Checkout this cool meme I got from reddit $currentImageUrl")
        val chooser = Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)
    }
    fun nextClick(view: View) {
    loadMeme()
    }
}



