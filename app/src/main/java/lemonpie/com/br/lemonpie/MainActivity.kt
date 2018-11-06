package lemonpie.com.br.lemonpie

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import lemonpie.com.br.lemonpie.db.AppDatabase
import lemonpie.com.br.lemonpie.db.utils.DatabaseInitMock
import java.util.*

class MainActivity : AppCompatActivity() {

    private var mDb: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        mDb = AppDatabase.getInMemoryDatabase(applicationContext)

        populateDb()

        fetchData()
    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        super.onDestroy()
    }

    private fun populateDb() {
        DatabaseInitMock.populateSync(mDb!!)
    }

    private fun fetchData() {
        // Note: this kind of logic should not be in an activity.
        val sb = StringBuilder()
        val youngUsers = mDb?.userModel()!!.findUsersYoungerThan(35)
        for (youngUser in youngUsers) {
            sb.append(
                String.format(
                    Locale.US,
                    "%s, %s (%d)\n", youngUser.lastName, youngUser.name, youngUser.age
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
