package com.mobitill.mobitill_2.data.models.generic;

import com.mobitill.mobitill_2.data.models.generic.models.LocalGeneric;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

/**
 * Created by james on 12/8/2016.
 */

public class GenericController {
    private static final String TAG = GenericController.class.getSimpleName();

    public void save(LocalGeneric localGeneric){
        localGeneric.save();
    }

    public void deleteAll(){
        LocalGeneric.deleteAll(LocalGeneric.class);
    }

    public List<LocalGeneric> getData(String model, String appId){
        List<LocalGeneric> data = Select.from(LocalGeneric.class)
                .where(Condition.prop("model").eq(model),
                        Condition.prop("appid").eq(appId))
                .list();

         return data;
    }

    public LocalGeneric getItem(){
        return null;
    }
}
