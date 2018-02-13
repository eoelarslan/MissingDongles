package com.missingdongles.scheduler.job;

import com.missingdongles.model.Dongle;
import com.missingdongles.dao.DongleDao;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

public class MissingDongle {

    private final int oneWeek=604800000;  //One week = 7*24*60*60*100
    private final int oneDay = 1000*60*60*24; // One day
    private static final Logger logger = LoggerFactory.getLogger(MissingDongle.class);
    private static final Timestamp defaultTimestamp = new Timestamp(1);

    /** Creates a new instance of MissingDongle */
    public MissingDongle() {
    }

    public void execute(String fileName) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        DongleDao dongleDao = (DongleDao) applicationContext.getBean("dongleDaoImpl");

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        List<Dongle> dongles = dongleDao.listDongles();
        logger.info("liste size:" + dongles.size());
        List<Dongle> dongleList = new ArrayList<Dongle>();
        List<Dongle> temporaryList = new ArrayList<Dongle>();


        int inactiveDongleCount = 0;

        BufferedWriter bw ;
        //XSSFWorkbook workbook;
        //XSSFSheet sheet;

        FileOutputStream fileOutput;
        HSSFWorkbook workbook;
        HSSFSheet worksheet;

        String newMerchant;
        String temporaryMerchant = "";

        for (Dongle dongle : dongles) {

            newMerchant = dongle.getMerchant();
            logger.info("gelen merchant:" + newMerchant);


            if (dongle.getEventTs() == null) {
                dongle.setEventTs(new Timestamp(1));
            }


            if (!temporaryMerchant.equals(newMerchant)) {//yeni bir merchant ise gir

                if ( inactiveDongleCount > 0 ) {

                    dongleList.addAll(temporaryList);
                }
                temporaryList.clear();
                temporaryMerchant = newMerchant;
                inactiveDongleCount = 0;

                if (timestamp.getTime() - (dongle.getEventTs().getTime()) > oneWeek) {
                    inactiveDongleCount += 1;
                }
                temporaryList.add(dongle);

            } else {
                logger.info("timestamp:"+dongle.getEventTs().toString());

                if (timestamp.getTime() - (dongle.getEventTs().getTime()) > oneWeek) {//en az bir tanesi 1 haftadan fazla sinyal almamışsa topluca beacon'ı dön.
                    inactiveDongleCount += 1;
                }
                temporaryList.add(dongle);
            }
        }
        if ( inactiveDongleCount > 0 ) {

            dongleList.addAll(temporaryList);
        }

        try {
            fileOutput = new FileOutputStream(fileName);
            workbook = new HSSFWorkbook();
            worksheet = workbook.createSheet("Missing Dongles");

            int rowNum = 0;
            int colNum = 0;

            HSSFRow row1 = worksheet.createRow(rowNum++);
            HSSFCell cellA = row1.createCell(colNum++);

            cellA.setCellValue("Device Serial");
            cellA = row1.createCell(colNum++);
            cellA.setCellValue("Merchant");
            cellA = row1.createCell(colNum++);
            cellA.setCellValue("Description");
            cellA = row1.createCell(colNum++);
            cellA.setCellValue("Vendor Name");
            cellA = row1.createCell(colNum++);
            cellA.setCellValue("Brand Name");
            cellA = row1.createCell(colNum++);
            cellA.setCellValue("Battery");
            cellA = row1.createCell(colNum++);
            cellA.setCellValue("Last Event");
            cellA = row1.createCell(colNum);
            cellA.setCellValue("Inactive For");



            for(Dongle dongle : dongleList){

                colNum = 0;
                row1 = worksheet.createRow(rowNum++);
                HSSFCell cellB = row1.createCell(colNum++);
                cellB.setCellValue(dongle.getDeviceSerial());

                cellB = row1.createCell(colNum++);
                cellB.setCellValue(dongle.getMerchant());

                cellB = row1.createCell(colNum++);
                cellB.setCellValue(dongle.getDescription());

                cellB = row1.createCell(colNum++);
                cellB.setCellValue(dongle.getVendorName());

                cellB = row1.createCell(colNum++);
                cellB.setCellValue(dongle.getBrandName());

                cellB = row1.createCell(colNum++);
                cellB.setCellValue(dongle.getBattery());

                //row1 = worksheet.createRow(rowNum++);

                if (dongle.getEventTs().compareTo(defaultTimestamp) == 0) {

                    cellB = row1.createCell(colNum++);
                    cellB.setCellValue("-");

                    cellB = row1.createCell(colNum);
                    cellB.setCellValue("-");
                    //bw.write("-" + "\t");
                    //bw.write("-" + "\t");
                } else {

                    cellB = row1.createCell(colNum++);
                    cellB.setCellValue(String.valueOf(dongle.getEventTs()));

                    cellB = row1.createCell(colNum);
                    cellB.setCellValue(String.valueOf((timestamp.getTime() - (dongle.getEventTs().getTime())) / oneDay));

                    //bw.write(dongle.getEventTs() + "\t");
                    //bw.write(String.valueOf((timestamp.getTime() - (dongle.getEventTs().getTime())) / oneDay));
                }



            }


            workbook.write(fileOutput);
            fileOutput.flush();
            fileOutput.close();

        } catch (IOException e) {
            e.printStackTrace();
        }




        /*try {
            bw = new BufferedWriter(new FileWriter(fileName, false));

            bw.write("Device Serial" +"\t"
                    + "Merchant" +"\t"
                    + "Description" +"\t"
                    + "Vendor Name" +"\t"
                    + "Brand Name" +"\t"
                    + "Battery" +"\t"
                    + "Last Event" +"\t"
                    + "Inactive For");
            bw.newLine();
            //CharConvert charConvert = new CharConvert();

            for (Dongle dongle : dongleList) {

                //charConvert.converter(dongle);

                logger.info(String.valueOf(dongle));
                bw.write(dongle.getDeviceSerial() + "\t");
                bw.write(dongle.getMerchant() + "\t");
                bw.write(dongle.getDescription() + "\t");
                bw.write(dongle.getVendorName() + "\t");
                bw.write(dongle.getBrandName() + "\t");
                bw.write(dongle.getBattery() + "\t");

                if (dongle.getEventTs().compareTo(defaultTimestamp) == 0) {
                    bw.write("-" + "\t");
                    bw.write("-" + "\t");
                } else {
                    bw.write(dongle.getEventTs() + "\t");
                    bw.write(String.valueOf((timestamp.getTime() - (dongle.getEventTs().getTime())) / oneDay));
                }

                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            logger.error("Error in file operation:", e);
        }*/

    }
}