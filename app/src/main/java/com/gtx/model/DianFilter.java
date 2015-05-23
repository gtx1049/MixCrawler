package com.gtx.model;

import android.content.Context;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Toast;

import com.gtx.crawler.DianDig;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.HttpGet;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.CookiePolicy;

/**
 * Created by Administrator on 2015/5/22.
 */
public class DianFilter extends BaseFilter
{
    public static String TAG = "DianFilter";

    public DianFilter(Context context)
    {
        super(context);
    }

    @Override
    public Entry getEntry(final String url)
    {
        Toast.makeText(context, url, Toast.LENGTH_SHORT);
        //AsyncHttpClient client = new AsyncHttpClient();

        //I can't use AsyncHttpClient but I use the default httpclient
        //I don't know why, may something wrong with HttpParam?
        //8 parameter in Async, only 4 in httpclient

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    HttpGet getMethod = new HttpGet(url);
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse response = null;
                    response = httpClient.execute(getMethod);
                    Log.i(TAG, "resCode = " + response.getStatusLine().getStatusCode());
                    String htmlResponse = EntityUtils.toString(response.getEntity(), "utf-8");
                    writeToFile("dian.html", htmlResponse.getBytes());
                    parseForm(htmlResponse);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();

        /*client.get(url, new AsyncHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                Toast.makeText(context, "Success!", Toast.LENGTH_SHORT);

                //writeToFile(filename, responseBody);
                parseForm(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                Toast.makeText(context, "Failure!", Toast.LENGTH_SHORT);
                Log.d(TAG, new String(responseBody));
            }

            @Override
            public void onStart()
            {
                Toast.makeText(context, "Start!", Toast.LENGTH_SHORT);
                // called before request is started
            }
        });*/
        return null;
    }

    public Entry parseForm(String form)
    {
        Document doc = Jsoup.parse(form);

        Element priceElement = doc.select("div.buy-box").first();
        String price = priceElement.html();

        Element titleElement = doc.select("div.intro").first();
        String title = titleElement.select("h3").first().html();

        String description = titleElement.select("p").first().html();

        String address = doc.select("div.address").first().html();

        String urlpic = doc.select("div.info").first().select("img").first().attr("src");

        this.savePic(urlpic, urlpic.substring(urlpic.length() - 15, urlpic.length()));

        Log.d(TAG, "Price : " + price);
        Log.d(TAG, "Title : " + title);
        Log.d(TAG, "Description : " + description);
        Log.d(TAG, "Address : " + address);
        Log.d(TAG, "Pic : " + urlpic);

        return null;
    }
}
