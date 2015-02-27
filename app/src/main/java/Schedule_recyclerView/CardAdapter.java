package Schedule_recyclerView;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.exebit.exebit2k15.R;

import java.util.ArrayList;

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
        viewHolder.tvtitle.setText(events.getEventtitle());
        viewHolder.tvsch.setText(events.getEventsch());
        viewHolder.tvdesc.setText(events.getEventdesc());
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}