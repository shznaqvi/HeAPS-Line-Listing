package edu.aku.hassannaqvi.heapslinelisting.models

import android.database.Cursor
import edu.aku.hassannaqvi.heapslinelisting.contracts.TableContracts.ClusterTable
import edu.aku.hassannaqvi.heapslinelisting.core.MainApp._EMPTY_
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by hassan.naqvi on 11/30/2016.
 */
class Cluster {
    var ID: Long = 0
    var distId: String = _EMPTY_
    var clusterCode: String = _EMPTY_
    var distName: String = _EMPTY_
    var area: String = _EMPTY_  /*Cluster name or definition*/


    constructor() {
        // Default Constructor
    }

    @Throws(JSONException::class)
    fun sync(jsonObject: JSONObject): Cluster {
        area = jsonObject.getString(ClusterTable.COLUMN_AREA)
        distId = jsonObject.getString(ClusterTable.COLUMN_DISTRICT_ID)
        clusterCode = jsonObject.getString(ClusterTable.COLUMN_CLUSTER_CODE)
        distName = jsonObject.getString(ClusterTable.COLUMN_DIST_NAME)




        return this
    }

    fun hydrate(cursor: Cursor): Cluster {
        ID = cursor.getLong(cursor.getColumnIndexOrThrow(ClusterTable.COLUMN_ID))

        area = cursor.getString(cursor.getColumnIndexOrThrow(ClusterTable.COLUMN_AREA))
        distId = cursor.getString(cursor.getColumnIndexOrThrow(ClusterTable.COLUMN_DISTRICT_ID))
        clusterCode = cursor.getString(cursor.getColumnIndexOrThrow(ClusterTable.COLUMN_CLUSTER_CODE))
        distName = cursor.getString(cursor.getColumnIndexOrThrow(ClusterTable.COLUMN_DIST_NAME))
        area = cursor.getString(cursor.getColumnIndexOrThrow(ClusterTable.COLUMN_AREA))



        return this
    }


}