package com.emailserver.email_server.Server;

import java.util.ArrayList;
public interface Criteria {
    ArrayList<String> meetCriteria(String path, String criteria);
}
