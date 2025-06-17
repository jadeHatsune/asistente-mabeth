package com.tonala.mabeth.ui.Calendario;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.tonala.mabeth.R;

import org.w3c.dom.Text;

import java.util.List;
@RequiresApi(api = Build.VERSION_CODES.O)
public class EventAdapter extends ArrayAdapter<Event>
{

    private TextView eventTitle, eventHora;

    public EventAdapter(@NonNull Context context, List<Event> events)
    {
        super(context, 0, events);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Event event = getItem(position);

        if (convertView == null ){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.evento_celda, parent, false);

            TextView eventTitlexd = convertView.findViewById(R.id.eventTitle);
            TextView eventHoraxd = convertView.findViewById(R.id.eventHora);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), EventoEditActivity2.class);
                    intent.putExtra( "ID", event.getId());
                    getContext().startActivity(intent);
                }
            });

            String eventTitle = event.getName();
            String eventHora = CalendarioUtilidades.formattedTime(event.getTime());
            eventTitlexd.setText(eventTitle);
            eventHoraxd.setText(eventHora);
            return convertView;
        }
        return convertView;
    }
}
