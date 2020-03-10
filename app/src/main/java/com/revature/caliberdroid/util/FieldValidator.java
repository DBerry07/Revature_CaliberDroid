package com.revature.caliberdroid.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldValidator {

    public static boolean isValidEmail(String input){
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()) return true;
        return false;
    }

    public static boolean isEmptyString(String input){
        if(input == null) return true;
        if(input.length() == 0) return true;
        if(input.trim().length() == 0) return true;
        return false;
    }

    public static boolean isValidZipCode(String input){
        String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()) return true;
        return false;
    }

    //No special characters
    public static String excludedSpecialCharacters = "[]{}<>%$!@#";
    public static boolean isValidPlainText(String input){
        String excludedSpecialCharactersRegEx = "^[^\\[\\]\\{\\}<>%\\$!@#]*$";
        Pattern pattern = Pattern.compile(excludedSpecialCharactersRegEx);
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()) return true;
        return false;
    }

    public static ArrayList<String> TwoLetterStatesList = new ArrayList<> (Arrays.asList(
            "AL",
            "AK",
            "AS",
            "AZ",
            "AR",
            "CA",
            "CO",
            "CT",
            "DC",
            "DE",
            "FM",
            "FL",
            "GA",
            "GU",
            "HI",
            "IA",
            "ID",
            "IL",
            "IN",
            "KS",
            "LA",
            "ME",
            "MI",
            "MN",
            "MO",
            "MP",
            "MS",
            "MT",
            "NC",
            "ND",
            "NE",
            "NH",
            "NJ",
            "NM",
            "NV",
            "NY",
            "OH",
            "OK",
            "OR",
            "PW",
            "PA",
            "PR",
            "RI",
            "SC",
            "SD",
            "TN",
            "TX",
            "UT",
            "VA",
            "VI",
            "VT",
            "WA",
            "WV",
            "WI",
            "WY"
    ));

    public static ArrayList<String> StatesList = new ArrayList<>(Arrays.asList(
            "Alabama",
            "Alaska",
            "American Samoa",
            "Arizona",
            "Arkansas",
            "California",
            "Colorado",
            "Connecticut",
            "District of Columbia",
            "Delaware",
            "Federated States of Micronesia",
            "Florida",
            "Georgia",
            "Guam",
            "Hawaii",
            "Iowa",
            "Idaho",
            "Illinois",
            "Indiana",
            "Kansas",
            "Louisiana",
            "Maine",
            "Michigan",
            "Minnesota",
            "Missouri",
            "Mississippi",
            "Montana",
            "Northern Mariana Islands",
            "North Carolina",
            "North Dakota",
            "Nebraska",
            "New Hampshire",
            "New Jersey",
            "New Mexico",
            "Nevada",
            "New York",
            "Ohio",
            "Oklahoma",
            "Oregon",
            "Palau",
            "Pennsylvania",
            "Puerto Rico",
            "Rhode Island",
            "South Carolina",
            "South Dakota",
            "Tennessee",
            "Texas",
            "Utah",
            "Vermont",
            "Virgin Islands",
            "Vermont",
            "Washington",
            "West Virginia",
            "Wisconsin",
            "Wyoming"
    ));

    private static ArrayList<String> StatesAndAbbreviationsList = null;
    public static ArrayList<String> getStatesAndAbbreviationsList(){
        if(StatesAndAbbreviationsList == null){
            StatesAndAbbreviationsList = new ArrayList<>();
            for(int i=0; i<StatesList.size(); i++){
                StatesAndAbbreviationsList.add( TwoLetterStatesList.get(i)+" "+StatesList.get(i));
            }
        }
        return StatesAndAbbreviationsList;
    }
}
