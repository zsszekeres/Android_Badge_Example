package com.example.badgetutorial

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlin.math.absoluteValue
import kotlin.math.min

class MainActivity : AppCompatActivity() {

    private var textCartItemCount: TextView? = null
    private var cartItemCount = 0

    private lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            cartItemCount++
            setupBadge()
        }

        findViewById<Button>(R.id.snackBarBtn).setOnClickListener {
            configSnackbarAndShowIt()
        }

    }

    @Suppress("DEPRECATION")
    private fun configSnackbarAndShowIt() {
        snackbar = Snackbar.make(findViewById(R.id.snackBarBtn), "BUY", Snackbar.LENGTH_LONG)
        snackbar.view.apply {
            val tv = findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            tv.apply {
                setTextColor(ContextCompat.getColor(this@MainActivity, android.R.color.black))
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
            setBackgroundColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    android.R.color.holo_red_light
                )
            )
            setOnClickListener {
                if (it.isShown) it.visibility = View.INVISIBLE
            }
        }
        snackbar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
            .also { Log.d("MENUONCREATE", "MENU IS Inflated : $menu") }


        val menuItem =
            menu.findItem(R.id.action_cart).also { Log.d("MENUONCREATE", "menuItem is : $it") }
        val actionView = menuItem.actionView.also { Log.d("MENUONCREATE", "actionView is : $it") }
        actionView?.run {
            textCartItemCount = this.findViewById(R.id.cart_badge) as TextView
        }

        setupBadge()

        actionView.setOnClickListener {
            onOptionsItemSelected(menuItem)
        }

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_cart -> {
                Log.d("onOptionsItemSelected", "called at: ${R.id.action_cart}")
                cartItemCount = 0
                setupBadge()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupBadge() {
        textCartItemCount?.run {
            if (cartItemCount == 0) {
                if (this.visibility != View.GONE)
                    this.visibility = View.GONE
            } else {
                this.text = min(cartItemCount, 99).absoluteValue.toString()
                if (this.visibility != View.VISIBLE)
                    this.visibility = View.VISIBLE
            }
        }
    }
}
