package com.emailserver.email_server.Server;

import java.util.Comparator;

import com.emailserver.email_server.userAndMessage.userContact;

public class contactComparator implements Comparator<userContact>{
    /**
     * compare between 
     * @param o1
     * @param o2
     * @return 1 if p1 is first ,0 if equal, -1 if less
     */
    @Override
    public int compare(userContact o1, userContact o2) {
        return o1.getName().compareTo(o2.getName());
    }
    
}
