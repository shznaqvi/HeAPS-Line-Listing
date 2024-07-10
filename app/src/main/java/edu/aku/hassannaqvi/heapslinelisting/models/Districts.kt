package edu.aku.hassannaqvi.heapslinelisting.models

import android.database.Cursor
import edu.aku.hassannaqvi.heapslinelisting.contracts.TableContracts.TableDistricts
import edu.aku.hassannaqvi.heapslinelisting.core.MainApp._EMPTY_
import org.json.JSONObject

/**
 * Created by hassan.naqvi on 10/31/2016.
 * @update ali.azaz
 */
class Districts {
    var districtCode: String = _EMPTY_
    var districtName: String = _EMPTY_

    fun sync(jsonObject: JSONObject): Districts {
        districtCode = jsonObject.getString(TableDistricts.COLUMN_DISTRICT_ID)
        districtName = jsonObject.getString(TableDistricts.COLUMN_DISTRICT_NAME)
        return this
    }

    fun hydrate(cursor: Cursor): Districts {
        districtCode = cursor.getString(cursor.getColumnIndexOrThrow(TableDistricts.COLUMN_DISTRICT_ID))
        districtName = cursor.getString(cursor.getColumnIndexOrThrow(TableDistricts.COLUMN_DISTRICT_NAME))
        return this
    }


}