package com.jesus.phisheye.dto;

import java.util.ArrayList;
import java.util.Date;

public class SampleDTO {
    public class Domain{
        public String id;
        public String domain;
        public String punycode;
        public String name;
        public String extension;
        public String whois_server;
        public ArrayList<String> status;
        public ArrayList<String> name_servers;
        public Date created_date;
        public Date created_date_in_time;
        public Date updated_date;
        public Date updated_date_in_time;
        public Date expiration_date;
        public Date expiration_date_in_time;
    }

    public class Registrant{
        public String name;
        public String street;
        public String city;
        public String province;
        public String postal_code;
        public String country;
        public String phone;
        public String fax;
        public String email;
    }

    public class Registrar{
        public String name;
        public String phone;
        public String email;
        public String referral_url;
    }

    public class Root{
        public Domain domain;
        public Registrar registrar;
        public Registrant registrant;
    }


}
