package com.emailserver.email_server.Server;


class CriteriaManager {
    /**
     * take the field to filtered
     * @param field the field which will be searched
     * @return the filtered messages or contacts
     */
    protected static Criteria getCriteria(String field)
    {
        if(field.equalsIgnoreCase("subject"))
        {
            return new subjectCriteria();
        }
        else if(field.equalsIgnoreCase("sender"))
        {
            return new senderCriteria();
        }
        else if(field.equalsIgnoreCase("receiver"))
        {
            return new receiverCriteria();
        }
        else if(field.equalsIgnoreCase("body"))
        {
            return new bodyCriteria();
        }
        else if(field.equalsIgnoreCase("attachment"))
        {
            return new attachmentCriteria();
        }
        else
        {
            return new globalCriteria(new bodyCriteria(),new attachmentCriteria(),new senderCriteria(),new receiverCriteria(),new subjectCriteria());
        }
    }
}
