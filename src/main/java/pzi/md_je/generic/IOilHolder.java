package pzi.md_je.generic;

public interface IOilHolder 
{
    public float GetCapacity();
    public float GetCurrentOil();
    public float Input(float amount);
    public void SetOil(float val);
}
