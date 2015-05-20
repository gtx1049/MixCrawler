package com.gtx.model;

import android.content.Context;
import android.widget.Toast;

import java.io.FileOutputStream;

/**
 * Created by Administrator on 2015/5/19.
 */
public class BaseFilter
{
    protected Context context;

    public BaseFilter(Context context)
    {
        this.context = context;
    }

    public Entry getEntry(String url)
    {
        return null;
    };

    protected void writeToFile(String filename, byte[] filecontent)
    {
        try{

            FileOutputStream fout = context.openFileOutput(filename, context.MODE_PRIVATE);

            fout.write(filecontent);

            fout.close();

            Toast.makeText(context, "Write!", Toast.LENGTH_SHORT);

        }

        catch(Exception e){

            e.printStackTrace();

        }
    }
}
