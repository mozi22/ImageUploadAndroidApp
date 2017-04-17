package com.main.junaidstore.ParcelConverter;

import android.os.Parcel;

import com.main.junaidstore.models.Posts;
import com.main.junaidstore.models.Response;
import com.main.junaidstore.models.Users;

import org.parceler.ParcelConverter;
import org.parceler.Parcels;

/**
 * Created by Muazzam on 4/17/2017.
 */

public class ParcelUsersConverter  implements ParcelConverter<Users> {
    @Override
    public void toParcel(Users input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Users fromParcel(Parcel parcel) {
        return (Users) Parcels.unwrap(parcel.readParcelable(Response.class.getClassLoader()));
    }
}
