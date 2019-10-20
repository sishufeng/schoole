package com.leien.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/10/20 15:19
 * @Description: 比较两个list中的元素，返回两个集合中不同的元素
 * @ClassName: CompareListUtils
 */
public class CompareListUtils {

    public static List<String> getDiffrent(List<String> list1, List<String> list2) {
        List<String> diff = new ArrayList<String>();
        List<String> maxList = list1;
        List<String> minList = list2;
        if(list2.size()>list1.size())
        {
            maxList = list2;
            minList = list1;
        }
        Map<String,Integer> map = new HashMap<String,Integer>(maxList.size());
        for (String string : maxList) {
            map.put(string, 1);
        }
        for (String string : minList) {
            if(map.get(string)!=null)
            {
                map.put(string, 2);
                continue;
            }
            diff.add(string);
        }
        for(Map.Entry<String, Integer> entry:map.entrySet())
        {
            if(entry.getValue()==1)
            {
                diff.add(entry.getKey());
            }
        }
        return diff;

    }

    /*public static void main(String[]args){
        List list1 = new ArrayList();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("5");
        List list2 = new ArrayList();
        list2.add("1");
        list2.add("2");
        list2.add("3");
        list2.add("4");
        List diffrent = getDiffrent(list1, list2);
        for (int i =0; i<diffrent.size();i++){
            System.out.println(diffrent.get(i));
        }
    }*/
}
