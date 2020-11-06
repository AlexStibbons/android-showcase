package com.alexstibbons.showcase

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
        val chooserIntent: Intent = Intent.createChooser(webIntent, "Open with")

        if (webIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(chooserIntent)
        } else {
            Toast.makeText(context.applicationContext, "Unable to open the link", Toast.LENGTH_SHORT).show()
        }
    }
}