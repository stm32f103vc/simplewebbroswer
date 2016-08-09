package example.floatactionbuttonmenu;

import android.graphics.Color;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.SeekBar;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

public class MainActivity extends AppCompatActivity {
    private boolean enable,showbuttons,show_home;
    FloatingActionsMenu fm;
    WebView w;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        enable = false;
        show_home = false;
        fm = (FloatingActionsMenu)findViewById(R.id.fa_menu);
        w = (WebView)findViewById(R.id.webView);
        w.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedHttpAuthRequest(WebView view, final HttpAuthHandler handler,
                                                  String host, String realm) {
            //    super.onReceivedHttpAuthRequest(view, handler, host, realm);
                view.setHttpAuthUsernamePassword(host, realm, "admin1024", "hengda");
                handler.proceed("admin1024", "hengda");
            }
        });
        w.setBackgroundColor(Color.WHITE);
        w.getSettings().setJavaScriptEnabled(true); //放在w.setWebViewClient(new WebViewClient());之前引起app崩溃
        et = (EditText)findViewById(R.id.editText);
        //this.setContentView(w); 引起app崩溃

        final FloatingActionButton backAction = (FloatingActionButton) findViewById(R.id.button_back);
        backAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                w.goBack();
            }
        });

        final FloatingActionButton forwardAction = (FloatingActionButton) findViewById(R.id.button_forward);
        forwardAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                w.goForward();
            }
        });

        final FloatingActionButton enableAction = (FloatingActionButton) findViewById(R.id.btn_d_and_n);
        enableAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                enable = !enable;
                if(enable) {
                    w.setBackgroundColor(Color.WHITE);
                }
                else {
                    w.setBackgroundColor(Color.BLUE);
                }
                //w.goBack();
            }
        });

        final FloatingActionButton initAction = (FloatingActionButton) findViewById(R.id.action_load);
        initAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                w.loadUrl(et.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        showbuttons = true;
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
            if(showbuttons)
                fm.setVisibility(100);
            else
                fm.setVisibility(1);
            showbuttons =!showbuttons;
            return true;
        }

        if(id == R.id.action_home)
        {
            if(show_home)
                et.setVisibility(0);
            else
                et.setVisibility(100);

            show_home = !show_home;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && w.canGoBack()) {
            w.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }
}
