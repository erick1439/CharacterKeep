package com.example.cop4331;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Edit extends AppCompatActivity
{
    // variables to do the updating process.
    EditText ws, bs, s, t, ag, inte, wp, fel, a, w, sb, tb, m, mag, ip, fpp, mx, current;
    String nameEdit, linkingId, classEdit, raceEdit;
    List<Update> dataArray;
    TextView name;

    private static final String TAG = "GalleryActivity";
    private static String URL_STATS = "http://www.cop4331.org/LAMPAPI/getStatsApp.php";
    private static String URL_UPDATE = "http://www.cop4331.org/LAMPAPI/updateApp.php";

    // Listviews to display the properties of a character.
    ListView skillsListview;
    ListView talentsListview;
    ListView trapsListview;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getIncomingIntent();

        String [] skills = Skills.getSkills(raceEdit, classEdit);
        String [] talents = Talents.getTalents(raceEdit, classEdit);
        String [] traps = Traps.getTraps(raceEdit, classEdit);

        // Adapters to populate listviews in the "activity_edit" page.
        trapsListview = (ListView)findViewById(R.id.itemsListview);
        trapsListview.setAdapter(new ArrayAdapter<String>(this, R.layout.my_listview_properties, R.id.nameEditList, traps));

        talentsListview = (ListView)findViewById(R.id.talentsListview);
        talentsListview.setAdapter(new ArrayAdapter<String>(this, R.layout.my_listview_properties, R.id.nameEditList, talents));

        skillsListview = (ListView)findViewById(R.id.skillsListview);
        skillsListview.setAdapter(new ArrayAdapter<String>(this, R.layout.my_listview_properties, R.id.nameEditList, skills));

        final String [] infoForUpdating = getStats();

        hideNavigationBar();

        // Creating instance of a button to cancel or go back to the menu page.
        ImageView goBackBtnEdit = (ImageView)findViewById(R.id.goBackBtnEdit);
        goBackBtnEdit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Intent startIntent = new Intent(getApplicationContext(), Menu.class);
                startActivity(startIntent);

            }
        });

        // Saves the changes made by the user
        Button save = (Button) findViewById(R.id.saveBtn);
        save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                if (infoForUpdating[0].equals(ws.getText().toString()) && infoForUpdating[1].equals(bs.getText().toString()) && infoForUpdating[2].equals(s.getText().toString()) && infoForUpdating[3].equals(t.getText().toString()) && infoForUpdating[4].equals(ag.getText().toString()) && infoForUpdating[5].equals(inte.getText().toString()) && infoForUpdating[6].equals(wp.getText().toString()) && infoForUpdating[7].equals(fel.getText().toString()) && infoForUpdating[8].equals(a.getText().toString()) && infoForUpdating[9].equals(w.getText().toString()) && infoForUpdating[10].equals(sb.getText().toString()) && infoForUpdating[11].equals(tb.getText().toString()) && infoForUpdating[12].equals(m.getText().toString())&& infoForUpdating[13].equals(mag.getText().toString()) && infoForUpdating[14].equals(ip.getText().toString()) && infoForUpdating[15].equals(fpp.getText().toString()) && infoForUpdating[16].equals(mx.getText().toString()) && infoForUpdating[17].equals(current.getText().toString()))
                {
                    Toast toast = Toast.makeText(Edit.this, "You haven't done any changes", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.LEFT | Gravity.BOTTOM,10,20);
                    toast.show();
                }
                else
                    updating();
            }
        });


        // Series of click events that request a upgrade of account.
        TextView nameClick = (TextView) findViewById(R.id.nameEditText);
        nameClick.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Toast toast = Toast.makeText(Edit.this, "Upgrade to change this field", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.LEFT | Gravity.BOTTOM,10,20);
                toast.show();
            }
        });

        TextView classClick = (TextView) findViewById(R.id.classEditText);
        classClick.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Toast toast = Toast.makeText(Edit.this, "Upgrade to change this field", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.LEFT | Gravity.BOTTOM,10,20);
                toast.show();
            }
        });

        TextView raceClick = (TextView) findViewById(R.id.raceEditText);
        raceClick.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                Toast toast = Toast.makeText(Edit.this, "Upgrade to change this field", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.LEFT | Gravity.BOTTOM,10,20);
                toast.show();
            }
        });

//        TextView maxClick = (TextView) findViewById(R.id.maxEditText);
//        maxClick.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v)
//            {
//                Toast toast = Toast.makeText(Edit.this, "Upgrade to change this field", Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.LEFT | Gravity.BOTTOM,10,20);
//                toast.show();
//            }
//        });
//
//        TextView currentClick = (TextView) findViewById(R.id.currentEditText);
//        currentClick.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v)
//            {
//                Toast toast = Toast.makeText(Edit.this, "Upgrade to change this field", Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.LEFT | Gravity.BOTTOM,10,20);
//                toast.show();
//            }
//        });
    }

    // Receiving and the info that is going to be display on the edit activity
    private void getIncomingIntent()
    {
        Log.d(TAG, "getIncomingIntent: checking for incoming intent");

        if (getIntent().hasExtra("nameEdit") && getIntent().hasExtra("classEdit") && getIntent().hasExtra("raceEdit") && getIntent().hasExtra("maxEdit") && getIntent().hasExtra("currentEdit") && getIntent().hasExtra("linkingId"))
        {
            Log.d(TAG, "getIncomingIntent: found intent extras");

            linkingId = getIntent().getStringExtra("linkingId");
            nameEdit = getIntent().getStringExtra("nameEdit");
            classEdit = getIntent().getStringExtra("classEdit");
            raceEdit = getIntent().getStringExtra("raceEdit");
            String maxEdit = getIntent().getStringExtra("maxEdit");
            String currentEdit = getIntent().getStringExtra("currentEdit");

            setInfo(nameEdit, classEdit, raceEdit, maxEdit, currentEdit);
        }
    }

    // Sets the info in the corresponding text box.
    private void setInfo(String nameEdit, String classEdit, String raceEdit, String maxEdit, String currentEdit)
    {
        Log.d(TAG, "setImage: setting the info to the widgets");

        name = (TextView)findViewById(R.id.nameEditText);
        name.setText(nameEdit);

        TextView classE = (TextView)findViewById(R.id.classEditText);
        classE.setText(classEdit);

        TextView race = (TextView)findViewById(R.id.raceEditText);
        race.setText(raceEdit);

//        TextView max = (TextView)findViewById(R.id.maxEditText);
//        max.setText(maxEdit);
//
//        TextView current = (TextView)findViewById(R.id.currentEditText);
//        current.setText(currentEdit);
    }

    // Acquiring the information from the database to display on the activity.
    public String[] getStats()
    {
        final String [] statsDisplay = new String [18];

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_STATS, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONArray jsonArray = new JSONArray(response);

                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    statsDisplay[17] = jsonObject.getString("hpCurrent");
                    statsDisplay[16] = jsonObject.getString("hpMax");
                    statsDisplay[0] = jsonObject.getString("WS");
                    statsDisplay[1] =jsonObject.getString("BS");
                    statsDisplay[2] =jsonObject.getString("S");
                    statsDisplay[3] =jsonObject.getString("T");
                    statsDisplay[4] =jsonObject.getString("AG");
                    statsDisplay[5] =jsonObject.getString("INTE");
                    statsDisplay[6] =jsonObject.getString("WP");
                    statsDisplay[7] =jsonObject.getString("FEL");
                    statsDisplay[8] =jsonObject.getString("A");
                    statsDisplay[9] =jsonObject.getString("W");
                    statsDisplay[10] =jsonObject.getString("SB");
                    statsDisplay[11] =jsonObject.getString("TB");
                    statsDisplay[12] =jsonObject.getString("M");
                    statsDisplay[13] =jsonObject.getString("MAG");
                    statsDisplay[14] =jsonObject.getString("IP");
                    statsDisplay[15] =jsonObject.getString("FPP");


                    setStats(statsDisplay);

                }catch(JSONException e)
                {
                    Toast.makeText(Edit.this, "Error getting stats info: " + e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(Edit.this, "Error getting stats info!: " + error.toString(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String, String> params = new HashMap<>();
                params.put("userId", MainActivity.success);
                params.put("name", nameEdit);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        return statsDisplay;
    }

    // Sets information on activity from database
    public void setStats(String [] statsDisplay)
    {
        int count  = 0;

        for (int i = 0; i < statsDisplay.length; i++)
            if (statsDisplay[i] == null)
                count++;

            if (count != 0)
                Toast.makeText(Edit.this, "Error not all information received. " + count, Toast.LENGTH_LONG).show();

            else
            {
                ws = (EditText) findViewById(R.id.wsText);
                ws.setText(statsDisplay[0]);

                bs = (EditText) findViewById(R.id.bsText);
                bs.setText(statsDisplay[1]);

                s = (EditText) findViewById(R.id.sText);
                s.setText(statsDisplay[2]);

                t = (EditText) findViewById(R.id.tText);
                t.setText(statsDisplay[3]);

                ag = (EditText) findViewById(R.id.agText);
                ag.setText(statsDisplay[4]);

                inte = (EditText) findViewById(R.id.inteText);
                inte.setText(statsDisplay[5]);

                wp = (EditText) findViewById(R.id.wpText);
                wp.setText(statsDisplay[6]);

                fel = (EditText) findViewById(R.id.felText);
                fel.setText(statsDisplay[7]);

                a = (EditText) findViewById(R.id.aText);
                a.setText(statsDisplay[8]);

                w = (EditText) findViewById(R.id.wText);
                w.setText(statsDisplay[9]);

                sb = (EditText) findViewById(R.id.sbText);
                sb.setText(statsDisplay[10]);

                tb = (EditText) findViewById(R.id.tbTecxt);
                tb.setText(statsDisplay[11]);

                m = (EditText) findViewById(R.id.mText);
                m.setText(statsDisplay[12]);

                mag = (EditText) findViewById(R.id.magText);
                mag.setText(statsDisplay[13]);

                ip = (EditText) findViewById(R.id.ipText);
                ip.setText(statsDisplay[14]);

                fpp = (EditText) findViewById(R.id.fppText);
                fpp.setText(statsDisplay[15]);

                mx = (EditText) findViewById(R.id.maxEditText);
                mx.setText(statsDisplay[16]);

                current = (EditText) findViewById(R.id.currentEditText);
                current.setText(statsDisplay[17]);
            }
    }

    // Method that contacts the database to do the update of a character information.
    private void updating()
    {
        dataArray = new ArrayList<Update>();
        dataArray.add(new Update(ws.getText().toString(), bs.getText().toString(), s.getText().toString(),
                t.getText().toString(), ag.getText().toString(), inte.getText().toString(), wp.getText().toString(),
                fel.getText().toString(), a.getText().toString(), w.getText().toString(), sb.getText().toString(),
                tb.getText().toString(), m.getText().toString(), mag.getText().toString(), ip.getText().toString(), fpp.getText().toString(), mx.getText().toString(), current.getText().toString()));


        Gson gson = new Gson();

        final String newDataArray = gson.toJson(dataArray);

        StringRequest updateInfo = new StringRequest(Request.Method.POST, URL_UPDATE, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Toast toast = Toast.makeText(Edit.this, "Saved", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.LEFT | Gravity.BOTTOM,10,20);
                toast.show();
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast toast = Toast.makeText(Edit.this, "Error Updating cards!: " + error.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.LEFT | Gravity.BOTTOM,0,0);
                toast.show();

                error.printStackTrace();

            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", MainActivity.success);
                params.put("name", name.getText().toString());
                params.put("array", newDataArray);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(updateInfo);
    }

    // It hides the navegation bar.
    private void hideNavigationBar()
    {
        this.getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );

    }
}
