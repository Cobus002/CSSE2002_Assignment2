package csse2002.block.world;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void main() {
        String args[] = {"foo.in", "actions.in", "hello.out"};
        Main.main(args);
    }
}