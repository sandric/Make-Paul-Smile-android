package com.example.sandric.mps.models;

import android.text.TextUtils;
import android.util.Log;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.sandric.mps.services.OpeningsService;
import com.example.sandric.mps.tables.OpeningModel;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sandric on 10.12.15.
 */
public class Opening implements Serializable{

    public String name;

    private ArrayList<String> moves;

    private ArrayList<String> annotations;

    public int startingMove;

    public String details;

    public int movesCount;

    public Opening(String name, ArrayList<String> moves, ArrayList<String> annotations, int startingMove, String details) {
        this.name = name;
        this.moves = moves;
        this.annotations = annotations;
        this.startingMove = startingMove;
        this.details = details;

        this.movesCount = this.moves.size();
    }


    public boolean isValid(Move move) {
        if (move.getNotation().equals(this.moves.get(move.number - 1)))
            return true;
        else
            return false;
    }

    public String[] getHint() {
        return Move.getCellPositionsFromNotation(this.moves.get(Board.CURRENT_MOVE_NUMBER - 1));
    }



    public static ArrayList<Opening> getOpeningsByGroupName (String groupName) {
        ArrayList<Opening> openings = new ArrayList<Opening>();

        for (OpeningModel openingModel : Opening.fetchOpeningsByGroup(groupName)) {
            openings.add(new Opening(
                    openingModel.name,
                    openingModel.movesArrayList(),
                    openingModel.annotationsArrayList(),
                    openingModel.startingMove,
                    openingModel.details));
        }

        return openings;
    }


    public static Opening getOpeningByName (String name) {

        OpeningModel openingModel = Opening.fetchOpeningByName(name);

        return new Opening(
                openingModel.name,
                openingModel.movesArrayList(),
                openingModel.annotationsArrayList(),
                openingModel.startingMove,
                openingModel.details);
    }





    public static OpeningModel fetchOpeningByName(String name) {
        return new Select()
                .from(OpeningModel.class)
                .where("Name = ?", name)
                .executeSingle();
    }


    public static List<OpeningModel> fetchOpenings() {
        return new Select()
                .from(OpeningModel.class)
                .execute();
    }

    public static List<OpeningModel> fetchOpeningsByGroup(String groupname) {
        return new Select()
                .from(OpeningModel.class)
                .where("GroupName = ?", groupname)
                .execute();
    }



    public static void getOpeningsFromRequest () {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://10.0.3.2:8080")
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .excludeFieldsWithoutExposeAnnotation()
                                .create()
                ))
                .build();

        OpeningsService service = retrofit.create(OpeningsService.class);

        Call<List<OpeningModel>> call = service.getOpenings();
        call.enqueue(new Callback<List<OpeningModel>>() {
            @Override
            public void onResponse(Call<List<OpeningModel>> call, Response<List<OpeningModel>> response) {

                new Delete().from(OpeningModel.class).execute();

                for (OpeningModel openingModel : response.body()) {
                    openingModel.annotationsString = TextUtils.join(";", openingModel.annotations);
                    openingModel.movesString = TextUtils.join(";", openingModel.moves);
                    openingModel.save();
                }
            }

            @Override
            public void onFailure(Call<List<OpeningModel>> call, Throwable t) {
                Log.d("MYTAG", "NOOOOO");
            }
        });
    }



    public String toString() {
        return this.name;
    }
}