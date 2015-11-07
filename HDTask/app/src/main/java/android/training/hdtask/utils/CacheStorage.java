package android.training.hdtask.utils;

/**
 * Created by priya on 11/5/15.
 */

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class CacheStorage {

    public static void writeObject(Context context, int key, Object object) throws IOException {
        FileOutputStream fos = context.openFileOutput(String.valueOf(key), Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();
    }

    public static Object readObject(Context context, int key) {
        try{
            FileInputStream fis = context.openFileInput(String.valueOf(key));
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object object = ois.readObject();
            if(object != null){
                return object;
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }
}

