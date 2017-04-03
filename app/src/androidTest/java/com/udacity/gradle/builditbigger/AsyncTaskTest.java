package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;

import com.example.JavaJokes;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;

/**
 * Created by mulya on 22/12/2016.
 */
@RunWith(AndroidJUnit4.class)
public class AsyncTaskTest{

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testAsyncTask(){

        try {
            FetchJokeTask jokeTask = new FetchJokeTask();
            jokeTask.execute();
            String joke = jokeTask.get(10, TimeUnit.SECONDS);
            assertEquals("Testing jokes...", joke, new JavaJokes().getJoke());
        }catch(TimeoutException e){
            fail("Timed out");
        }
        catch(InterruptedException e){
            fail("connection interrupted");
        }catch(ExecutionException e){
            fail("Execution error");
        }
    }
}