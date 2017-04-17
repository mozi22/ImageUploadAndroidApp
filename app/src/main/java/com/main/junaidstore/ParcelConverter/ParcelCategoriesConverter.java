package com.main.junaidstore.ParcelConverter;

import android.os.Parcel;

import com.main.junaidstore.models.Categories;
import com.main.junaidstore.models.Response;
import com.main.junaidstore.models.Users;

import org.parceler.ParcelConverter;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muazzam on 4/17/2017.
 */

public class ParcelCategoriesConverter  implements ParcelConverter<List<Categories>> {

    @Override
    public void toParcel(List<Categories> input, Parcel parcel) {
        if (input == null) {
            parcel.writeInt(-1);
        }
        else {
            parcel.writeInt(input.size());
            for (Categories item : input) {
                parcel.writeParcelable(Parcels.wrap(item), 0);
            }
        }
    }

    @Override
    public List<Categories> fromParcel(Parcel parcel) {
        int size = parcel.readInt();
        if (size < 0) return null;
        List<Categories> items = new ArrayList<Categories>();
        for (int i = 0; i < size; ++i) {
            items.add((Categories) Parcels.unwrap(parcel.readParcelable(Categories.class.getClassLoader())));
        }
        return items;
    }
}