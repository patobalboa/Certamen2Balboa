/*
  Autor: Patricio Balboa
  Rut: 17.592.019-6
 */

package cl.codelab.certamen2balboa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mcontext;

    public CustomInfoWindowAdapter(Context mcontext) {
        this.mcontext = mcontext;
        mWindow = LayoutInflater.from(mcontext).inflate(R.layout.custom_info_window,null);
    }

    private void rendowWindowText(Marker marker, View view){
        String title = marker.getTitle();
        TextView inTitle = (TextView) view.findViewById(R.id.snipTitle);
        assert title != null;
        if(!title.equals("")){
            inTitle.setText(title);
        }
        String snippet = marker.getSnippet();
        TextView inSnippet = (TextView) view.findViewById(R.id.snipSnippet);
        assert snippet != null;
        if(!snippet.equals("")){
            inSnippet.setText(snippet);
        }


    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
}
