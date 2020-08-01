/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.report;

import com.rowi.lms.dao.PackageDAO;
import com.rowi.lms.modle.Voucher;
import com.rowi.lms.util.Utilitys;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ROSHAN
 */
public class ReceiptREP implements Printable {

//    private static String name;
//    private static String receiptNo;
//    private static String branch;
//    private static String cashier;
//    private static String regNo;
//    private static String regPackage;
//    private static String remark;
//    private static String amount;
    private static String greating;
    private static String note;
    private static String type;

    public static void main(String[] args) {
        Printable p = new ReceiptREP();
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        job.setPrintable(p, pf);
        try {
            job.print();
        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(Voucher voucher, String voucherNo) {
//
//        name = voucher.getCustomerName();
//        receiptNo = voucherNo;
//        branch = voucher.getBranch();
//        cashier = voucher.getUser();
//        regNo = voucher.getCode();
//        regPackage = voucher.getCpu();
//        remark = voucher.getRemark();
//        amount = voucher.getAmount();
//        type = voucher.getType();

        Printable p = new ReceiptREP();
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = new PageFormat();
        job.setPrintable(p, pf);
        try {
            job.print();
        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        int i = 1;
//
        String name = "Roshan Wijendra";
        String receiptNo = "REP00001";
        String branch = "PUJ";
        String cashier = "Chamara";
        String regNo = "PUJ00001";
        String regPackage = "VTNB";
        String remark = "Test entry";
        String amount = "15,000.00";
        greating = "Thank You !";
        note = "Payments Are Not Refundable";

        int hi = 100;
        int wi = 120;
        String line = "--------------------------------------------------------------------------------";
        if (pageIndex == 0) {
            Font fo1 = new Font("Arial Black", Font.ITALIC, 17);
            graphics.setFont(fo1);
            wi = wi + 42;
            graphics.drawString("H", wi + 9, hi);
            Font fo2 = new Font("Arial Black", Font.ITALIC, 13);
            graphics.setFont(fo2);
            String ARITH = "ARITH";
            graphics.drawString(ARITH, wi + 23, hi);
            graphics.setFont(fo1);
            graphics.drawString("A", wi + 68, hi);
            hi = hi + 12;
            Font fo3 = new Font("GungsuhChe", Font.ITALIC, 12);
            graphics.setFont(fo3);
            graphics.drawString("DRIVING SCHOOL", wi + 40, hi);

            hi = hi + 22;
            Font fo4 = new Font("GungsuhChe", Font.BOLD, 11);
            graphics.setFont(fo4);

            String Header = "Receipt";
            if (type.startsWith("VOUCHER")) {
                Header = "Voucher";
            }
            graphics.drawString(Header, wi + 50, hi);
            wi = wi - 42;

            hi = hi + 13;
            Font fo5 = new Font("GungsuhChe", Font.PLAIN, 10);
            graphics.setFont(fo5);
            graphics.drawString(line, wi + 6, hi);
//            graphics.drawLine(1, hi, 800, hi);

            hi = hi + 18;
            graphics.drawString("Branch : " + branch + "                      Receipt No : " + receiptNo, wi + 6, hi);

            hi = hi + 16;
            graphics.drawString("Date : " + Utilitys.systemDate() + "               Cashier : " + cashier, wi + 6, hi);
            if (regNo != null) {

                hi = hi + 16;
                graphics.drawString("Reg No : " + regNo, wi + 6, hi);

                hi = hi + 16;
                graphics.drawString("Customer : " + name, wi + 6, hi);

                hi = hi + 16;
                if (regPackage != null) {
                    try {
                        String pack = PackageDAO.getPackage(regPackage);
                        graphics.drawString("Reg Package : " + pack, wi + 6, hi);
                    } catch (SQLException ex) {
                        Logger.getLogger(ReceiptREP.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

            hi = hi + 16;
            graphics.drawString("Remark : " + remark, wi + 6, hi);

            hi = hi + 10;
            Font fo6 = new Font("GungsuhChe", Font.BOLD, 12);
            graphics.setFont(fo6);

            graphics.drawRect(wi + 75, hi, 110, 20);
            hi = hi + 15;
            graphics.drawString("Rs. : " + Utilitys.getCurrencyValue(amount), wi + 80, hi);

            hi = hi + 20;
            graphics.setFont(fo5);
            graphics.drawString(line, wi + 6, hi);
//            graphics.drawLine(1, hi, 800, hi);

            hi = hi + 16;
            graphics.drawString(greating, wi + 88, hi);
            hi = hi + 12;
            graphics.drawString(note, wi + 70, hi);
            hi = hi + 12;
            graphics.drawString("[ RoWi ]", wi + 95, hi);
            hi = hi + 12;
            graphics.drawString("*** Issue only testing purpose ***", wi + 45, hi);

        }
        i = 1;

        if (pageIndex == 1) {
            i = 0;
        }

        if (i == 1) {
            return PAGE_EXISTS;
        } else {
            return NO_SUCH_PAGE;
        }
    }

}
