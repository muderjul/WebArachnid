package AbstractTests;

import static org.junit.jupiter.api.Assertions.*;

import AbstractArachnid.URLHandler;
import AbstractArachnid.URLQueue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class URLTests {

    @Test
    void Test_Handler_Linkto(){
        URLHandler firstConstr = new URLHandler("url", 3, "otherURL");
        assertEquals("otherURL", firstConstr.getLinkto(), "Linkto did not match.");

        URLHandler secondConstr = new URLHandler("url", "otherURL");
        assertEquals("otherURL", firstConstr.getLinkto(), "Linkto did not match.");

        firstConstr.setLinkto("differentURL");
        assertEquals("differentURL", firstConstr.getLinkto(), "Linkto did not match after set.");
    }

    @Test
    void Test_Handler_NameURL(){
        URLHandler handler = new URLHandler("URL", "bla");

        assertEquals("url", handler.getName(), "Name did not match");
        assertEquals("URL", handler.getURL(), "URL did not match");

        URLHandler handler1 = new URLHandler("URL", 1, "bla");

        assertEquals("url", handler1.getName(), "Name did not match");
        assertEquals("URL", handler1.getURL(), "URL did not match");
    }

    @Test
    void Test_Handler_DepthGetIllegal(){
        URLHandler handler = new URLHandler("URL", "bla");
        try{
            handler.getDepth();
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("URLHandler.getDepth did not throw exception on depth not set.");
    }

    @Test
    void Test_Handler_Depth(){

        URLHandler handler = new URLHandler("url", 3, "bla");
        assertEquals(3, handler.getDepth(), "Set depth did not match.");


        URLHandler second = new URLHandler("url", "bla");
        second.setDepth(5);
        assertEquals(5, second.getDepth(), "Depth could not be set properly on the first time.");
        second.setDepth(3);
        assertEquals(5, second.getDepth(), "Depth could be reset.");
    }

    @Test
    void Test_Handler_Equals(){
        URLHandler that = new URLHandler("Name", 3, "linkto");
        URLHandler other = new URLHandler("Name", 5, "otherLink");

        assertTrue(that.equals(that), "Handler does not equal himself.");
        assertTrue(that.equals(other), "Handler does not equal handler with same name.");
        assertTrue(other.equals(that), "Handlers equal method is not symmetric.");
    }



    @Test
    void Test_Queue_Seen(){
        URLHandler handler = new URLHandler("Name", 3, "bla");

        URLQueue queue = new URLQueue();
        queue.addSeen(handler);
        assertTrue(queue.isSeen(handler), "Expected handler to be seen but wasn't.");
    }

    @Test
    void Test_Queue_PushPop(){
        URLHandler handler = new URLHandler("Name", 3, "bla");

        URLQueue queue = new URLQueue();
        queue.addUnseen(handler);
        assertFalse(queue.isSeen(handler), "Expected handler not to be seen, but was.");
        assertEquals(handler, queue.getNext());
    }

    @Test
    void Test_Queue_EmptyBehaviour(){
        URLHandler handler = new URLHandler("Name", 3, "bla");

        URLQueue queue = new URLQueue();
        assertEquals(null, queue.getNext(), "Expected queue to be empty upon creation.");
        queue.addUnseen(handler);
        assertEquals(handler, queue.getNext(), "Queue did not pop the only pushed element.");
        assertEquals(null, queue.getNext(), "Queue popped element although there shouldn't be any.");
    }

    @Test
    void Test_Queue_SeenBehaviour(){
        URLHandler handler = new URLHandler("Name", 3, "bla");

        URLQueue queue = new URLQueue();
        queue.addUnseen(handler);
        assertFalse(queue.isSeen(handler), "Unseen element was flagged as seen.");
        queue.addSeen(handler);
        assertTrue(queue.isSeen(handler), "Seen element was not flagged as seen.");
        assertEquals(null, queue.getNext(), "Expected handler to be removed from unseen.");
        queue.addUnseen(handler);
        assertEquals(null, queue.getNext(), "Expected queue not to push a seen element.");
    }

    @Test
    void Test_Queue_AddAll(){
        URLHandler handler1 = new URLHandler("Name1", 3, "bla1");
        URLHandler handler2 = new URLHandler("Name2", 3, "bla2");
        URLHandler handler3 = new URLHandler("Name3", 3, "bla3");
        URLHandler handler4 = new URLHandler("Name4", 3, "bla4");
        URLHandler handler5 = new URLHandler("Name5", 3, "bla5");

        List<URLHandler> handlerList = new ArrayList<URLHandler>(5);
        handlerList.add(handler1);
        handlerList.add(handler2);
        handlerList.add(handler3);
        handlerList.add(handler4);
        handlerList.add(handler5);


        URLQueue queue = new URLQueue();
        queue.addUnseen(handler2); // test for handler contained in unseen
        queue.addSeen(handler4);   // test for handler contained in seen

        queue.addAllUnseen(handlerList);
        assertTrue(queue.isSeen(handler4), "Handler should have been seen.");
        assertEquals(handler2, queue.getNext(), "Queue did not operate as FIFO queue.");
        assertEquals(handler1, queue.getNext(), "Queue did not provide correct element.");
        assertEquals(handler3, queue.getNext(), "Queue did not provide correct next element.");
        assertEquals(handler5, queue.getNext(), "Queue did not provide correct next element.");
        assertEquals(null, queue.getNext(), "Queue contained more elements than it should have");

        assertFalse(queue.isSeen(handler1), "Queue had element as seen when it shouldn't have after addall operation.");
    }
}
