package pzi.md_je.util;

public class AABBRaycastRes
{
    public double dist;
    public boolean hit;
    public AABBRaycastRes(boolean h, double t)
    {
        hit = h;
        dist = t;
    }
}