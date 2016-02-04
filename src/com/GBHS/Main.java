package com.GBHS;

import org.apache.commons.lang3.StringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static String response;
    private static int loc;
    private static ArrayList<String> checkArray = new ArrayList<>();

    public static void main(String[] args)
    {
        String type;
        Scanner sc = new Scanner(System.in);
        System.out.println("Type 1 for Android, Type 2 for iOS");
        type = sc.nextLine();
        System.out.println("Type 1 for Names, Type 2 for Emails, Type 3 for Phones");
        response = sc.nextLine();

        Document staff;

        try
        {
            staff = Jsoup.connect("http://grandblanc.high.schoolfusion.us/modules/cms/pages.phtml?pageid=120116").get();

            Element table = staff.select("table").last();
            Elements rows = table.select("tr");
            for (int i = 3; i < rows.size(); i++) //Skip first three rows
            {
                Element row = rows.get(i);
                switch (response)
                {
                    case ("1"):
                        checkArray.add(row.select("td").get(0).text());
                        break;
                    case ("2"):
                        checkArray.add(StringUtils.substringBetween(
                                row.select("td").get(0).toString().toLowerCase(), "mailto:", ".org") + ".org");
                        break;
                    case ("3"):
                        checkArray.add("810" + row.select("td").get(2).text().replace("-", "").replace("\u00a0", ""));
                        break;
                }
            }

            if (type.equals("1"))
            {
                printAndroid(checkArray);
            }
            else if (type.equals("2"))
            {
                printIOS(checkArray);
            }
        }catch(IOException | NullPointerException e){
            e.printStackTrace();
        }

    }

    public static void printAndroid(ArrayList<String> checkArray)
    {
        for (String item: checkArray) {
            try {
                item = item.toLowerCase();
                if (response.equals("1")) {
                    loc = item.indexOf(" ");
                    item = item.substring(0, 1).toUpperCase() + item.substring(1, loc).toLowerCase() + " " +
                            item.substring(loc + 1, loc + 2).toUpperCase() + item.substring(loc + 2).toLowerCase();
                }
                else if (response.equals("3"))
                {
                    if (item.equals("810"))
                    {
                        item = "NONE";
                    }
                }
                System.out.println("<item>" + item + "</item>");
            }catch (NullPointerException e){
                e.printStackTrace();
                System.out.println("MISSING");
            }
        }
    }

    public static void printIOS(ArrayList<String> checkArray)
    {
        for (String item: checkArray) {
            try {
                item = item.toLowerCase();
                if (response.equals("1")) {
                    loc = item.indexOf(" ");
                    item = item.substring(0, 1).toUpperCase() + item.substring(1, loc).toLowerCase() + " " +
                            item.substring(loc + 1, loc + 2).toUpperCase() + item.substring(loc + 2).toLowerCase();
                }
                else if (response.equals("3"))
                {
                    if (item.equals("810"))
                    {
                        item = "NONE";
                    }
                }
                System.out.println("\"" + item + "\",");
            }catch (NullPointerException e){
                e.printStackTrace();
                System.out.println("MISSING");
            }
        }
    }
}
