/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.print;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author ROSHAN
 */
public class ReceiptPrint implements Printable{
    public static String tableNo = "";
    public static String serv = "";
  /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 public static void main(String[] args) throws PrinterException { 
    
        Printable p = new ReceiptPrint();
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        job.setPrintable(p, pf);
        job.print();
       
}


//    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
       int i=1; 
//    
        
             
       String line="--------------------------------------------------------------------------------------------------------";   
              if (pageIndex==0) {  
                  
 ///////////////////////////////////////// HEADER ///////////////////////////////////////////////////////////////
         int hi=100;         
             Font fob=new Font("Britannic Bold", Font.BOLD, 14);
         graphics.setFont(fob);    
         //String pr="AVASTA";
                           
        // graphics.drawString(pr,46, hi);                 
                        
             Font fo=new Font("DejaVu Serif", Font.BOLD, 11);
         graphics.setFont(fo);    
         
        // graphics.drawString("Resort & Spa", 101, hi);
      // hi=hi+12;
       
             Font fo0=new Font("Serif", Font.BOLD, 11);
         graphics.setFont(fo0);    
         
        // graphics.drawString("Anuradapura, Sri Lanka.",48, hi);
        // hi=hi+15;
         
         
         graphics.setFont(fob);    
         String kot="KOT - "+serv;
         
         int lkot=kot.length();                 
         graphics.drawString(kot,70, hi); 
         hi=hi+10;
 ///////////////////////////////////////// END HEADER ///////////////////////////////////////////////////////////////  
         
 /*  LINE   */         graphics.drawString(line, 7, hi);         /*  LINE   */ 
                       hi=hi+10;
////////////////////////////////////////// INVOICE TITLE /////////////////////////////////////////////////////////////         
     
             Font fo1=new Font("GungsuhChe", Font.BOLD, 11);
         graphics.setFont(fo1);    
      
         graphics.drawString("Table # : "+tableNo ,9, hi);
         String cashiar= "Server : ";
         int cashl=graphics.getFontMetrics().stringWidth(cashiar);
         cashl=205-cashl;         
         graphics.drawString( cashiar,cashl, hi);
         
         hi=hi+16;
         int j = 1;
                  
////////////////////////////////////////// END INVOICE TITLE /////////////////////////////////////////////////////////////
          graphics.setFont(fo0);    
       //  graphics.drawString(line, 7, hi);
       //   hi=hi+8;
        
          Font fo4=new Font("DejaVu Sans Mono", Font.PLAIN, 8);
          graphics.setFont(fo4);  
          Calendar c= Calendar.getInstance();
          Date dt=c.getTime();
          DateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
          graphics.drawString("Date & Time : "+df.format(dt),53, hi);
          hi=hi+4;
        
         graphics.drawLine(12, hi, 235, hi);
         
          hi=hi+8;
          Font fo6=new Font("DejaVu Sans Mono", Font.BOLD, 7);
          graphics.setFont(fo6);
          String rabid="Rabid Technologies (+94)71 957 0 985";
          graphics.drawString(rabid,42, hi);
          
          //hi=hi+10;
        //  String rabidc="Hotline : (+94)71 957 0 985";
        //  graphics.drawString(rabidc,47, hi);
          
//////////////////////////////////////////////////////////////// END FOTTER //////////////////////////////////////////////////////////////////////
         i=1;
              }
              
              if(pageIndex==1){
               i=0;
              }
              
          if(i==1){
       return PAGE_EXISTS;  
          }else{
           return NO_SUCH_PAGE;
          }
         
    }
}
