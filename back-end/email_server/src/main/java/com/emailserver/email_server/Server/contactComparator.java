package com.emailserver.email_server.Server;

import java.util.Comparator;

import com.emailserver.email_server.userAndMessage.userContact;

public class contactComparator implements Comparator<userContact>{

    @Override
    public int compare(userContact o1, userContact o2) {
        return o1.getName().compareTo(o2.getName());
    }
    
}
