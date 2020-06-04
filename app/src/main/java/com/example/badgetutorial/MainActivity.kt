package com.example.badgetutorial

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.*
import kotlin.math.absoluteValue
import kotlin.math.min

class MainActivity : AppCompatActivity() {

    private var textCartItemCount: TextView? = null
    private var cartItemCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            cartItemCount++
            setupBadge()
        }


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
