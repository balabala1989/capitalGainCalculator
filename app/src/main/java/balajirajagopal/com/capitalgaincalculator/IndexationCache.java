package balajirajagopal.com.capitalgaincalculator;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public final class IndexationCache {

    private static HashMap<String, Long> indexationValues = new HashMap<String, Long>();

    public static Long get(String indexedYear, Context context){
        Long indexValue = indexationValues.get(indexedYear);

        if(indexValue == null){
            InputStream inputStream = context.getResources().openRawResource(R.raw.indexation);

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            String[] splitting;

            try {
                while ((line = bufferedReader.readLine()) != null) {
                    splitting = line.split(",");
                    indexationValues.put(splitting[0],Long.parseLong(splitting[1]));
                }
                indexValue = indexationValues.get(indexedYear);
            }
            catch (IOException exception){
                exception.printStackTrace();
                return 1L;
            }
        }
        return indexValue == null ? 1L : indexValue;
    }
}
