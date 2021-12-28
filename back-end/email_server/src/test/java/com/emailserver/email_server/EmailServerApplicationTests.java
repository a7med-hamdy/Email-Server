package com.emailserver.email_server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import com.emailserver.LoginAndSessionManagement.sessionManager;
import com.emailserver.email_server.userAndMessage.message;
import com.emailserver.email_server.userAndMessage.messageBody;
import com.emailserver.email_server.userAndMessage.messageHeader;
import com.emailserver.email_server.userAndMessage.messageMaker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServerApplicationTests {

	@Test
	void messageTest() {

        messageMaker maker = new messageMaker();

        Date now=new Date();
        Date then= new Date(121,10,27);
        System.out.println(now);
        System.out.println(then);
        Queue<Integer> to = new LinkedList<>();
        to.add(99999);
        to.add(555);
        message n = maker.getNewMessage(1111, "hello", 5,342544, to, "new", now, 1, new ArrayList<>(Arrays.asList("image.jpf")));

        assertEquals(false, n.getDeleted());
        assertEquals(1111, n.getID());
        assertEquals(now.getTime(), n.getTime());
        assertEquals(1, n.isPriority());
        assertTrue(n.getHeader() instanceof messageHeader);
        assertEquals("new", n.getHeader().getSubject());
        assertTrue(n.getBody() instanceof messageBody);


        n.setTime(then);

        assertEquals(true, n.getDeleted());
    }

	@Test
    void sessionTest() {

        sessionManager session = sessionManager.getInstanceOf();
        session.createSession(50, "userName", "password", "Joj@joster.com");
        assertEquals(null, session.getSessionByUserID(58));
        assertTrue(session.getSessionByUserID(50) instanceof Object);

    }

}
