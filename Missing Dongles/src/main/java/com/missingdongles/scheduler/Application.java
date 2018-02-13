package com.missingdongles.scheduler;

import com.missingdongles.scheduler.job.MissingDongle;

public class Application{
    public static void main(String[] args) throws Exception{

        if(args.length < 1){

            System.out.println("File name arguments are not provided");
        } else{

            MissingDongle missingDongle = new MissingDongle();
            missingDongle.execute(args[0]);
        }
    }

}
