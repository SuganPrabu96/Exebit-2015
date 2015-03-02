package Schedule_recyclerView;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.exebit.exebit2k15.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by srikrishna on 07-02-2015.
 */
public class CardAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<ListOfEvents_Event> items;

    private Context context;

    public CardAdapter(ArrayList<ListOfEvents_Event> items, Context context) {
        this.items = items;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false);
        ViewHolder vH = new ViewHolder(context, v);
        return vH;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ListOfEvents_Event events = items.get(position);

        viewHolder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        String[] loc;
        loc = new String[]{"#EF5350","#2196F3","#FF5722","#AB47BC","#1B5E20"};
        Random r = new Random();
        int col = r.nextInt(5);
        String s = loc[col];
        viewHolder.back.setBackgroundColor(Color.parseColor(s));


        viewHolder.tvtitle.setText(events.getEventtitle());
        viewHolder.tvsch.setText(events.getEventsch());
        viewHolder.tvdesc.setText(events.getEventdesc());
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}