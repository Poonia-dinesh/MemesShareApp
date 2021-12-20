package com.example.MemesShare

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("RedundantSamConstructor")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeem()
    }

private fun loadmeem()
    {
        pgbar.visibility= View.VISIBLE
        next.isEnabled = false
        share.isEnabled = false
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener {
                              val url = it.getString("url")
                Glide.with(this).load(url).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pgbar.visibility= View.GONE
                        next.isEnabled = true
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pgbar.visibility= View.GONE
                        next.isEnabled = true
                        share.isEnabled = true
                        return false
                    }
                }).into(imageView)

            },
            Response.ErrorListener {


            })

       MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


    }

    fun NextButton(view: android.view.View) {
   loadmeem()
    }
    fun Share(view: android.view.View) {
  val intent = Intent(Intent.ACTION_SEND, Uri.parse("url"))
        intent.type= "text /plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hi Check out this new meme")
       startActivity(intent)

    }
}