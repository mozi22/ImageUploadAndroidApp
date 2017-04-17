package com.main.junaidstore.ParcelConverter;

import android.os.Parcel;

import com.main.junaidstore.models.Posts;
import com.main.junaidstore.models.Response;

import org.parceler.ParcelConverter;
import org.parceler.Parcels;

/**
 * Created by Muazzam on 4/17/2017.
 */

public class ParcelPostsConverter  implements ParcelConverter<Posts> {

    @Override
    public void toParcel(Posts input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Posts fromParcel(Parcel parcel) {
        return (Posts) Parcels.unwrap(parcel.readParcelable(Response.class.getClassLoader()));
    }
}
