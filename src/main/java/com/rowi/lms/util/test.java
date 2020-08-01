/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rowi.lms.util;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author ROSHAN
 */
public class test {

    public static void main(String[] args) {

        ArrayList list = new ArrayList();
        list.add("2");
        list.add("4");
        list.add("8");
        list.add("9");
        list.add("1");
        for (Object list1 : list) {
            System.out.println("NS : " + list1);
        }

        SortedSet set = new TreeSet(list);
        int i = 10;
        int ii = 0;
        for (Object set1 : set) {
            ii = Integer.parseInt((String) set1);
            System.out.println("i - " + i);
            System.out.print(ii + " - ");
            if (ii >= i) {
                ii -= i;
                i = 0;
            } else {
                i -= ii;
                ii = 0;
            }
            System.out.println(ii);
        }

    }
}
