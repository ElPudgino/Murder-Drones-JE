package pzi.md_je.util;

import java.util.concurrent.ThreadLocalRandom;

public class rand 
{
    public static float Next()
    {
        return ThreadLocalRandom.current().nextFloat();
    }
}
