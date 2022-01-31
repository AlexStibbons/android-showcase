package com.alexstibbons.showcase

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast


interface LinkResource {
    fun openInBrowser(url: String)
}

internal class LinkResourceImpl(
    private val context: Context
) : LinkResource {

    override fun openInBrowser(url: String) {

        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        try {
            context.startActivity(webIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context.applicationContext, "Unable to open the link", Toast.LENGTH_SHORT).show()
        }
    }
}