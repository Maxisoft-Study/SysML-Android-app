package mica.maxime.mica_sysml;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.enums.SnackbarType;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity {
    @InjectView(R.id.recycler_view_left_drawer)
    RecyclerView recyclerViewLeft;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private MyAdapter adapter;

    @OnClick(R.id.button)
    void add() {
        adapter.addToList("test", adapter.getItemCount());
        Snackbar.with(getApplicationContext()) // context
                .type(SnackbarType.MULTI_LINE) // Set is as a multi-line snackbar
                .text("document added") // text to be displayed
                .duration(Snackbar.SnackbarDuration.LENGTH_LONG)
                .attachToRecyclerView(recyclerViewLeft)
                .show(this); // where it is displayed
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, EmbeddedJettyServer.class));
        ButterKnife.inject(this);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewLeft.setLayoutManager(layoutManager);

        adapter = new MyAdapter(new ArrayList<String>());
        recyclerViewLeft.setAdapter(adapter);
        recyclerViewLeft.setItemAnimator(new DefaultItemAnimator());



        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        Snackbar.with(getApplicationContext()) // context
                .text("U R WELCOME") // text to display
                .duration(3000)
                .show(this); // activity where it is displayed
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent setting = new Intent(this, SettingsActivity.class);
            startActivity(setting);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
