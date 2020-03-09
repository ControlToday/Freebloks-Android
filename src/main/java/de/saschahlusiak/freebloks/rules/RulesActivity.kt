package de.saschahlusiak.freebloks.rules

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import de.saschahlusiak.freebloks.R
import de.saschahlusiak.freebloks.utils.analytics

class RulesActivity : Activity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rules_activity)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        findViewById<View>(R.id.youtube).setOnClickListener(this)

        analytics.logEvent("show_rules", null)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(v: View) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_LINK))
        startActivity(intent)
        analytics.logEvent("show_rules_video", null)
    }

    companion object {
        private const val YOUTUBE_LINK = "https://www.youtube.com/watch?v=pc8nmWpcQWs"
    }
}