package com.main.junaidstore.ParcelConverter;

import android.os.Parcel;

import com.main.junaidstore.models.Categories;
import com.main.junaidstore.models.Posts;
import com.main.junaidstore.models.Response;

import org.parceler.ParcelConverter;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muazzam on 4/17/2017.
 */

public class ParcelPostsConverter  implements ParcelConverter<List<Posts>> {


    @Override
    public void toParcel(List<Posts> input, Parcel parcel) {
        if (input == null) {
            parcel.writeInt(-1);
        }
        else {
            parcel.writeInt(input.size());
            for (Posts item : input) {
                parcel.writeParcelable(Parcels.wrap(item), 0);
            }
        }
    }

    @Override
    public List<Posts> fromParcel(Parcel parcel) {
        int size = parcel.readInt();
        if (size < 0) return null;
        List<Posts> items = new ArrayList<Posts>();
        for (int i = 0; i < size; ++i) {
            items.add((Posts) Parcels.unwrap(parcel.readParcelable(Posts.class.getClassLoader())));
        }
        return items;
    }
}
