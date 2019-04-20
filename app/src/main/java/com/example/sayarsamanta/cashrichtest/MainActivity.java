package com.example.sayarsamanta.cashrichtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> Number;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    RecyclerViewAdapter RecyclerViewHorizontalAdapter;
    LinearLayoutManager HorizontalLayout ;
    View ChildView ;
    RelativeLayout relativeLayout;
    int RecyclerViewItemPosition ;
    TextView textViewShareValue,textViewFixedVale,textViewdynamaicText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview1);
        textViewFixedVale=findViewById(R.id.fixedValue);
        textViewShareValue=findViewById(R.id.shareValue);
        relativeLayout=findViewById(R.id.dynamicColor);
        textViewdynamaicText=findViewById(R.id.dynamaicText);

        updatingTitle();

        GetData service = RetrofitClient.getRetrofitInstance().create(GetData.class);
        Call<List<Model>> call = service.getAllData();

//Execute the request asynchronously//

        call.enqueue(new Callback<List<Model>>() {

            @Override

//Handle a successful response//

            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                loadDataList(response.body());
            }

            @Override

//Handle execution failures//

            public void onFailure(Call<List<Model>> call, Throwable throwable) {

//If the request fails, then display the following toast//

                Toast.makeText(MainActivity.this, "Unable to load users", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updatingTitle(){
        final String[] array = {getString(R.string.dynamic_sip),getString(R.string.earn_more_than)};
        textViewdynamaicText.post(new Runnable() {
            int i = 0;
            @Override
            public void run() {
                textViewdynamaicText.setText(array[i]);
                i++;
                if (i ==2)
                    i = 0;
                textViewdynamaicText.postDelayed(this, 5000);
            }
        });
    }


    private void loadDataList(List<Model> usersList) {

//Get a reference to the RecyclerView//
        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(usersList);

//Use a LinearLayoutManager with default vertical orientation//

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

//Set the Adapter to the RecyclerView//

        recyclerView.setAdapter(RecyclerViewHorizontalAdapter);
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyView> {
        int selected_position=-1;
        private List<Model> list;

        public class MyView extends RecyclerView.ViewHolder {

            public TextView textView;
            public CardView cardView;
            public MyView(View view) {
                super(view);

                textView = (TextView) view.findViewById(R.id.textview1);
                cardView=view.findViewById(R.id.cardview);

            }
        }


        public RecyclerViewAdapter(List<Model> horizontalList) {
            this.list = horizontalList;
        }

        @Override
        public MyView onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item, parent, false);

            return new MyView(itemView);
        }

        @Override
        public void onBindViewHolder(final MyView holder, final int position) {

            holder.textView.setText(list.get(position).getDate());

            if (position == selected_position) {
                holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.white));
                holder.textView.setTextColor(getResources().getColor(R.color.faveo));
            } else {
                holder.cardView.setCardBackgroundColor(getResources().getColor(R.color.faveo));
                holder.textView.setTextColor(getResources().getColor(R.color.white));
            }
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selected_position = position;
                    notifyDataSetChanged();
                    textViewShareValue.setText(list.get(position).getQuity()+"%");
                    int fixedValue=100-Integer.parseInt(list.get(position).getQuity());
                    if (fixedValue<50){
                        relativeLayout.setBackgroundColor(getResources().getColor(R.color.yellow));
                    }
                    else{
                        relativeLayout.setBackgroundColor(getResources().getColor(R.color.layout_background));
                    }
                    textViewFixedVale.setText(fixedValue+"%");

                    notifyDataSetChanged();

                }
            });
            }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }
}
