package jiuwei.kt03sdkdemo.library.task;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import jiuwei.kt03sdkdemo.business.util.SafeAsyncTask;
import jiuwei.kt03sdkdemo.library.bean.Brand;
import jiuwei.kt03sdkdemo.library.bean.BrandList;
import jiuwei.kt03sdkdemo.library.util.Constant;
import jiuwei.kt03sdkdemo.library.util.Globals;
import jiuwei.kt03sdkdemo.library.util.HttpRequest;


/**
 * Created by zhangcirui on 15/8/18.
 */
public class GetBrandListTask extends SafeAsyncTask<BrandList> {

    private static final String TAG = GetBrandListTask.class.getSimpleName();

    public int mDeviceId;

    public GetBrandListTask(int deviceId){
        this.mDeviceId = deviceId;
    }

    @Override
    public BrandList call() throws Exception {

        try {
          /*  StringBuffer url = new StringBuffer(Constant.brandUrl);
            Future<String> future = AsyncHttpClient.getDefaultInstance().executeString(new AsyncHttpPost(url.toString()), null);
            String value = future.get(Constant.TIME_OUT, TimeUnit.MILLISECONDS);
*/


            /*Brand[][] brands = new Gson().fromJson(value, Brand[][].class);

            for(Brand brand : brands[0]){

                Log.d(TAG,brand.toString());

            }*/
            BrandList brandList = new BrandList();
            List<Brand> brands = new ArrayList<Brand>();
            Gson gson = new Gson();

            Object[][] comBrands = gson.fromJson(
                    HttpRequest.sendGet(Constant.GETSERVERBRAND + "" + mDeviceId),
                    new TypeToken<Object[][]>() {
                    }.getType());

            for (Object[] object : comBrands) {
                Brand brand = new Brand();
                brand.setId(Double.valueOf((Double) object[0]).intValue());
                brand.setBrand((String) object[1]);
                if (Globals.LocalLanguage == 0) {
                    brand.setBrand_tra((String) object[3]);
                } else {
                    brand.setBrand_tra((String) object[5]);
                }
                brand.setSortLetters((String) object[1]);
                brands.add(brand);
            }

            brandList.setBrandList(brands);
            Log.d(TAG,brands.toString());
            return brandList;
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return null;
        }
    }
}
