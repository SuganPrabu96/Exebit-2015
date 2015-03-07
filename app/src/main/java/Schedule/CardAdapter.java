package Schedule;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.exebit.exebit2k15.R;
import com.example.exebit.exebit2k15.ViewHolder;

import com.example.exebit.exebit2k15.StickyHeaderScrollViewActivity;

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
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final ListOfEvents_Event events = items.get(position);


        viewHolder.itemView.findViewById(R.id.card_view2).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
//Pos starts from zero ; where zero is 1st card


                //Toast.makeText(context, "Item clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, StickyHeaderScrollViewActivity.class);
                intent.putExtra("pos",position );
                intent.putExtra("categ",events.getEventcateg());
                intent.putExtra("schedule",events.getEventsch());
                intent.putExtra("title",events.getEventtitle());
                intent.putExtra("description",events.getShortdesc());
                intent.putExtra("format",events.getEventformat());
                intent.putExtra("faq",events.getEventfaqfull());
                intent.putExtra("coord",events.getCoorddetails());



                context.startActivity(intent);
            }
        });






        viewHolder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        String[] loc;
        loc = new String[]{"#EF5350","#2196F3","#FF5722","#AB47BC","#1B5E20"};
        Random r = new Random();
        int col = r.nextInt(5);
        String s = loc[col];
        viewHolder.back.setBackgroundColor(Color.parseColor(s));


        viewHolder.tvtitle.setText(events.getEventtitle());
        viewHolder.tvcateg.setText(events.getEventcateg());
        viewHolder.tvsch.setText(events.getEventsch());
        viewHolder.tvdesc.setText(events.getEventdesc());
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}