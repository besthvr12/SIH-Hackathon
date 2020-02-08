package com.example.trafficvision.ui.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.trafficvision.R;

import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class HomeFragment extends Fragment {

    protected HomeViewModel homeViewModel;
    Context context = getContext();
    TextView textView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });






        // -------------------------------------- Checking network
        ConnectivityManager check = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] info = check.getAllNetworkInfo();

        for (int i = 0; i<info.length; i++){
            if (info[i].getState() == NetworkInfo.State.CONNECTED){

                Toast.makeText(getContext() , "Internet Connected" , Toast.LENGTH_SHORT).show();
            }
        }
        //----------------------------------------

        //---------------------------------------- Network Tasks
        if(homeViewModel.getCounter().getValue().intValue() == 0) {
            String link = "http://harshverma18101.pythonanywhere.com/?format=json";
            new GetTraffic(context, link).execute(link);
        }


        //-----------------------------------------




        return root;
    }


    class GetTraffic extends AsyncTask<String , String , String> {

        private Context mContext;
        private String link ;


        public  GetTraffic(Context context , String mLink){
            mContext = context;
            link = mLink;
        }

        protected String doInBackground(String... urls) {


            StringBuffer buffer = new StringBuffer();
            String line = "";

            try {

                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));


                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);

                }



            }catch(Exception e){
                Log.i("Inside network", "onCreateView: Error occurred" + e);
            }

            publishProgress(buffer.toString());

            return buffer.toString();
            //update ui after asynctask
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Log.i("executing", values[0]);

            homeViewModel.setCounter(1);
            String level = "";


            try{
            JSONObject reader = new JSONObject(values[0]);
            level = reader.getString("languages");

            }catch (Exception e){
                Log.e("JsonError", "onProgressUpdate: Couldnt get json" );
            }
            String str = "You are entering" + level + "traffic area";
            homeViewModel.setText(str);


        }

        protected void onPostExecute(String result) {

            Log.i("executing", result);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new GetTraffic(context,link).execute(link);
                }
            }, 3000);

        }
    }
}

