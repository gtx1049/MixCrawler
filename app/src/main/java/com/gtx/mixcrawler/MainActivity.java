package com.gtx.mixcrawler;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.gtx.crawler.BaseDig;
import com.gtx.crawler.DianDig;
import com.gtx.crawler.MeiDig;


public class MainActivity extends ActionBarActivity
{
    private WebView wb;
    private Button bt;

    BaseDig bd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wb = (WebView)findViewById(R.id.webview);

        bd = new DianDig(wb);
        bd.load();

        bt = (Button)findViewById(R.id.get_content);

        bt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bd.saveContent(MainActivity.this);
            }
        });
    }

}
