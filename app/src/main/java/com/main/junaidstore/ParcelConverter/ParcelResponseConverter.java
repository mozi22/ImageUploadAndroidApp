package com.main.junaidstore.ParcelConverter;

import android.os.Parcel;

import com.main.junaidstore.models.Response;

import org.parceler.ParcelConverter;
import org.parceler.Parcels;

/**
 * Created by Muazzam on 4/17/2017.
 */

public class ParcelResponseConverter implements ParcelConverter<Response>{
    @Override
    public void toParcel(Response input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Response fromParcel(Parcel parcel) {
        return (Response) Parcels.unwrap(parcel.readParcelable(Response.class.getClassLoader()));
    }}
