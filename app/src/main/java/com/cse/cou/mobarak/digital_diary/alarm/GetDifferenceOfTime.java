package com.cse.cou.mobarak.digital_diary.alarm;

import java.util.Date;


/**
 * Created by  on 7/17/2018.
 */

public class GetDifferenceOfTime {
    String str_starting, str_finish,str_time;
    int syear, smonth, sday, dyear, dmonth, dday, rday, rmonth, ryear,dhour,dminutes;

    public long getDifferece(String start,String finish,String time) {
      long difference = 0;

        str_starting=start;
        str_finish = finish;
        str_time=time;

        if (str_starting.isEmpty() || str_finish.isEmpty()) {
            return 10;
        } else {
            String[] item = str_starting.replace(":", ",").split(",");
            syear = Integer.parseInt(item[0].trim());
            smonth = Integer.parseInt(item[1].trim())+1;
            sday = Integer.parseInt(item[2].trim());
            String[] item2 = str_finish.replace(":", ",").split(",");
            dyear = Integer.parseInt(item2[2].trim());
            dmonth = Integer.parseInt(item2[1].trim());
            dday = Integer.parseInt(item2[0].trim());
            String[] item3 = str_time.replace(":", ",").split(",");
            dhour = Integer.parseInt(item3[0].trim());
            dminutes = Integer.parseInt(item3[1].trim());

            if (dday >= sday) {

                rday = dday - sday;
                if (dmonth >= smonth) {
                    rmonth = dmonth - smonth;
                    ryear = dyear - syear;

                } else {
                    dyear = dyear - 1;
                    dmonth = dmonth + 12;
                    rmonth = dmonth - smonth;
                    ryear = dyear - syear;

                }
            } else {
                dmonth = dmonth - 1;
                dday = dday + 30;
                rday = dday - sday;
                if (dmonth >= smonth) {
                    rmonth = dmonth - smonth;
                    ryear = dyear - syear;

                } else {
                    dyear = dyear - 1;
                    dmonth = dmonth + 12;
                    rmonth = dmonth - smonth;
                    ryear = dyear - syear;

                }

            }

            if (ryear < 0) {
                return 5;

            } else {


                int rhour=0,rminutes=0;
                int shour=new Date().getHours();
                int sminutes=new Date().getMinutes();

                rhour=dhour-shour;
                rminutes=dminutes-sminutes;
                if(rhour<0){
                    rhour=24+rhour;
                }
                if(rminutes<0){
                    rminutes=60+rminutes;
                }


                difference=(ryear*365+rmonth*30+rday)*24*60*60+rhour*60*60+rminutes*60;


                return difference;

            }



        }

    }

}
